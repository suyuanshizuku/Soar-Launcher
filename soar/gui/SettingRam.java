package soar.gui;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import soar.Soar;
import soar.utils.ColorUtils;
import soar.utils.FileUtils;
import soar.utils.animation.Animation;
import soar.utils.mouse.MouseUtils;

public class SettingRam {

    private boolean dragging = false;
    private double renderWidth;
    private double renderWidth2;
    private float x, y;
    public int currentRam;
    
    private Animation animation = new Animation(0.0F);
    
    private File dataFile;
    
    public SettingRam() {
    	
		dataFile = new File(FileUtils.launcherDir, "Setting.txt");
		
    	if(!dataFile.exists()) {
    		try {
				dataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	currentRam = 4;
    	this.load();
    }
    
    public void setPosition(float x, float y) {
    	this.x = x;
    	this.y = y;
    }
    
	public void drawScreen(int mouseX, int mouseY) {
		
        double min = 2;
        double max = 16;
        double l = 140;

        renderWidth = (l) * (currentRam - min) / (max - min);
        renderWidth2 = (l) * (max - min) / (max - min);

        animation.setAnimation((float) renderWidth, 14);
        
        double diff = Math.min(l, Math.max(0, mouseX - (x - 70)));
        
        if (dragging) {
            if (diff == 0) {
            	currentRam = (int) min;
            }
            else {
                double newValue = roundToPlace(((diff / l) * (max - min) + min), 2);
                currentRam = (int) newValue;
            }
        }
        
        Gui.drawSmoothRound((float) (x - 70), (float) (y + 13), (float) (renderWidth2), 10, 9, new Color(245, 245, 245).getRGB());
        Gui.drawSmoothRound((float) (x - 70), (float) (y + 12), animation.getValue(), 11, 9, ColorUtils.getClientColor(0).getRGB());
        Gui.drawSmoothRound((float) (x) + animation.getValue() - 73, (float) (y + 8), 7, 20, 6, new Color(225, 225, 225).getRGB());
	}
	
    public void mouseClicked(int mouseX, int mouseY) {
        if (MouseUtils.isInside(mouseX, mouseY, x - 73, y + 10, renderWidth2, 20)) {
            dragging = true;
        }
    }
    
    public void mouseReleased(int mouseX, int mouseY) {
        dragging = false;
    }

    private double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    public void save() {
		ArrayList<String> toSave = new ArrayList<String>();
		
		toSave.add("Ram:" + currentRam);
		
		try {
			PrintWriter pw = new PrintWriter(this.dataFile);
			for (String str : toSave) {
				pw.println(str);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			Soar.instance.logger.info("File does not exist!");
			e.printStackTrace();
		}
    }
    
    public void load() {
		ArrayList<String> lines = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			Soar.instance.logger.info("File does not exist!");
			e.printStackTrace();
		}
		
		for (String s : lines) {
			String[] args = s.split(":");
			
			if (s.toLowerCase().startsWith("ram:")) {
				currentRam = Integer.parseInt(args[1]);
			}
		}
    }
}
