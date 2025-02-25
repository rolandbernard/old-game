package net.game;

import java.awt.*;

public class BouncyObject extends GoodStaticObject {

	protected Representation normalRepresentation = null;
	protected FinishingAnimation bounceAnimation = null;
	
	public BouncyObject(Representation normalRepresentation, FinishingAnimation bounceAnimation) {
		super(normalRepresentation);
		this.normalRepresentation = normalRepresentation;
		this.bounceAnimation = bounceAnimation;
	}

	public boolean collitionX(Component object) {
		rep = bounceAnimation;
		((Animation) rep).restart();
		return false;
	}

	public boolean collitionY(Component object) {
		rep = bounceAnimation;
		((Animation) rep).restart();
		return false;
	}
	
	public void paint(Graphics g)
	{
		if(rep == bounceAnimation && ((FinishingAnimation) rep).finished())
		{
			rep = normalRepresentation;
			if(rep instanceof Animation)
				((Animation) rep).restart();
		}
		
		super.paint(g);
	}
}
