package net.game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame
{

	protected JButton newGame = null;
	protected JButton editor = null;
	
	protected JFileChooser levelSelector = null;
	
	public MainFrame() throws HeadlessException {
		setTitle("Game");
		setBounds(10, 10, 150, 100);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Insets insets = getInsets();
		Container contentPain = getContentPane();
		
		newGame = new JButton();
		newGame.setBounds(insets.left + 5, insets.top + 5, 110, 30);
		newGame.setText("New game");
		newGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) {
				if(levelSelector.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
				{
					GameFrame game = new GameFrame("Game", 750, 750, levelSelector.getSelectedFile().getAbsolutePath());
					game.setResizable(false);
				}
			}
		});
		
		editor = new JButton();
		editor.setBounds(insets.left + 5, insets.top + 35,  110, 30);
		editor.setText("Editor");
		editor.addActionListener(new ActionListener()
		{
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				EditorFrame editor = new EditorFrame("Editor", 500, 500);
			}
		});
		
		levelSelector = new JFileChooser("assets/level/");
		levelSelector.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		contentPain.add(newGame);
		contentPain.add(editor);
		
		setVisible(true);
	}

}
