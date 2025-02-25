package net.game;

import java.awt.Component;
import java.awt.Graphics;

public class PlayerObject extends MovingObject
{	
	protected PlayerState state = PlayerState.START;
	protected long charge = 0;
	
	protected PowerUpObject powerup = null;
	protected HandObject hand = null;
	
	protected int points = 0;
	protected int[] savePoint = {0, 0, 0};
	
	protected double xMovementTarget = 0;
	protected boolean lastDirection = false;
	
	protected boolean finished = false;
	protected long lastKilled = 0;
	
	//protected double recordedDeltaTime = 0;
	
	public PlayerObject()
	{
		super(AssetLoader.playerStartAnimation.clone());
		forceState(PlayerState.START);
	}
	
	public void saveSavePoint()
	{
		savePoint[0] = getX();
		savePoint[1] = getY();
		savePoint[2] = points;
	}
	
	public void loadSavePoint()
	{
		if(powerup != null)
		{
			powerup.kill();
			powerup = null;
		}
		xMovement = 0;
		yMovement = 0;
		xMovementTarget = 0;
		forceState(PlayerState.START);
		setLocation(savePoint[0], savePoint[1]);
		points = savePoint[2];
	}
	
	public int getPoints()
	{
		return this.points;
	}
	
	public boolean getFinished()
	{
		return this.finished;
	}
	
	public void addPowerUp(PowerUpObject powerup)
	{
		if(this.powerup != null)
		{
			this.powerup.kill();
			this.points += 5;
		}
		
		this.powerup = powerup;
	}
	
	public boolean update(double dt)
	{
		//recordedDeltaTime = dt;
		
		if(state != PlayerState.KILLED && state != PlayerState.START && state != PlayerState.HITING && state != PlayerState.END)
		{
			if(super.update(dt))
				return true;
			
			if(!killed)
			{
				// StateChange
				if(state == PlayerState.DIVING)
				{
					if(((FinishingAnimation)rep).finished())
						yMovement = 800;
				}
				else if(GameInputHandler.getKey("hit"))
				{
					if(state == PlayerState.RUNNING || state == PlayerState.STANDING && GameInputHandler.isNew("hit"))
					{
						this.charge = System.nanoTime();
						xMovementTarget = 0;
						setState(PlayerState.CHARGING);
					}
					else if((state == PlayerState.JUMPING || state == PlayerState.FALLING) && GameInputHandler.isNew("hit"))
					{
						if(GameInputHandler.getKey("down"))
						{
							xMovementTarget = 0;
							this.yMovement = 50;
							setState(PlayerState.DIVING);
						}
						else
						{
							this.charge = System.nanoTime();
							xMovementTarget = 0;
							yMovement = 0;
							hit();
						}
					}
					
				}
				else if(state == PlayerState.CHARGING)
				{
					hit();
				}
				else
				{
					if(GameInputHandler.getKey("left") && System.nanoTime() - lastKilled > 500000000L)
					{
						if(state == PlayerState.STANDING)
							setState(PlayerState.RUNNING);
						
						if(state == PlayerState.STANDING || state == PlayerState.RUNNING)
							xMovementTarget = -300;
						else if((state == PlayerState.JUMPING || state == PlayerState.FALLING) && 
								(getObjectAt(getX() - 1, getY()) instanceof GoodStaticObject))
						{
							setState(PlayerState.HANGING);
							xMovementTarget = 0;
						}
						else if(state == PlayerState.JUMPING)
							xMovementTarget = -150;
						else if(state == PlayerState.HANGING && !lastDirection)
							setState(PlayerState.FALLING);
					}
					else if(GameInputHandler.getKey("right") && System.nanoTime() - lastKilled > 500000000L)
					{
						if(state == PlayerState.STANDING)
							setState(PlayerState.RUNNING);
						
						if(state == PlayerState.STANDING || state == PlayerState.RUNNING)
							xMovementTarget = 300;
						else if((state == PlayerState.JUMPING || state == PlayerState.FALLING) && 
								(getObjectAt(getX() + 1, getY()) instanceof GoodStaticObject))
						{
							setState(PlayerState.HANGING);
							xMovementTarget = 0;
						}
						else if(state == PlayerState.JUMPING)
							xMovementTarget = 150;
						else if(state == PlayerState.HANGING && lastDirection)
							setState(PlayerState.FALLING);
					}
					else if(System.nanoTime() - lastKilled > 500000000L)
					{
						if(state == PlayerState.RUNNING)
						{
							setState(PlayerState.FALLING);
						}
						xMovementTarget = 0;
						
					}
					
					if(GameInputHandler.getKey("down"))
					{
						if(state == PlayerState.STANDING)
							setState(PlayerState.LOW);
					}
					else if(state == PlayerState.LOW)
					{
						setState(PlayerState.STANDING);
					}
					
					if(GameInputHandler.getKey("jump"))
					{
						boolean isNew = GameInputHandler.isNew("jump");
						if((state == PlayerState.RUNNING || state == PlayerState.STANDING) && isNew)
						{
							setState(PlayerState.JUMPING);
							yMovement = -350;
						}
						else if(state == PlayerState.JUMPING)
							yMovement -= 550*dt;
						else if(state == PlayerState.HANGING && isNew)
						{
							setState(PlayerState.FALLING);
							yMovement = -350;
							setLocation(getX() + (lastDirection ? 5 : -5), getY());
							xMovementTarget = (lastDirection ? 1 : -1) * 200;
							xMovement = xMovementTarget;
						}
					}
				}
				
				if(state == PlayerState.HANGING && !(getSolidObjectAt(getX() - (lastDirection ? 1 : -1), getY()) instanceof StaticObject))
					setState(PlayerState.FALLING);
				
				if((state == PlayerState.JUMPING || state == PlayerState.RUNNING || state == PlayerState.STANDING) &&
						yMovement > 0 && getSolidObjectAt(getX(), getY() + 10) == null)
					setState(PlayerState.FALLING);
				
				
				if(state == PlayerState.HANGING && yMovement > 0)
				{
					yMovement -= 650 * dt;
				}
				
				if(state == PlayerState.STANDING && getSolidObjectAt(getX() + getWidth()/2*(lastDirection ? -1 : 1), getY() + getHeight()/5) == null)
				{
					rep = AssetLoader.playerOnTheEdgeAnimation;
				}
			}
		}
		else if(state == PlayerState.START)
		{
			if(((FinishingAnimation)rep).finished())
				setState(PlayerState.FALLING);
		}
		else if(state == PlayerState.KILLED)
		{
			yMovement -= (G + 1) * dt;
			if(super.update(dt))
				return true;
			
			try
			{
				if(((FinishingAnimation)rep).finished())
				{
					this.loadSavePoint();
					this.setState(PlayerState.FALLING);
				}
			}
			catch (ClassCastException e) {
			}
		}
		else if(state == PlayerState.HITING)
		{
			if(hand.getKilled())
			{
				setState(PlayerState.FALLING);
				hand.remove();
				hand = null;
			}
		}
		else if(state == PlayerState.END)
		{
			if(((FinishingAnimation)rep).finished())
			{
				this.finished = true;
			}
		}
		
		if(getSolidObjectAt(getX(), getY()+1) == null)
			xMovement = (xMovement * (1 - Math.max(dt*2, 1)) +
					xMovementTarget * Math.max(dt*2, 1));
		else
			xMovement = (xMovement * (1 - Math.max(dt*3, 1)) +
					xMovementTarget * Math.max(dt*3, 1));
		
		return false;
	}
	
