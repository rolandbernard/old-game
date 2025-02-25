package net.game;

import java.awt.Component;

public class SimpleGoodObject extends GoodStaticObject {

	public SimpleGoodObject(Representation rep) {
		super(rep);
	}

	public boolean collitionX(Component object) {
		return false;
	}

	public boolean collitionY(Component object) {
		return false;
	}
}
