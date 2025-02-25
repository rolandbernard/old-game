package net.game;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Vector;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	protected int xOff = 0;
	protected int yOff = 0;
	
	public GamePanel()
	{
		super();
		this.setLayout(null);
		this.setOpaque(false);
		this.setDoubleBuffered(false);
	}
	
	public void setOffset(int xOff, int yOff)
	{
		this.xOff = xOff;
		this.yOff = yOff;
	}
	
	public void update(double dt)
	{
		//Component[] components = this.getComponents();
		Component[] components = this.getAllMovingObjectsAt(-xOff - 200, -yOff - 200, this.getWidth() + 400, this.getHeight() + 400);
		for(Component c : components)
		{
			if(((MovingObject)c).update(dt))
				break;
		}
	}
	
	public void paint(Graphics g)
	{
		g.translate(xOff, yOff);
		super.paint(g);
	}

	public Component getObjectAt(int x, int y, int width, int height) {
		Component ret = null;
		
			Rectangle neuePosition = 
				new Rectangle(x,y,width,height);
			Component[] komponenten = this.getComponents();
			int i = 0;
		  while (komponenten != null && i < komponenten.length && ret == null) {
		  	if (komponenten[i] != this && neuePosition.intersects(komponenten[i].getBounds()))
		  			ret = komponenten[i];
		  	i++;
		  }
		
		return ret;
	}
	
	public Component[] getAllMovingObjectsAt(int x, int y, int width, int height)
	{
		Vector<Component> ret = new Vector<>();
		
		// Kontrolliere ob sich die neue Position mit anderen Objekten ???berdeckt
		Rectangle neuePosition = 
			new Rectangle(x,y,width,height);
		// Gehe alle Objekte des Formulars durch und vergleiche ihre Position mit der 
		// neuen Position
		Component[] komponenten = this.getComponents();
		int i = 0;
	  while (komponenten != null && i < komponenten.length) 
	  {
	  	// Wenn das Objekt nicht das zu kontrollierende Objekt ist und das Objekt
	  	// mit dem zu Kontrollierendem zusammenf???llt
	  	if (komponenten[i] != this && komponenten[i] instanceof MovingObject && neuePosition.intersects(komponenten[i].getBounds()))
	  			ret.add(komponenten[i]);
	  	i++;
	  }
		
		
		return ret.toArray(new Component[ret.size()]);
	}
}
