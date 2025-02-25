package net.game;

import java.awt.Image;

public class LoopingAnimation extends Animation
{
	public LoopingAnimation(String[] imagenames, long animationTime) {
		super(imagenames, animationTime);
	}
	
	public LoopingAnimation(LoopingAnimation animation)
	{
		super(animation);
	}
	
	public Representation clone()
	{
		return new LoopingAnimation(this);
	}
	
	public Image getFrame() {
		int thisFrame = (int) ((System.nanoTime() - this.startedAt)*this.frames.length/animationTime % this.frames.length);
	
		this.lastFrame = thisFrame;
		return this.frames[thisFrame];
	}
}
