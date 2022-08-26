package me.eldodebug.soarlauncher.management.color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import me.eldodebug.soarlauncher.SoarLauncher;
import me.eldodebug.soarlauncher.utils.Logger;
import me.eldodebug.soarlauncher.utils.OSType;

public class ColorManager {

	private int red;
	private int green;
	private int blue;
	
	private int red2;
	private int green2;
	private int blue2;
	
	public ColorManager() {
		
		OSType osType = OSType.getType();
		
		if(osType.equals(OSType.WINDOWS)) {
			ArrayList<String> lines = new ArrayList<String>();
			
			if(!SoarLauncher.instance.fileManager.getSoarConfigFile().exists()) {
				return;
			}
			
			try {
				BufferedReader reader = new BufferedReader(new FileReader(SoarLauncher.instance.fileManager.getSoarConfigFile()));
				String line = reader.readLine();
				while (line != null) {
					lines.add(line);
					line = reader.readLine();
				}
				reader.close();
			} catch (Exception e) {
				Logger.error(e.getMessage());
			}
			
			for (String s : lines) {
				String[] args = s.split(":");
				
				if(!StringUtils.containsIgnoreCase(lines.toString(), "color:") || !StringUtils.containsIgnoreCase(lines.toString(), "color2:")) {
					setColorBlack();
				}else {
					if (s.toLowerCase().startsWith("color:")) {
						if(StringUtils.isNumeric(args[1]) || StringUtils.isNumeric(args[2]) || StringUtils.isNumeric(args[3])) {
							this.setRed(Integer.parseInt(args[1]));
							this.setGreen(Integer.parseInt(args[2]));
							this.setBlue(Integer.parseInt(args[3]));
						}else {
							setColorBlack();
						}
					}
					if (s.toLowerCase().startsWith("color2:")) {
						if(StringUtils.isNumeric(args[1]) || StringUtils.isNumeric(args[2]) || StringUtils.isNumeric(args[3])) {
							this.setRed2(Integer.parseInt(args[1]));
							this.setGreen2(Integer.parseInt(args[2]));
							this.setBlue2(Integer.parseInt(args[3]));
						}else {
							setColorBlack();
						}
					}
				}
			}
		}
	}
	
	private void setColorBlack() {
		this.setRed(0);
		this.setGreen(0);
		this.setBlue(0);
		this.setRed2(0);
		this.setGreen2(0);
		this.setBlue2(0);
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

	public int getRed2() {
		return red2;
	}

	public void setRed2(int red2) {
		this.red2 = red2;
	}

	public int getGreen2() {
		return green2;
	}

	public void setGreen2(int green2) {
		this.green2 = green2;
	}

	public int getBlue2() {
		return blue2;
	}

	public void setBlue2(int blue2) {
		this.blue2 = blue2;
	}
}
