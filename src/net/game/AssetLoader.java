package net.game;

import java.io.*;
import java.util.*;

public class AssetLoader 
{	
	private static final String RepresentationFileName = "assets/representations.csv";
	private static final String ObjectFileName = "assets/objects.csv";
 	
	// All Representations
	private static Map<String, Representation> rep = null;
	public static Map<String, ObjectDesc> objects = null;
	
	// Nothing
	public static Sprite nothing = null;
	
	// OneUp
	public static LoopingAnimation oneUp = null;
	public static FinishingAnimation oneUpBreak = null;
	
	// Point
	public static LoopingAnimation point = null;
	public static Sprite pointSprite = null;
	public static FinishingAnimation pointBreak = null;
	
	// Player
	public static LoopingAnimation playerStandingAnimation = null;
	public static ValueControledAnimation playerRunningAnimation = null;
	public static LoopingAnimation playerChargingAnimation = null;
	public static FinishingAnimation playerHitingAnimation = null;
	public static FinishingAnimation playerJumpingUpAnimation = null;
	public static FinishingAnimation playerFallingDownAnimation = null;
	public static FinishingAnimation playerFallingFastAnimation = null;
	public static FinishingAnimation playerDeatAnimation = null;
	public static FinishingAnimation playerHangingAnimation = null;
	public static LoopingAnimation playerOnTheEdgeAnimation = null;
	public static FinishingAnimation playerStartAnimation = null;
	public static FinishingAnimation playerStandingDownAnimation = null;
	public static FinishingAnimation playerEndAnimation = null;
	
	// Hand
	public static ValueControledAnimation handAnimation = null;
	
	public static String[] createNames(String start, int numOfFrames)
	{
		String[] ret = new String[numOfFrames];
		for(int i = 0; i < numOfFrames; i++)
		{
			ret[i] = start + "frame" + i + ".png";
		}
		return ret;
	}
	
	public static void loadAll()
	{
		rep = new HashMap<>();
		objects = new HashMap<>();


		// Nothing
		nothing = new Sprite("assets/textures/nothing.png");

		// OneUps
		oneUp = new LoopingAnimation(createNames("assets/textures/oneUp/", 3), 500000000L);
		oneUpBreak = new FinishingAnimation(createNames("assets/textures/oneUpBreak/", 6), 500000000L);
		
		// Point
		point = new LoopingAnimation(createNames("assets/textures/pointsNormal/", 4),  300000000L);
		pointSprite = new Sprite("assets/textures/pointsNormal/frame0.png");
		pointBreak = new FinishingAnimation(createNames("assets/textures/pointsBreak/", 1),  1000L);
		
		// Player
		playerStandingAnimation = new LoopingAnimation(createNames("assets/textures/playerStanding/" , 11), 5000000000L);
		playerJumpingUpAnimation = new FinishingAnimation(createNames("assets/textures/playerJumpingUp/" , 4), 75000000L);
		playerRunningAnimation = new ValueControledAnimation(createNames("assets/textures/playerRunning/", 7));
		playerStartAnimation = new FinishingAnimation(createNames("assets/textures/playerStart/", 10), 800000000L);
		playerFallingDownAnimation = new FinishingAnimation(createNames("assets/textures/playerFalling/", 6), 150000000L);
		playerHangingAnimation = new FinishingAnimation(createNames("assets/textures/playerHolding/", 5), 200000000L);
		playerStandingDownAnimation = new FinishingAnimation(createNames("assets/textures/playerLow/", 5), 30000000L);
		playerOnTheEdgeAnimation = new LoopingAnimation(createNames("assets/textures/playerEdge/", 4), 200000000L);
		playerDeatAnimation = new FinishingAnimation(createNames("assets/textures/playerDeath/", 6), 1000000000L);
		playerChargingAnimation = new LoopingAnimation(createNames("assets/textures/playerCharging/", 10),  150000000L);
		playerHitingAnimation  = new FinishingAnimation(createNames("assets/textures/playerHitting/", 2), 30000000L);
		playerFallingFastAnimation = new FinishingAnimation(createNames("assets/textures/playerDiving/", 6), 200000000L);
		playerEndAnimation = new FinishingAnimation(createNames("assets/textures/playerEnd/", 25), 1000000000L);
		
		// Hand
		handAnimation = new ValueControledAnimation(createNames("assets/textures/hand/", 28));
		
		// Load objects from file
		try {
    		String line = null;
			// Load Representations
            BufferedReader bufferedReader = new BufferedReader(new FileReader(RepresentationFileName));
            
            while((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(",");
                Representation newRepresentation = null;
                
                switch(Integer.parseInt(splitLine[1]))
                {
                case 0:
                	newRepresentation = new Sprite(splitLine[2]);
                	break;
                case 1:
                	newRepresentation = new LoopingAnimation(createNames(splitLine[2], Integer.parseInt(splitLine[3])), Long.parseLong(splitLine[4]));
                	break;
                case 2:
                	newRepresentation = new FinishingAnimation(createNames(splitLine[2], Integer.parseInt(splitLine[3])), Long.parseLong(splitLine[4]));
                	break;
                default:
                	throw new NumberFormatException();
                }
                
                rep.put(splitLine[0], newRepresentation);
            }   

            bufferedReader.close();    
            
    		// Load Objects
            bufferedReader = new BufferedReader(new FileReader(ObjectFileName));
            
            while((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(",");
                ObjectDesc newObjectDesc = new ObjectDesc();
                
                newObjectDesc.typeID = Integer.parseInt(splitLine[1]);
                newObjectDesc.rep = new Representation[splitLine.length - 2];
                
                for(int i = 0; i < newObjectDesc.rep.length; i++)
                	newObjectDesc.rep[i] = rep.get(splitLine[i + 2]);
                
                objects.put(splitLine[0], newObjectDesc);
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
	}
}
