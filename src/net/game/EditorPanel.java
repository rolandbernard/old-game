package net.game;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class EditorPanel extends JPanel
{
	protected ObjectDesc[] objects = null;
	protected String[] names = null;
	protected int currentObject = 0;
	protected EditorChoice preview = null;
	
	protected int xOff = 0;
	protected int yOff = 0;
	
	protected boolean mouseDown = false;
	
	protected int lastMouseX = 0;
	protected int lastMouseY = 0;
	
	protected GamePanel gamePanel = null;
	protected BackgroundPanel backgroundPanel = null;
	protected BackgroundPanel backgroundPanelFar = null;
	protected BackgroundPanel backgroundPanelVeryFar = null;
	
	
	public EditorPanel()
	{
		super();
		this.setLayout(null);
		this.setDoubleBuffered(false);
		setBackground(new Color(0, 12, 30));
		
		preview = new EditorChoice();
		this.add(preview);
		
		objects = AssetLoader.objects.values().toArray(new ObjectDesc[AssetLoader.objects.size()]);
		names = AssetLoader.objects.keySet().toArray(new String[AssetLoader.objects.keySet().size()]);
		
		changeObjectBy(0);
		
		this.addMouseListener(new MouseListener()
		{
			public void mouseReleased(MouseEvent e) {
			}
			
			public void mousePressed(MouseEvent e) {
			}
			
			public void mouseExited(MouseEvent e) {
				lastMouseX = e.getX();
				lastMouseY = e.getY();
			}
			
			public void mouseEntered(MouseEvent e) {
				lastMouseX = e.getX();
				lastMouseY = e.getY();
			}
			
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1)
				{
					addThisObject();
				}
				else
				{
					preview.setLocation(-xOff + e.getX(), -yOff + e.getY());
					if(gamePanel.getObjectAt(e.getX() - xOff - 1, e.getY() - yOff - 1, 2, 2) != null)
						gamePanel.remove(gamePanel.getObjectAt(e.getX() - xOff - 1, e.getY() - yOff - 1, 2, 2));
					else if(backgroundPanel.getObjectAt(e.getX() - xOff - 1, e.getY() - yOff - 1, 2, 2) != null)
						backgroundPanel.remove(backgroundPanel.getObjectAt(e.getX() - xOff - 1, e.getY() - yOff - 1, 2, 2));
					else if(backgroundPanelFar.getObjectAt(e.getX() - xOff/10 - 1, e.getY() - yOff/10 - 1, 2, 2) != null)
						backgroundPanelFar.remove(backgroundPanelFar.getObjectAt(e.getX() - xOff/10 - 1, e.getY() - yOff/10 - 1, 2, 2));
					else if(backgroundPanelVeryFar.getObjectAt(e.getX() - xOff/100 - 1, e.getY() - yOff/100 - 1, 2, 2) != null)
						backgroundPanelVeryFar.remove(backgroundPanelVeryFar.getObjectAt(e.getX() - xOff/100 - 1, e.getY() - yOff/100 - 1, 2, 2));
				}
			}
		});
		
		this.addMouseWheelListener(new MouseWheelListener()
		{
			public void mouseWheelMoved(MouseWheelEvent e) {
				changeObjectBy(e.getWheelRotation());
			}
		});
		
		this.addMouseMotionListener(new MouseMotionListener()
		{
			public void mouseMoved(MouseEvent e) {
				lastMouseX = e.getX();
				lastMouseY = e.getY();
			}
			
			public void mouseDragged(MouseEvent e) {
				xOff -= lastMouseX - e.getX();
				yOff -= lastMouseY - e.getY();
				

				lastMouseX = e.getX();
				lastMouseY = e.getY();
			}
		});
		
		gamePanel = new GamePanel();
		gamePanel.setBounds(0, 0, getWidth(), getHeight());
		
		backgroundPanel = new BackgroundPanel();
		backgroundPanel.setBounds(0, 0, getWidth(), getHeight());
		
		backgroundPanelFar = new BackgroundPanel();
		backgroundPanelFar.setBounds(0, 0, getWidth(), getHeight());
		
		backgroundPanelVeryFar = new BackgroundPanel();
		backgroundPanelVeryFar.setBounds(0, 0, getWidth(), getHeight());
		
		this.add(gamePanel);
		this.add(backgroundPanel);
		this.add(backgroundPanelFar);
		this.add(backgroundPanelVeryFar);
	}
	
	public void reset()
	{
		gamePanel.removeAll();
		backgroundPanel.removeAll();
		backgroundPanelFar.removeAll();
		backgroundPanelVeryFar.removeAll();
		
		xOff = 0;
		yOff = 0;
		
		changeObjectBy(0);
	}
	
	public void reload()
	{
		objects = AssetLoader.objects.values().toArray(new ObjectDesc[AssetLoader.objects.size()]);
		names = AssetLoader.objects.keySet().toArray(new String[AssetLoader.objects.keySet().size()]);
		
		currentObject = 0;
		changeObjectBy(0);
	}
	
	public void load(String filename)
	{
		reset();
		LevelLoader.loadLevel(filename, gamePanel, backgroundPanel, backgroundPanelFar, backgroundPanelVeryFar);
	}
	
	public void save(String filename)
	{
		LevelLoader.saveLevel(filename, gamePanel, backgroundPanel, backgroundPanelFar, backgroundPanelVeryFar);
	}
	
	public void changeObjectBy(int delta)
	{
		currentObject += delta;
		if(currentObject < -5)
			currentObject += objects.length + 5;
		if(currentObject >= objects.length)
			currentObject -= objects.length + 5;

		switch(currentObject)
		{
		case -1:
			preview.setRepresentation(AssetLoader.playerStandingAnimation);
			preview.setName("Player");
			break;
		case -2:
			preview.setRepresentation(AssetLoader.point);
			preview.setName("Point");
			break;
		case -3:
			preview.setRepresentation(AssetLoader.nothing);
			preview.setName("Save");
			break;
		case -4:
			preview.setRepresentation(AssetLoader.oneUp);
			preview.setName("OneUp");
			break;
		case -5:
			preview.setRepresentation(AssetLoader.nothing);
			preview.setName("End");
			break;
		default:
			preview.setRepresentation(objects[currentObject].rep[0]);
			preview.setName(names[currentObject]);
				break;
		}
	}
	
	public void addThisObject()
	{
		JComponent newObject = null;
		  
    switch(currentObject)
    {
    case -1:
    	newObject = new PlayerObject();
    	((PlayerObject) newObject).setState(PlayerState.STANDING);
    	break;
    case -2:
    	newObject = new PointObject();
    	break;
    case -3:
    	newObject = new SavePointObject();
    	break;
    case -4:
    	newObject = new OneUpObject();
    	break;
    case -5:
    	newObject = new FinishingObject();
    	break;
		default:
        {
        	 ObjectDesc newObjectDesc = objects[currentObject];
             
             switch(newObjectDesc.typeID)
             {
             case 0:
             	newObject = new BreakableObject(newObjectDesc.rep[0].clone(), (FinishingAnimation) newObjectDesc.rep[1].clone());
             	break;
             case 1:
             	newObject = new SpecialBreakableObject(newObjectDesc.rep[0].clone(), (FinishingAnimation) newObjectDesc.rep[1].clone());
             	break;
             case 2:
             	newObject = new SimpleGoodObject(newObjectDesc.rep[0].clone());
             	break;
             case 3:
             	newObject = new SimpleBadObject(newObjectDesc.rep[0].clone());
             	break;
             case 4:
             	newObject = new EnemyObject(newObjectDesc.rep[0].clone(), newObjectDesc.rep[1].clone(), (FinishingAnimation) newObjectDesc.rep[2].clone());
             	break;
             case 5:
             case 7:
             case 8:
             	newObject = new BackgroundObject(newObjectDesc.rep[0]);
             	break;
             case 6:
            	newObject = new BouncyObject(newObjectDesc.rep[0].clone(), (FinishingAnimation) newObjectDesc.rep[1].clone());
            	break;
             default:
             	throw new NumberFormatException();
             }
        }
        	break;
        }
        
        if(newObject instanceof BackgroundObject)
        {	
					 switch(objects[currentObject].typeID)
					 {
					 case 5:
						 backgroundPanel.add(newObject);
						 backgroundPanel.setComponentZOrder(newObject, 0);
			       newObject.setLocation(-xOff + preview.getX(), -yOff + preview.getY());
						 break;
					 case 7:
						 backgroundPanelFar.add(newObject);
						 backgroundPanelFar.setComponentZOrder(newObject, 0);
			       newObject.setLocation(-xOff/10 + preview.getX(), -yOff/10 + preview.getY());
						 break;
					 case 8:
						 backgroundPanelVeryFar.add(newObject);
						 backgroundPanelVeryFar.setComponentZOrder(newObject, 0);
			       newObject.setLocation(-xOff/100 + preview.getX(), -yOff/100 + preview.getY());
						 break;
					 }
        }
        else
        {
        	gamePanel.add(newObject);
        	gamePanel.setComponentZOrder(newObject, 0);
          newObject.setLocation(-xOff + preview.getX(), -yOff + preview.getY());
        }
        
    	newObject.setName(preview.getName());
	}
	
	public void paint(Graphics g)
	{
		backgroundPanel.setSize(getWidth(), getHeight());
		backgroundPanelFar.setSize(getWidth(), getHeight());
		backgroundPanelVeryFar.setSize(getWidth(), getHeight());
		gamePanel.setSize(getWidth(), getHeight());
	
		preview.setLocation(lastMouseX - preview.getImageWidth()/2, lastMouseY - preview.getImageHeight()/2);
		
		gamePanel.setOffset(xOff, yOff);
		backgroundPanel.setOffset(xOff, yOff);
		backgroundPanelFar.setOffset(xOff/10, yOff/10);
		backgroundPanelVeryFar.setOffset(xOff/100, yOff/100);
		
		super.paint(g);
	}
}
