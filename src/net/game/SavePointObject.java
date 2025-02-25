package net.game;

public class SavePointObject extends SpecialObject {

	public SavePointObject() {
		super(AssetLoader.nothing);
		G = 0;
	}
	
	public boolean update(double dt)
	{
		return false;
	}
}
