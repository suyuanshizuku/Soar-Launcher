package soarlauncher.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import soarlauncher.utils.ClientUtils;
import soarlauncher.utils.DialogType;
import soarlauncher.utils.FileUtils;

public class Config {

	/*
	 * Minecraft memory setting
	 */
	public static int MEMORY = 3;
	
	/*
	 * Launcher Dark Mode
	 */
	public static boolean DARK_MODE = false;
	
	/*
	 * Save launcher settings
	 */
	public static void save() {
		
		ArrayList<String> toSave = new ArrayList<String>();
		
		toSave.add("/*");
		toSave.add("* Memory settings in Minecraft");
		toSave.add("*/");
		toSave.add("Memory:" + MEMORY);
		
		toSave.add("\n");
		
		toSave.add("/*");
		toSave.add("* Launcher dark mode");
		toSave.add("*/");
		toSave.add("DarkMode:" + DARK_MODE);
		
		try {
			PrintWriter pw = new PrintWriter(FileUtils.CONFIG_FILE);
			for (String str : toSave) {
				pw.println(str);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			ClientUtils.showDialog(DialogType.ERROR, e.getMessage());
		}
	}
	
	/*
	 * Load launcher settings
	 */
	public static void load() {
		
		ArrayList<String> lines = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(FileUtils.CONFIG_FILE));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			ClientUtils.showDialog(DialogType.ERROR, e.getMessage());
		}
		
		for (String s : lines) {
			String[] args = s.split(":");
			
			if (s.toLowerCase().startsWith("memory:")) {
				Config.MEMORY = Integer.parseInt(args[1]);
			}
			
			if (s.toLowerCase().startsWith("darkmode:")) {
				Config.DARK_MODE = Boolean.parseBoolean(args[1]);
			}
		}
	}
}
