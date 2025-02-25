package net.game;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class EditorFrame extends JFrame
{
	protected EditorPanel panel = null;
	protected JFileChooser fileChooser = null;
	
	protected JButton saveButton = null;
	protected JButton loadButton = null;
	protected JButton newObjectButton = null;
	
	public EditorFrame(String title, int width, int height)
	{
		setTitle(title);
		setBounds(10, 10, width, height);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBackground(new Color(0, 12, 30));
		setLayout(null);
		
		Container contentPane = getContentPane();
		
		
		fileChooser = new JFileChooser("assets/level/");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		loadButton = new JButton("Load");
		loadButton.setBounds(getInsets().left + 5, getInsets().top + 5, 75, 25);

		loadButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					panel.load(fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		saveButton = new JButton("Save");
		saveButton.setBounds(getInsets().left + 85, getInsets().top + 5, 75, 25);
		
		saveButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					panel.save(fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		
		newObjectButton = new JButton("Add object");
		newObjectButton.setBounds(getInsets().left + 165, getInsets().top + 5, 110, 25);
		
		newObjectButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
			}
		});

		contentPane.add(saveButton);
		contentPane.add(loadButton);
		//contentPane.add(newObjectButton);
		
		panel = new EditorPanel();
		panel.setBounds(0, 0, width, height);
		
		contentPane.add(panel);
		
		setVisible(true);
	}
	
	public void paint(Graphics g)
	{
		panel.setSize(getWidth(), getHeight());
		super.paint(g);
		repaint();
	}
}
