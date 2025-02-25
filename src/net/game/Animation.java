package net.game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Animation implements Representation
{
	protected Image[] frames = null;
	protected long animationTime = 0;
	protected long startedAt = 0;
	protected int lastFrame = -1;

	public Animation(String[] imagenames, long animationTime)
	{
		frames = new Image[imagenames.length];
		
		for(int i = 0; i < imagenames.length; i++)
		{
			if(imagenames[i] != null)
			{
				try {
					this.frames[i] = ImageIO.read(new File(imagenames[i]));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		this.animationTime = animationTime;
		this.restart();
	}
	
	public Animation(Animation animation)
	{
		this.animationTime = animation.getAnimationTime();
		this.frames = animation.getFrames();
	}
	
	public abstract Representation clone();
	
	public long getAnimationTime()
	{
		return this.animationTime;
	}
	
	public Image[] getFrames()
	{
		return this.frames;
	}
	
	public void restart()
	{
		this.lastFrame = -1;
		this.startedAt = System.nanoTime();
	}
}