	public void hit()
	{
		double speed = Math.min((System.nanoTime() - charge) / 1000000000.0, 1) * 400 + 800;
		if(GameInputHandler.getKey("up"))
		{
			hand = new HandObject(this, 3);
			hand.setMovement(0, -speed);
			hand.setLocation(getX(), getY() + 22);
		}
		else if(GameInputHandler.getKey("left"))
		{
			hand = new HandObject(this, 1);
			hand.setMovement(-speed, 0);
			hand.setLocation(getX() - 10, getY() + getHeight()/2);
		}
		else if(GameInputHandler.getKey("right"))
		{
			hand = new HandObject(this, 0);
			hand.setMovement(speed + 100, 0);
			hand.setLocation(getX() + 10, getY() + getHeight()/2);
		}
		else
		{
			hand = new HandObject(this, (lastDirection ? 1 : 0));
			hand.setMovement((lastDirection ? -speed : speed + 100), 0);
			hand.setLocation(getX() + (lastDirection ? - 10 : 10), getY() + 32);
		}
		
		getParent().add(hand);
		getParent().setComponentZOrder(hand, 0);
		setState(PlayerState.HITING);
	}

	public void setState(PlayerState state)
	{
		if(this.state != PlayerState.KILLED)
		{
			forceState(state);
		}
	}
	
	public void forceState(PlayerState state)
	{
		this.state = state;
		
		switch(state)
		{
		case STANDING:
			rep = AssetLoader.playerStandingAnimation.clone();
			((Animation)rep).restart();
			break;
		case RUNNING:
			rep = AssetLoader.playerRunningAnimation.clone();
			break;
		case JUMPING:
			rep = AssetLoader.playerJumpingUpAnimation.clone();
			((Animation)rep).restart();
			break;
		case FALLING:
			rep = AssetLoader.playerFallingDownAnimation.clone();
			((Animation)rep).restart();
			break;
		case HANGING:
			rep = AssetLoader.playerHangingAnimation.clone();
			((Animation)rep).restart();
			break;
		case LOW:
			rep = AssetLoader.playerStandingDownAnimation.clone();
			((Animation)rep).restart();
			break;
		case KILLED:
			this.rep = AssetLoader.playerDeatAnimation.clone(); 
			((Animation)rep).restart();
			break;
		case CHARGING:
			this.rep = AssetLoader.playerChargingAnimation.clone(); 
			((Animation)rep).restart();
			break;
		case HITING:
			this.rep = AssetLoader.playerHitingAnimation.clone(); 
			((Animation)rep).restart();
			break;
		case DIVING:
			this.rep = AssetLoader.playerFallingFastAnimation.clone();
			((Animation)rep).restart();
			break;
		case END:
			this.rep = AssetLoader.playerEndAnimation.clone();
			((Animation)rep).restart();
			break;
		case START:
			this.rep = AssetLoader.playerStartAnimation.clone();
			((Animation)rep).restart();
			break;
		default:
			break;
		}
	}
	
	
	
