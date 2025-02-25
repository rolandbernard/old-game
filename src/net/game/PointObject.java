package net.game;

import java.awt.Component;
import java.awt.Graphics;

public class PointObject extends SpecialObject {
	
	protected boolean collected = false;
	
	public PointObject() {
		super(AssetLoader.point.clone());
		G = 0;
	}

	public boolean collitionX(Component object) {
		if(object instanceof PlayerObject)
		{
			kill();
		}
		return false;
	}

	public boolean collitionY(Component object) {
		if(object instanceof PlayerObject)
		{
			kill();
		}
		return false;
	}
	
	public boolean getKilled()
	{
		if(killed)
		{
			if(collected)
				return true;
			else
			{
				collected = true;
				return false;
			}
		}
		else
			return false;
	}
	
	public void kill()
	{
		rep = AssetLoader.pointBreak.clone();
		((Animation)rep).restart();
		super.kill();
	}
	
	public boolean update(double dt)
	{
		if(killed && ((FinishingAnimation)rep).finished())
		{
			this.remove();
		}
		return super.update(dt);
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
	}
}
