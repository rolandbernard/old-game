package net.game;

import java.awt.*;
import javax.swing.*;

public class ObjectEditorFrame extends JFrame {

	protected static final String[] types  = {
			"Breakable object",
			"Special breakable object",
			"Simple good object",
			"Simble bad object",
			"Enemy object",
			"Background object",
			"Bouncy object"
	};
	
	protected JComboBox<String> type = null;
	protected JLabel[] path = null;
	protected JButton[] button = null;
	protected JFileChooser fileChooser = null;
	protected JButton end = null;
	
	public ObjectEditorFrame() {
		setTitle("Add object");
		setBounds(10, 10, 300, 200);
		setLayout(null);
		
		fileChooser = new JFileChooser("assete/textures/");
		
		Container contentPane = getContentPane();
		Insets insets = getInsets();
		
		type = new JComboBox<>(types);
		type.setSelectedItem(types[2]);
		type.setBounds(insets.left + 10, insets.top + 10, 150, 20);
		
		contentPane.add(type);
	}

}
