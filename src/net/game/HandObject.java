package net.game;

import java.awt.Component;

public class HandObject extends SpecialObject {

	protected GameObject player = null;
	protected int dir = 0;
	
	public HandObject(PlayerObject from, int dir) {
		super(AssetLoader.handAnimation);
		this.player = from;
		this.dir = dir;
		G = 0;
	}
	
	public boolean update(double dt)
	{
		switch(dir)
		{
		case 0:
			xMovement -= 7000 * dt;
			if(getX() < player.getX())
				this.kill();
			break;
		case 1:
			xMovement += 7000 * dt;
			if(getX() > player.getX())
				this.kill();
			break;
		case 2:
			yMovement -= 7000 * dt;
			if(getY() < player.getY() + player.getHeight()/2)
				this.kill();
			break;
			
		case 3:
			yMovement += 7000 * dt;
			if(getY() > player.getY() + player.getHeight()/2)
				this.kill();
			break;
		}
		
		((ValueControledAnimation)rep).setValue((int)((Math.abs(player.getX() + (dir == 1 ? getWidth()/2 : 0) - getX()) + Math.abs(player.getY() + 32 - getY())) * 0.05 ) * 4 + dir);
		
		return super.update(dt);
	}
	
	public boolean collitionX(Component object) {
		if(!(object instanceof PlayerObject) && Math.signum(xMovement) == Math.signum(getX() - player.getX()))
		{
			while(getObjectAt((int)xPos, (int)yPos) != null)
				this.xPos -= Math.signum(object.getX() - this.getX());
			this.xMovement = 0;
		}
		return false;
	}

	public boolean collitionY(Component object) {
		if(!(object instanceof PlayerObject) && Math.signum(yMovement) == Math.signum(getY() - player.getY()))
		{
			while(getObjectAt((int)xPos, (int)yPos) != null)
				this.yPos -= Math.signum(object.getY() - this.getY());
			this.yMovement = 0;
		}
		return false;
	}
}
