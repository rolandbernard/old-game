package net.game;

public class UpdateThread extends Thread
{
	public GamePanel game = null;
	protected long lastUpdate = 0;
	protected boolean ended = false;
	
	public UpdateThread(GamePanel game) {
		this.game = game;
	}
	
	public void run()
	{
		while(!ended)
		{
			if(lastUpdate == 0)
				lastUpdate = System.nanoTime();
			
			long thisTime = System.nanoTime();
			double dt = Math.min((thisTime - lastUpdate) / 1000000000.0, 0.1);
			lastUpdate = thisTime;
			game.update(dt);
		}
	}
	
	public void end()
	{
		ended = true;
	}

}
