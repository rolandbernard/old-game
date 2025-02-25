package net.game;

import java.awt.*;
import java.io.*;
import javax.swing.*;

public class LevelLoader {
	
	public static PlayerObject loadLevel(String level, Container game, Container background, Container backgroundFar, Container backgroundVeryFar)
	{
		PlayerObject ret = new PlayerObject();
		
		// Load objects from file
		try {
    		String line = null;
			// Load Representations
            BufferedReader bufferedReader = new BufferedReader(new FileReader(level));
            
            while((line = bufferedReader.readLine()) != null && line.length() > 0) {
                String[] splitLine = line.split(",");
                if(splitLine.length < 3)
                	break;
                
                JComponent newObject = null;
                
                switch(splitLine[0])
                {
                case "Player":
                	newObject = ret;
                	break;
                case "Point":
                	newObject = new PointObject();
                	break;
                case "Save":
                	newObject = new SavePointObject();
                	break;
                case "OneUp":
                	newObject = new OneUpObject();
                	break;
                case "End":
                	newObject = new FinishingObject();
                	break;
                default:
                {
                	 ObjectDesc newObjectDesc = AssetLoader.objects.get(splitLine[0]);
                     
                     switch(newObjectDesc.typeID)
                     {
                     case 0:
                     	newObject = new BreakableObject(newObjectDesc.rep[0], (FinishingAnimation) newObjectDesc.rep[1]);
                     	break;
                     case 1:
                     	newObject = new SpecialBreakableObject(newObjectDesc.rep[0], (FinishingAnimation) newObjectDesc.rep[1]);
                     	break;
                     case 2:
                     	newObject = new SimpleGoodObject(newObjectDesc.rep[0]);
                     	break;
                     case 3:
                     	newObject = new SimpleBadObject(newObjectDesc.rep[0]);
                     	break;
                     case 4:
                     	newObject = new EnemyObject(newObjectDesc.rep[0], newObjectDesc.rep[1], (FinishingAnimation) newObjectDesc.rep[2]);
                     	break;
                     case 5:
                     case 7:
                     case 8:
                     	newObject = new BackgroundObject(newObjectDesc.rep[0]);
                     	break;
                     case 6:
                    	newObject = new BouncyObject(newObjectDesc.rep[0], (FinishingAnimation) newObjectDesc.rep[1]);
                    	break;
                     default:
                     	throw new NumberFormatException();
                     }
                }
                	break;
                }
                
                newObject.setLocation(Integer.parseInt(splitLine[1]), Integer.parseInt(splitLine[2]));
                
                if(newObject instanceof BackgroundObject)
                {
                	 switch(AssetLoader.objects.get(splitLine[0]).typeID)
                	 {
                	 case 5:
                		 background.add(newObject);
                		 break;
                	 case 7:
                		 backgroundFar.add(newObject);
                		 break;
                	 case 8:
                		 backgroundVeryFar.add(newObject);
                		 break;
                	 }
                }
                else
                	game.add(newObject);
              
            	newObject.setName(splitLine[0]);
            }   

            bufferedReader.close();   
        }
		catch(NumberFormatException e){   
            e.printStackTrace();
		}
        catch(FileNotFoundException e) {      
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
		
        return ret;
	}
	
	public static void saveLevel(String filename, Container game, Container background, Container backgroundFar, Container backgroundVeryFar)
	{
		// TODO: all layers
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
		  
			Component[] components = game.getComponents();
			
			for(Component component : components)
			{
				if(component instanceof BackgroundObject || component instanceof GameObject)
				bufferedWriter.write(component.getName() + "," + component.getX() + "," + component.getY() + "\n");
			}
			

			components = background.getComponents();
			
			for(Component component : components)
			{
				if(component instanceof BackgroundObject || component instanceof GameObject)
				bufferedWriter.write(component.getName() + "," + component.getX() + "," + component.getY() + "\n");
			}
			
			components = backgroundFar.getComponents();
			
			for(Component component : components)
			{
				if(component instanceof BackgroundObject || component instanceof GameObject)
				bufferedWriter.write(component.getName() + "," + component.getX() + "," + component.getY() + "\n");
			}
			
			components = backgroundVeryFar.getComponents();
			
			for(Component component : components)
			{
				if(component instanceof BackgroundObject || component instanceof GameObject)
				bufferedWriter.write(component.getName() + "," + component.getX() + "," + component.getY() + "\n");
			}
		    
		    bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
