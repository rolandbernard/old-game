package net.game;

import java.awt.*;
import java.util.Vector;

import javax.swing.*;

public abstract class GameObject extends JComponent
{
	protected double xPos = 0;
	protected double yPos = 0;
	
	protected boolean killed = false;
	
	protected Representation rep = null;
	protected Image curImage = null;
	
	protected boolean ignore = false;
	
	public GameObject(Representation rep)
	{
		this.rep = rep;
		this.killed = false;
		this.xPos = 0;
		this.yPos = 0;
		changeImage(this.rep.getFrame());
	}
	
	public void paint(Graphics g)
	{
		this.paint(g, false);
	}
	
	public void paint(Graphics g, boolean invert)
	{
		if(this.getParent().getParent() instanceof EditorPanel)
		{
			g.setColor(new Color(150,150,150,50));
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		
		changeImage(this.rep.getFrame());
		
		if(this.curImage != null)
			g.drawImage(this.curImage, (invert ? this.getWidth() : 0)
					, 0, (invert ? -this.getWidth() : this.getWidth()), this.getHeight(), this);
	}
	
	public void changeImage(Image newImage)
	{
		this.curImage = newImage;
		
		this.setSize(this.curImage.getWidth(this), this.curImage.getHeight(this));
	}
	
	public void setLocation(int x, int y)
	{
		this.xPos = x;
		this.yPos = y;
		super.setLocation(x, y);
	}
	
	public void setLocation(double x, double y)
	{
		this.xPos = x;
		this.yPos = y;
		super.setLocation((int)x, (int)y);
	}
	
	/**
	 * Kontrolliert ob das Objekt - verschoben zur ???bergebenen x- und y-Position - mit 
	 * einem anderen Objekt kollidiert. Ist das der Fall, so wird das andere kollidierende 
	 * Objekt zur???ck geliefert.<br>
	 * Es wird ebenfalls der contentPane des Formulars zur???ck geliefert, falls das Objekt 
	 * au???erhalb des Formulars positioniert werden sollte, d. h. ???ber den Rand des 
	 * Formulars hinaus ragen w???rde
	 * @param x die zu kontrollierende x-Koordinate
	 * @param y die zu kontrollierende y-Koordinate
	 * @return das Objekt das mit dem Objekt kollidiert oder - falls das Objekt an der 
	 * ???bergebenen Position au???erhalb des Frames positioniert wird - wird der contentPane 
	 * zur???ck geliefert. Liefert null zur???ck, falls das Objekt ohne ???berdeckung an der 
	 * ???bergebenen Position positioniert werden kann
	 */
	public Component getObjectAt(int x, int y) {
		return getObjectAt(x, y, getWidth(), getHeight());
	}
	
	public Component getObjectAt(int x, int y, int width, int height) {
		Component ret = null;
		if (this.getParent() != null && !ignore) {
			// Kontrolliere ob neue Position au???erhalb des Frames liegt
			{
				// Kontrolliere ob sich die neue Position mit anderen Objekten ???berdeckt
				Rectangle neuePosition = 
					new Rectangle(x,y,width,height);
				// Gehe alle Objekte des Formulars durch und vergleiche ihre Position mit der 
				// neuen Position
				Component[] komponenten = this.getParent().getComponents();
				int i = 0;
			  while (komponenten != null && i < komponenten.length && ret == null) {
			  	// Wenn das Objekt nicht das zu kontrollierende Objekt ist und das Objekt
			  	// mit dem zu Kontrollierendem zusammenf???llt
			  	if (komponenten[i] != this && 
			  		neuePosition.intersects(komponenten[i].getBounds()) && !(((GameObject) komponenten[i]).getIgnore()))
			  			ret = komponenten[i];
			  	i++;
			  }
			}
		}
		return ret;
	}
	
	public Component[] getAllObjectsAt(int x, int y) {
		return getAllObjectsAt(x, y, getWidth(), getHeight());
	}
	
	public Component[] getAllObjectsAt(int x, int y, int width, int height)
	{
		Vector<Component> ret = new Vector<>();
		
		if (this.getParent() != null && !ignore) 
		{
				// Kontrolliere ob sich die neue Position mit anderen Objekten ???berdeckt
				Rectangle neuePosition = 
					new Rectangle(x,y,width,height);
				// Gehe alle Objekte des Formulars durch und vergleiche ihre Position mit der 
				// neuen Position
				Component[] komponenten = this.getParent().getComponents();
				int i = 0;
			  while (komponenten != null && i < komponenten.length) 
			  {
			  	// Wenn das Objekt nicht das zu kontrollierende Objekt ist und das Objekt
			  	// mit dem zu Kontrollierendem zusammenf???llt
			  	if (komponenten[i] != this && komponenten[i] != null &&
			  		neuePosition.intersects(komponenten[i].getBounds()) && !(((GameObject) komponenten[i]).getIgnore()))
			  			ret.add(komponenten[i]);
			  	i++;
			}
		}
		
		
		return ret.toArray(new Component[ret.size()]);
	}
	
	public Component getSolidObjectAt(int x, int y)
	{
		return getSolidObjectAt(x, y, getWidth(), getHeight());
	}
	
	public Component getSolidObjectAt(int x, int y, int width, int height)
	{
		Component ret = null;
		Component[] components = getAllObjectsAt(x, y, width, height);
		for(int i = 0; i < components.length && ret == null; i++)
			if(!(components[i] instanceof SpecialObject))
				ret = components[i];
		return ret;
	}
	
	public boolean getIgnore()
	{
		return ignore;
	}
	
	public void kill() {
		this.killed = true;
	}
	
	public boolean getKilled()
	{
		return this.killed;
	}
	
	/**
	 * Objekt stirbt, indem es sich selbst???ndig vom contentPane des Formulars entfernt.
	 * Dabdurch wird das Objekt auch nicht mehr angezeigt
	 */
	public void remove()
	{
		if (this.getParent() != null) {
			this.getParent().remove(this);
		}
	}
	
	public abstract boolean collitionX(Component object);
	public abstract boolean collitionY(Component object);
}
