package net.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class GameInputHandler implements KeyListener
{
	protected static Map<String, Boolean> keys = null;
	protected static Map<String, Boolean> keysNew = null;
	protected static Map<Integer, String> keyBindings = null;
	
	public static boolean getKey(String keyname)
	{
		if(!keys.get(keyname))
		{
			keysNew.put(keyname, true);
			return false;
		}
		else
			return true;
	}
	
	public static boolean isNew(String keyname)
	{
		if(keysNew.get(keyname))
		{
			keysNew.put(keyname, false);
			return true;
		}
		else
			return false;
	}
	
	public static boolean nothingPressed()
	{
		return !keys.values().contains(true);
	}
	
	public GameInputHandler()
	{
		if(keys == null)
		{
			keys = new HashMap<>();
			keysNew = new HashMap<>();
			
			// All keys
			keys.put("left", false);
			keys.put("right", false);
			keys.put("up", false);
			keys.put("down", false);
			keys.put("jump", false);
			keys.put("hit", false);
			
			keysNew.put("left", false);
			keysNew.put("right", false);
			keysNew.put("up", false);
			keysNew.put("down", false);
			keysNew.put("jump", false);
			keysNew.put("hit", false);
		}
		if(keyBindings == null)
		{
			keyBindings = new HashMap<>();
			
			// All keys
			keyBindings.put(KeyEvent.VK_A, "left");
			keyBindings.put(KeyEvent.VK_D, "right");
			keyBindings.put(KeyEvent.VK_W, "up");
			keyBindings.put(KeyEvent.VK_S, "down");
			keyBindings.put(KeyEvent.VK_LEFT, "left");
			keyBindings.put(KeyEvent.VK_RIGHT, "right");
			keyBindings.put(KeyEvent.VK_UP, "up");
			keyBindings.put(KeyEvent.VK_DOWN, "down");
			keyBindings.put(KeyEvent.VK_SPACE, "jump");
			keyBindings.put(KeyEvent.VK_SHIFT, "hit");
		}
	}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
		String name = keyBindings.get(e.getKeyCode());
		if(name != null)
		{
			keys.put(name, true);
		}
	}

	public void keyReleased(KeyEvent e) {
		String name = keyBindings.get(e.getKeyCode());
		if(name != null)
			keys.put(name, false);
	}
	
}
