package net.game;

import java.awt.Image;

public abstract interface Representation {
	
	public abstract Image getFrame();
	public abstract Representation clone();
	
}
