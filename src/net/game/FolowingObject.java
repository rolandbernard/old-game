package net.game;

import java.awt.Component;

public abstract class FolowingObject extends SpecialObject {

	protected GameObject folowing = null;
	protected double strength = 0;
	protected double radius = 0;
	
	public FolowingObject(Representation rep, double strength, double radius) {
		super(rep);
		G = 0;
		this.strength = strength;
		this.radius = radius;
	}

	public boolean update(double dt)
	{
		if(folowing != null && folowing.getLocation().distance(getLocation()) > radius)
		{
			xMovement += (folowing.getX() + folowing.getWidth()/2 - getX() - getWidth()/2) * strength * dt;
			yMovement += (folowing.getY() + folowing.getHeight()/2 - getY() - getHeight()/2) * strength * dt;
		}
		else
		{
			xMovement = 0;
			yMovement = 0;
		}
		
		return super.update(dt);
	}
	
	public boolean collitionX(Component object) {
		if(object instanceof MovingObject)
		{
			folowing = (GameObject) object;
			ignore = true;
		}
		return false; 
	}

	public boolean collitionY(Component object) {
		if(object instanceof MovingObject)
		{
			folowing = (GameObject) object;
			ignore = true;
		}
		return false;
	}

}