	public PlayerState getState()
	{
		return this.state;
	}
	
	public void kill()
	{
		if(this.powerup != null)
		{
			setState(PlayerState.JUMPING);
			this.powerup.kill();
			this.powerup = null;
		}
		else if(!killed)
		{
			setState(PlayerState.KILLED);
		}
		lastKilled = System.nanoTime();
	}
	
	public boolean collitionX(Component object) 
	{
		if(state != PlayerState.END)
		{
			if(object instanceof SpecialObject)
			{
				if(object instanceof PointObject && !((GameObject) object).getKilled())
				{
					points++;
				}
				else if(object instanceof OneUpObject && !((GameObject) object).getKilled())
				{
					addPowerUp((PowerUpObject) object);
				}
				else if(object instanceof FinishingObject)
				{
					setState(PlayerState.END);
				}
				else if(object instanceof SavePointObject)
				{
					saveSavePoint();
				}
			}
			else
			{
				if((object instanceof BadMovingObject || object instanceof BadStaticObject))
				{
					if(!((GameObject)object).getKilled())
					{
						super.collitionX(object);
						
						this.xMovementTarget = Math.signum(getX() - object.getX()) * 300;
						this.xMovement = Math.signum(getX() - object.getX()) * 300;
						
						this.kill();
						return true;
					}
				}
				else if(object instanceof BouncyObject)
				{
					double newMovement = Math.signum(this.xMovement) * -500;
					super.collitionX(object);
					this.xMovement = newMovement;
					setState(PlayerState.JUMPING);
					return true;
				}
				else if(!(object instanceof SpecialObject) && !((GameObject) object).getKilled())
				{
					xMovementTarget = 0;
					xMovement = 0;
					if(getSolidObjectAt((int)xPos, getY() - getHeight()/6) == null)
						super.collitionY(object);
					else
						super.collitionX(object);
				}
			}
			
		}
		return false;
	}

	public boolean collitionY(Component object) 
	{
		if(state != PlayerState.END)
		{
			if(object instanceof SpecialObject)
			{
				if(object instanceof PointObject && !((GameObject) object).getKilled())
				{
					points++;
				}
				else if(object instanceof OneUpObject && !((GameObject) object).getKilled())
				{
					addPowerUp((PowerUpObject) object);
				}
				else if(object instanceof FinishingObject)
				{
					setState(PlayerState.END);
				}
				else if(object instanceof SavePointObject)
				{
					saveSavePoint();
				}
			}
			else
			{
				if(object instanceof BadStaticObject && !((GameObject) object).getKilled())
				{
					super.collitionY(object);
					
					this.yMovement = Math.signum(getY() - object.getY()) * 300;
					this.kill();
					return true;
				}
				else if(object instanceof BadMovingObject)
				{
					if(object.getY() < this.getY())
					{
						if(!((GameObject) object).getKilled())
						{
							super.collitionY(object);
							this.yMovement = Math.signum(getY() - object.getY()) * 300;
							this.kill();
							return true;
						}
					}
					else
					{
						double newMovement = Math.signum(getY() - object.getY()) * 300;
						super.collitionY(object);
						this.yMovement = newMovement;
						setState(PlayerState.JUMPING);
					}
				}
				else if(object instanceof BouncyObject)
				{
					double newMovement = Math.signum(this.yMovement) * -500;
					super.collitionY(object);
					this.yMovement = newMovement;
					setState(PlayerState.JUMPING);
					return true;
				}
				else if(!((GameObject) object).getKilled())
				{
					if(getSolidObjectAt(getX(), (int)yPos + 5) != null && state != PlayerState.LOW && state != PlayerState.CHARGING && state != PlayerState.HITING)
						this.setState(PlayerState.STANDING);
					
					if(getSolidObjectAt(getX() + getWidth()/10, (int)yPos + (int)(Math.signum(yMovement))*getHeight()/16) == null || getSolidObjectAt(getX() - getWidth()/10, (int)yPos + (int)(Math.signum(yMovement))*getHeight()/16) == null)
					{
						super.collitionX(object);
					}
					else
						super.collitionY(object);
				}
			}
			
		}
		return false;
	}
	
	public void paint(Graphics g)
	{
		if(xMovement > 0.1)
			lastDirection = false;
		else if(xMovement < -0.1)
			lastDirection = true;

		try{
			if(state == PlayerState.RUNNING)
				((ValueControledAnimation)rep).setValue(Math.abs(lastDirection ? 6 - ((int)(xPos * 0.08) % 7) : ((int)(xPos * 0.08) % 7)));
		}catch(ClassCastException e){	
		}
		
		
		super.paint(g, lastDirection);
	}

}
