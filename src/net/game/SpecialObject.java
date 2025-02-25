package net.game;

import java.awt.Component;

public abstract class SpecialObject extends MovingObject {

	public SpecialObject(Representation rep) {
		super(rep);
	}
	
	public boolean collitionX(Component object) {
		return false;
	}
	
	public boolean collitionY(Component object) {
		return false;
	}
}
