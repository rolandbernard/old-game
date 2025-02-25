package net.game;

import java.awt.Component;
import java.awt.Graphics;

public class SpecialBreakableObject extends SpecialObject {

	protected Representation normalRepresentation = null;
	protected FinishingAnimation breakAnimation = null;
	
	public SpecialBreakableObject(Representation normalRepresentation, FinishingAnimation breakAnimation) 
	{
		super(normalRepresentation);
		this.normalRepresentation = normalRepresentation;
		this.breakAnimation = breakAnimation;
		G = 0;
	}

	public boolean collitionX(Component object) {
		if(object instanceof MovingObject)
			if((object instanceof PlayerObject || object instanceof HandObject)  && !((GameObject)object).getKilled())
			{
				rep = breakAnimation;
				((Animation)rep).restart();
				this.kill();
				this.ignore = true;
			} 
		return false;
	}

	public boolean collitionY(Component object) {
		if(object instanceof MovingObject)
		{
			if((object instanceof PlayerObject || object instanceof HandObject) && !((GameObject)object).getKilled())
			{
				rep = breakAnimation;
				((Animation)rep).restart();
				this.kill();
				this.ignore = true;
			}
		}
		return false;
	}
	
	public void paint(Graphics g)
	{
		if(killed && ((FinishingAnimation)rep).finished())
		{
			PointObject newPoint = new PointObject();
			newPoint.setLocation(getX() + getHeight()/2 - newPoint.getWidth()/2, getY() + getHeight()/2 - newPoint.getHeight()/2);
			this.getParent().add(newPoint);
			this.remove();
		}
		else
			super.paint(g);
	}

}
