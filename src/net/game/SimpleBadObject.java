package net.game;

import java.awt.Component;

public class SimpleBadObject extends BadStaticObject {

	public SimpleBadObject(Representation rep) {
		super(rep);
	}

	public boolean collitionX(Component object) {
		return false;
	}

	public boolean collitionY(Component object) {
		return false;
	}

}
