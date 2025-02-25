package net.game;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class GameFrame extends JFrame {
	
	protected GamePanel gamePanel = null;
	protected BackgroundPanel backgroundPanel = null;
	protected BackgroundPanel backgroundPanelFar = null;
	protected BackgroundPanel backgroundPanelVeryFar = null;

	protected JPanel uiPanel = null;
	protected JLabel endLable = null;
	
	public static final int SCALE = 1;
	
	protected PlayerObject player = null;
	
	protected UpdateThread updateThread = null;
	
	public GameFrame(String title, int width, int height, String level)
	{
		this.setTitle(title);
		this.setBounds(10, 10, width, height);
		this.addKeyListener(new GameInputHandler());
		this.setResizable(false);
		
		this.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				updateThread.end();
				try {
					updateThread.join();
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				dispose();
			}
			
		});
		
		Container contentPane = this.getContentPane();
		contentPane.setBackground(new Color(0, 12, 30));
		this.setBackground(new Color(0, 12, 30));
		
		this.gamePanel = new GamePanel();
		
		this.backgroundPanel = new BackgroundPanel();
		this.backgroundPanelFar = new BackgroundPanel();
		this.backgroundPanelVeryFar = new BackgroundPanel();
		
		this.uiPanel = new JPanel();
		this.uiPanel.setLayout(null);
		this.uiPanel.setOpaque(false);
		this.uiPanel.setDoubleBuffered(false);
		
		backgroundPanel.setSize(getWidth()/SCALE, getHeight()/SCALE);
		gamePanel.setSize(getWidth()/SCALE, getHeight()/SCALE);
		uiPanel.setSize(getWidth()/SCALE, getHeight()/SCALE);
		
		endLable = new JLabel("", SwingConstants.CENTER);
		endLable.setBackground(null);
		endLable.setOpaque(false);
		endLable.setBounds(0,0,0,0);
		endLable.setForeground(Color.GRAY);
		endLable.setFont(new Font("TimesRoman", Font.PLAIN, 50/SCALE));
		
		contentPane.add(endLable);
		contentPane.add(uiPanel);
		contentPane.add(gamePanel);
		contentPane.add(backgroundPanel);
		contentPane.add(backgroundPanelFar);
		contentPane.add(backgroundPanelVeryFar);
		
		player = LevelLoader.loadLevel(level, gamePanel, backgroundPanel, backgroundPanelFar, backgroundPanelVeryFar);
		player.saveSavePoint();
		
		PointsUI p = new PointsUI(player);
		p.setBounds(10, 10, 75, 15);
		
		uiPanel.add(p);
		
		updateThread = new UpdateThread(gamePanel);
		updateThread.start();
		
		this.setVisible(true);
	}
	
	public void shutdown()
	{
		updateThread.end();
		try {
			updateThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		setVisible(false);
		dispose();
	}
	
	public void paint(Graphics g)
	{
		backgroundPanel.setSize(getWidth()/SCALE + getInsets().left, getHeight()/SCALE + getInsets().top);
		backgroundPanelFar.setSize(getWidth()/SCALE + getInsets().left, getHeight()/SCALE + getInsets().top);
		backgroundPanelVeryFar.setSize(getWidth()/SCALE + getInsets().left, getHeight()/SCALE + getInsets().top);
		gamePanel.setSize(getWidth()/SCALE + getInsets().left, getHeight()/SCALE + getInsets().top);
		uiPanel.setSize(getWidth()/SCALE + getInsets().left, getHeight()/SCALE + getInsets().top);
	
		int xOff = player.getX() - getWidth()/2/SCALE + player.getWidth()/2;
		int yOff = player.getY() - getHeight()/2/SCALE + player.getHeight()/2;
		
		gamePanel.setOffset(-xOff, -yOff);
		backgroundPanel.setOffset(-xOff, -yOff);
		backgroundPanelFar.setOffset(-xOff/10, -yOff/10);
		backgroundPanelVeryFar.setOffset(-xOff/100, -yOff/100);
		
		BufferedImage buffer = new BufferedImage(this.getWidth()/SCALE + getInsets().left, this.getHeight()/SCALE + getInsets().top, BufferedImage.TYPE_INT_ARGB);
		Graphics newG = buffer.getGraphics();
		super.paint(newG);
		
		if(player.getFinished())
		{
			endLable.setBackground(new Color(20,20,20,150));
			endLable.setOpaque(true);
			endLable.setText("LEVEL CLEARED");
			endLable.setBounds(0,getHeight()/4,getWidth(),getHeight()/2);
			
			if(GameInputHandler.getKey("jump"))
			{
				setVisible(false);
				updateThread.end();
				try {
					updateThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				dispose();
			}
		}
		g.drawImage(buffer, 0, 0, this.getWidth() + getInsets().left + getInsets().right, this.getHeight() + getInsets().top + getInsets().bottom, this);
		repaint();
	}
}
