package net.game;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite implements Representation
{
	protected Image image = null;

	public Sprite(String imagename)
	{
		if(imagename != null)
		{
			try {
				this.image = ImageIO.read(new File(imagename));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Sprite(Sprite sprite)
	{
		this.image = sprite.getFrame();
	}
	
	public Representation clone()
	{
		return new Sprite(this);
	}
	
	public Image getFrame() {
		return this.image;
	}
}
