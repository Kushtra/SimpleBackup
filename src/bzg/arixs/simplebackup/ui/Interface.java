package bzg.arixs.simplebackup.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import bzg.arixs.simplebackup.utils.Backup;
import bzg.arixs.simplebackup.utils.FileSystem;

public class Interface {

	private final String destination = System.getProperty("user.home") + "\\AppData\\Roaming\\SimpleBackup\\Backup";
	
	private boolean type;
	private final double VERSION = 1.0;
	private final String TITLE = "Simple Backup "+VERSION;
	private String path;
	
	private FileSystem fs;
	private Backup bu;
	private JFrame frame;
	private JPanel panel;
	private JButton ff = new JButton("Folder");
	private JButton save = new JButton("Backup");
	private JButton find = new JButton("Select");
	private JButton open = new JButton("Open");
	private JLabel label = new JLabel("");
	
	
	public Interface() {
		fs = new FileSystem();
		bu = new Backup();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) { e.printStackTrace(); }
		
		panel = new JPanel(new FlowLayout());
		panel.setBackground(new Color(128, 64, 255));
		
		frame = new JFrame(TITLE);
		frame.setSize(360, 86);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		
		initLabel();
		
		panel.add(ff);
		panel.add(find);
		panel.add(save);
		panel.add(open);
		panel.add(label);
		
		ff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				type = !type;
				updateLabelAndButton();
			}
		});
		find.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateFile();
			}
		});
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backup();
			}
		});
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				open();
			}
		});
		
		frame.setVisible(true);
	}
	
	private void backup() {
		if(path == null) {
			JOptionPane.showMessageDialog(null, "Please select a file first!");
		} else {
			JOptionPane.showMessageDialog(null, bu.backup(path, destination));
		}
	}
	
	private void open() {
		fs.open();
	}
	
	private void initLabel() {
		if(fs.saved()) {
			if(!type) {
				String savedFolder = fs.getSavedFolder();
				path = savedFolder;
				label.setText(savedFolder);
			} else {
				String savedFile = fs.getSavedFile();
				path = savedFile;
				label.setText(savedFile);
			}
		}
	}
	
	private void updateLabelAndButton() {
		if(!type) {
			label.setText(fs.getSavedFolder());
			ff.setText("Folder");
		} else {
			label.setText(fs.getSavedFile());
			ff.setText(" File ");
		}
	}
	
	private String chooseFile() {
		JFileChooser jfc = new JFileChooser();
		if(!type) {
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		} else if(type) {
			jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		}
		int returnValue = jfc.showOpenDialog(null);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			return jfc.getSelectedFile().getAbsolutePath();
		} else {
			return null;
		}
	}
	
	private void updateFile() {
		String newString = chooseFile();
		if(newString != null) {
			if(!type) {
				fs.setLastSavedFolder(newString);
			} else {
				fs.setLastSavedFile(newString);
			}
			path = newString;
			label.setText(newString);
		}
	}
	
}
