package soarlauncher;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import soarlauncher.config.Config;
import soarlauncher.utils.ClientUtils;
import soarlauncher.utils.DialogType;
import soarlauncher.utils.FileUtils;

public class SoarLauncher extends JFrame {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * Instance
	 */
	private static SoarLauncher instance = new SoarLauncher();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
					SoarLauncher frame = new SoarLauncher();
					
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setTitle("Soar Launcher");
					frame.setBounds(10, 10, 340, 130);
					frame.setResizable(false);
					frame.setVisible(true);
					
				    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
				    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
				    
				    frame.setLocation(x, y);
				    
				} catch (Exception e) {
					ClientUtils.showDialog(DialogType.ERROR, e.getMessage());
				}
			}
		});
	}
	
	private void init() {
		FileUtils.createDir();
		Config.load();
		
		if(Config.DARK_MODE) {
			FlatDarkLaf.setup();
		}else {
			FlatLightLaf.setup();
		}
	}

	/**
	 * Create the frame.
	 */
	public SoarLauncher() {
		
		/*
		 * Launcher initialization
		 */
		this.init();
		
		/*
		 * Main Frame
		 */
		JPanel mainFrame = new JPanel();
		
		mainFrame.setLayout(null);
		
		//Launch button
		JButton launchButton = new JButton("Launch");
		
		launchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				launchButton.setEnabled(false);
				
				new Thread() {
					@Override
					public void run() {
						try {
							launchButton.setText("Downloading...");
							if(!ClientUtils.isDownloaded()) {
								ClientUtils.downloadData();
							}
							ClientUtils.downloadOptifine();
							ClientUtils.downloadSoar();
							launchButton.setText("Launching!");
							ClientUtils.launchSoar();
						} catch (IOException e) {
							ClientUtils.showDialog(DialogType.ERROR, e.getMessage());
						}	
					}
				}.start();
			}
		});
		
		launchButton.setBounds(112, 15, 100, 30);
		mainFrame.add(launchButton);
		
		/*
		 * Settings Frame
		 */
		JPanel settingsFrame = new JPanel();
		
		//Memory Label
		JLabel memoryLabel = new JLabel();
		
		memoryLabel.setText("Memory: " + Config.MEMORY + " GB");
		
		//Memory slider setting
		JSlider memorySlider = new JSlider(1, 32);
		memorySlider.setValue(Config.MEMORY);
		
		memorySlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				Config.MEMORY = memorySlider.getValue();
				memoryLabel.setText("Memory: " + Config.MEMORY + " GB");
				Config.save();
			}
			
		});
		
		//Dark mode check box
		JCheckBox darkModeCheckBox = new JCheckBox();
		
		darkModeCheckBox.setText("DarkMode");
		darkModeCheckBox.setSelected(Config.DARK_MODE);
		
		darkModeCheckBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Config.DARK_MODE = darkModeCheckBox.isSelected();
				Config.save();
				ClientUtils.showDialog(DialogType.INFO, "Restart required to apply");
			}
			
		});
		
		settingsFrame.add(memoryLabel);
		settingsFrame.add(memorySlider);
		
		settingsFrame.add(darkModeCheckBox);
		
		/*
		 * Tab System
		 */
		
		JTabbedPane tab = new JTabbedPane();
		
		tab.add("Main", mainFrame);
		tab.add("Settings", settingsFrame);
		
		this.getContentPane().add(tab, BorderLayout.CENTER);
	}
    
	public static SoarLauncher getInstance() {
		return instance;
	}
}
