package net.game;

public class OneUpObject extends PowerUpObject {

	public OneUpObject() {
		super(AssetLoader.oneUp);	
	}
	
	public void kill()
	{
		rep = AssetLoader.oneUpBreak;
		((Animation)rep).restart();
		super.kill();
		//Thread.dumpStack();
	}
	
	public boolean update(double dt)
	{
		if(killed && ((FinishingAnimation)rep).finished())
		{
			this.remove();
		}
		
		return super.update(dt);
	}
}
