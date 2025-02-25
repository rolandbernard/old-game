package net.game;

import java.awt.Component;
import java.awt.Graphics;

public class BreakableObject extends GoodStaticObject {

	protected Representation normalRepresentation = null;
	protected FinishingAnimation breakAnimation = null;
	
	public BreakableObject(Representation normalRepresentation, FinishingAnimation breakAnimation) 
	{
		super(normalRepresentation);
		this.normalRepresentation = normalRepresentation;
		this.breakAnimation = breakAnimation;
	}

	public boolean collitionX(Component object) {
		if(object instanceof MovingObject)
			if(((object instanceof PlayerObject && ((PlayerObject)object).getState() == PlayerState.DIVING) || object instanceof HandObject)  && !((GameObject)object).getKilled())
			{
				if(object instanceof PlayerObject)
				{
					((MovingObject) object).setMovement(0, 0);
					((PlayerObject) object).setState(PlayerState.STANDING);
				}
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
			if(((object instanceof PlayerObject && ((PlayerObject)object).getState() == PlayerState.DIVING) || object instanceof HandObject) && !((GameObject)object).getKilled())
			{
				if(object instanceof PlayerObject)
				{
					((MovingObject) object).setMovement(0, 0);
					((PlayerObject) object).setState(PlayerState.STANDING);
				}
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
			this.remove();
		}
		else
			super.paint(g);
	}
}
