package net.game;

import java.awt.*;

public abstract class MovingObject extends GameObject
{
	protected double G = 1000;
	protected double SPEED_REDUCTION = 0.9;
	
	protected double xMovement = 0;
	protected double yMovement = 0;
	
	public MovingObject(Representation rep)
	{
		super(rep);
	}
	
	public void setXMovement(double xMovement)
	{
		this.xMovement = xMovement;
	}
	
	public void setYMovement(double yMovement)
	{
		this.yMovement = yMovement;
	}
	
	public void setMovement(double xMovement, double yMovement)
	{
		this.setXMovement(xMovement);
		this.setYMovement(yMovement);
	}
	
	public double getMovement()
	{
		return Math.sqrt(this.xMovement * this.xMovement + this.yMovement * this.yMovement);
	}
	
	public boolean update(double dt)
	{
		this.yMovement += G * dt;
	
		this.yMovement *= Math.pow(SPEED_REDUCTION, dt);
		this.xMovement *= Math.pow(SPEED_REDUCTION, dt);
		
		Component[] collitions;
		
		if(Math.abs(yMovement) > Math.abs(xMovement))
		{
			this.yPos += this.yMovement*dt;
			collitions = getAllObjectsAt(this.getX(), (int)this.yPos);
			for(Component collition : collitions)
				if(collition != null)
				{
					if(collition instanceof GameObject)
						if(((GameObject)collition).collitionY(this))
							return true;
					if(this.collitionY(collition))
						return true;
				}
			
			this.setLocation(this.xPos, this.yPos);
			
			this.xPos += this.xMovement*dt;
			collitions = getAllObjectsAt((int)this.xPos, this.getY());
			for(Component collition : collitions)
				if(collition != null)
				{
					if(collition instanceof GameObject)
						if(((GameObject)collition).collitionX(this))
							return true;
					if(this.collitionX(collition))
						return true;
				}
			
		}
		else
		{
			this.xPos += this.xMovement*dt;
			collitions = getAllObjectsAt((int)this.xPos, this.getY());
			for(Component collition : collitions)
				if(collition != null)
				{
					if(collition instanceof GameObject)
						if(((GameObject)collition).collitionX(this))
							return true;
					if(this.collitionX(collition))
						return true;
				}
			
			this.setLocation(this.xPos, this.yPos);
			
			this.yPos += this.yMovement*dt;
			collitions = getAllObjectsAt(this.getX(), (int)this.yPos);
			for(Component collition : collitions)
				if(collition != null)
				{
					if(collition instanceof GameObject)
						if(((GameObject)collition).collitionY(this))
							return true;
					if(this.collitionY(collition))
						return true;
				}
		}
		
		
		this.setLocation(this.xPos, this.yPos);
		return false;
	}

	public boolean collitionX(Component object) 
	{
		while(getSolidObjectAt((int)xPos, (int)yPos) != null)
			this.xPos -= Math.signum(object.getX() - this.getX());
		this.xMovement = 0;
		this.setLocation(this.xPos, this.yPos);
		return false;
	}
	
	public boolean collitionY(Component object)
	{
		while(getSolidObjectAt((int)xPos, (int)yPos) != null)
			this.yPos -= Math.signum(object.getY() - this.getY());
		this.yMovement = 0;
		this.setLocation(this.xPos, this.yPos);
		return false;
	}
}
