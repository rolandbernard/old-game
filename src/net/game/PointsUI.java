package net.game;

import java.awt.*;
import javax.swing.*;

public class PointsUI extends JComponent {

	protected PlayerObject player;
	protected JLabel lable = null;
	
	public PointsUI(PlayerObject player) {
		this.player = player;
		lable = new JLabel("0000");
		lable.setBounds(17, 0, 75, 15);
		lable.setForeground(Color.GRAY);
		lable.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		this.add(lable);
	}
	
	public void paint(Graphics g)
	{
		lable.setText(" " + String.valueOf(player.getPoints()));
		super.paint(g);
		g.drawImage(AssetLoader.pointSprite.getFrame(), 0, 0, 15, 15, this);
	}
}
