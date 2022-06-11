package soar.utils.theme;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import soar.utils.FileUtils;

public class ThemeParser {

	private static File dataFile;
	public static EnumAccentColor accentColor;
	
	public static void init() {
		
		accentColor = EnumAccentColor.GREEN;
		
		dataFile = new File(FileUtils.minecraftFolder, "soar/config/theme/theme.txt");
		
		if(!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		ThemeParser.parseTheme();
	}
	
	public static void parseTheme() {
		
		ArrayList<String> lines = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(ThemeParser.dataFile));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (String s : lines) {
			String[] args = s.split(":");

			if (s.toLowerCase().startsWith("accentcolor:")) {
				ThemeParser.accentColor = ThemeParser.getAccentColorByName(args[1]);
			}
		}
	}
	
	public static EnumAccentColor getAccentColorByName(String color) {
		
		if(color.equals("BLUE")) {
			return EnumAccentColor.BLUE;
		}
		
		if(color.equals("GREEN")) {
			return EnumAccentColor.GREEN;
		}
		
		if(color.equals("MELON")) {
			return EnumAccentColor.MELON;
		}
		
		if(color.equals("PINK")) {
			return EnumAccentColor.PINK;
		}
		
		if(color.equals("PURPLE")) {
			return EnumAccentColor.PURPLE;
		}
		
		if(color.equals("RED")) {
			return EnumAccentColor.RED;
		}
		
		return EnumAccentColor.GREEN;
	}
}
