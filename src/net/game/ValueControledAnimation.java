package net.game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ValueControledAnimation implements Representation 
{
	protected Image[] frames = null;
	
	protected int value = 0;
	
	public ValueControledAnimation(String[] imagenames)
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
	}
	
	public ValueControledAnimation(ValueControledAnimation rep)
	{
		this.frames = rep.getFrames();
	}
	
	public Representation clone()
	{
		return new ValueControledAnimation(this);
	}
	
	public Image[] getFrames()
	{
		return this.frames;
	}
	
	public void setValue(int value)
	{
		if(value < this.frames.length && value != this.value)
		{
			this.value = value;
		}
	}
	
	public Image getFrame() 
	{
		return this.frames[this.value];
	}

}
