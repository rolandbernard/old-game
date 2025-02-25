package net.game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {

	protected int xOff = 0;
	protected int yOff = 0;
	
	public BackgroundPanel()
	{
		super();
		this.setBackground(Color.BLACK);
		this.setLayout(null);
		this.setOpaque(false);
	}
	
	public void setOffset(int xOff, int yOff)
	{
		this.xOff = xOff;
		this.yOff = yOff;
	}
	
	public Component getObjectAt(int x, int y, int width, int height) {
		Component ret = null;
		if (this.getParent() != null) {
			{
				Rectangle neuePosition = 
					new Rectangle(x,y,width,height);
				Component[] komponenten = this.getComponents();
				int i = 0;
			  while (komponenten != null && i < komponenten.length && ret == null) {
			  	if (komponenten[i] != this && neuePosition.intersects(komponenten[i].getBounds()))
			  			ret = komponenten[i];
			  	i++;
			  }
			}
		}
		return ret;
	}
	
	public void paint(Graphics g)
	{
		g.translate(xOff, yOff);
		super.paint(g);
	}
}
