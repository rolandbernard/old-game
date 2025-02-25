package net.game;

import java.awt.*;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class EditorChoice extends JComponent
{
	protected Representation rep = null;
	protected Image curImage = null;
	
	protected JLabel nameLable = null;
	
	public EditorChoice() {
		this.setBounds(0, 0, 32, 32);
		nameLable = new JLabel("--");
		nameLable.setBounds(10,0,500,10);
		this.add(nameLable);
	}
	
	public void setRepresentation(Representation rep)
	{
		this.rep = rep;
	}
	
	public void setName(String name)
	{
		nameLable.setText(name);
		super.setName(name);
	}
	
	public void changeImage(Image newImage)
	{
		this.curImage = newImage;
		
		this.setSize(Math.max(this.curImage.getWidth(this), 500), this.curImage.getHeight(this));
	}
	
	public int getImageHeight()
	{
		if(curImage != null)
			return this.curImage.getHeight(this);
		else
			return 0;
	}
	
	public int getImageWidth()
	{
		if(curImage != null)
			return this.curImage.getWidth(this);
		else
			return 0;
	}
	
	public void paint(Graphics g)
	{
			
		g.setColor(new Color(150,150,150,50));
		g.fillRect(0, 0, getImageWidth(), getImageHeight());
		
			
		if(rep != AssetLoader.nothing)
		{
			if(rep != null)
			{
				changeImage(this.rep.getFrame());
				
				if(this.curImage != null)
					g.drawImage(this.curImage, 0, 0, this);
			}
		}
		else
		{
			changeImage(this.rep.getFrame());
			g.drawRect(0, 0, curImage.getWidth(this), curImage.getHeight(this));
		}
		super.paint(g);
	}
}
