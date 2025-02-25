package net.game;

import java.awt.Image;

public class FinishingAnimation extends Animation
{
	public FinishingAnimation(String[] imagenames, long animationTime) {
		super(imagenames, animationTime);
	}

	public FinishingAnimation(FinishingAnimation animation)
	{
		super(animation);
	}
	
	public Representation clone()
	{
		return new FinishingAnimation(this);
	}
	
	public Image getFrame() {
		int thisFrame = (int) ((System.nanoTime() - this.startedAt)*this.frames.length/animationTime);
		if(thisFrame >= this.frames.length)
			thisFrame = this.frames.length-1;
		if(thisFrame < 0)
			thisFrame = 0;
		
		this.lastFrame = thisFrame;
		return this.frames[thisFrame];
	}
	
	public boolean finished()
	{
		int thisFrame = (int) ((System.nanoTime() - this.startedAt)*this.frames.length/animationTime);
		if(thisFrame >= this.frames.length)
			return true;
		else
			return false;
	}
}
