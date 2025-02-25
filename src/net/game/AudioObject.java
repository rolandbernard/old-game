package net.game;

import java.io.*;
import javax.sound.sampled.*;

public class AudioObject {

	protected Clip clip = null;
	
	public AudioObject(String filename)
	{
		try {
	          AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filename)); 

	         this.clip = AudioSystem.getClip();
	         this.clip.open(audioIn);
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	}
	
	public void loop(int count)
	{
		this.clip.loop(count);
	}
	
	public void start()
	{
        this.clip.loop(1);
	}
	
	public void stop()
	{
		this.clip.stop();
	}
}
