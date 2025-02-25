package net.game;

import java.awt.Component;
import java.awt.Graphics;

public class EnemyObject extends BadMovingObject {

	protected Representation normalRepresentation = null;
	protected Representation attackRepresentation = null;
	protected long lastAttack = 0;
	protected FinishingAnimation killAnimaion = null;
	
	protected boolean direction = false;
	
	public EnemyObject(Representation normalRepresentation, Representation attackRepresentation, FinishingAnimation killAnimaion) {
		super(normalRepresentation);
		this.normalRepresentation = normalRepresentation;
		this.attackRepresentation = attackRepresentation;
		this.killAnimaion = killAnimaion;
	}
	
	public void kill()
	{
		rep = this.killAnimaion;
		((Animation)rep).restart();
		
		if(killed)
			this.remove();
		else
			super.kill();
	}
	
	public boolean update(double dt)
	{
		if(rep == attackRepresentation && (System.nanoTime() - lastAttack) > 1000000000L)
			rep = normalRepresentation;
			
		return super.update(dt);
	}
	
	public boolean collitionX(Component object) {
		if(object instanceof HandObject || object instanceof BadStaticObject)
		{
			xMovement = Math.signum(getX() - object.getX()) * 500;
			kill();
		}
		else if(object instanceof PlayerObject && !killed)
		{
			rep = attackRepresentation;
			if(rep instanceof Animation)
				((Animation)rep).restart();
			lastAttack = System.nanoTime();
			direction = getX() < object.getX();
		}
		else if(!(object instanceof SpecialObject))
			super.collitionX(object);

		return false;
	}

	public boolean collitionY(Component object) {
		if(object instanceof HandObject || object instanceof BadStaticObject)
		{
			yMovement = Math.signum(getY() - object.getY()) * 500;
			kill();
		}
		else if(object instanceof PlayerObject)
		{
			if(object.getY() < getY())
				kill();
			else if(!killed)
			{
				rep = attackRepresentation;
				if(rep instanceof Animation)
					((Animation)rep).restart();
				lastAttack = System.nanoTime();
				direction = getX() < object.getX();
			}
		}
		else if(!(object instanceof SpecialObject))
			super.collitionY(object);

		return false;
	}
	
	public void paint(Graphics g)
	{
		super.paint(g, direction);
	}
}
