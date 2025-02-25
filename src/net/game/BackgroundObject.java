package net.game;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JComponent;

public class BackgroundObject extends JComponent 
{
	protected Representation rep = null;
	protected Image curImage = null;
	
	public BackgroundObject(Representation rep)
	{
		this.rep = rep;
		changeImage(this.rep.getFrame());
	}
	
	public void changeImage(Image newImage)
	{
		this.curImage = newImage;
		
		this.setSize(this.curImage.getWidth(this), this.curImage.getHeight(this));
	}
	
	public void paint(Graphics g)
	{
		changeImage(this.rep.getFrame());
		
		if(this.curImage != null)
			g.drawImage(this.curImage, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}
