package soar.utils.theme;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import soar.utils.FileUtils;

public class ThemeParser {

	private static File dataFile;
	public static int r1, g1, b1, r2, g2, b2;
	
	public static void init() {
		
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

			if (s.toLowerCase().startsWith("color:")) {
				r1 = Integer.parseInt(args[1]);
				g1 =  Integer.parseInt(args[2]);
				b1 = Integer.parseInt(args[3]);
			}
			
			if (s.toLowerCase().startsWith("color2:")) {
				r2 = Integer.parseInt(args[1]);
				g2 =  Integer.parseInt(args[2]);
				b2 = Integer.parseInt(args[3]);
			}
		}
	}
}
