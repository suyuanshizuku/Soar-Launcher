package me.eldodebug.soarlauncher.gui.setting;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import me.eldodebug.soarlauncher.SoarLauncher;
import me.eldodebug.soarlauncher.gui.Gui;
import me.eldodebug.soarlauncher.utils.ColorUtils;
import me.eldodebug.soarlauncher.utils.animation.simple.SimpleAnimation;
import me.eldodebug.soarlauncher.utils.mouse.MouseUtils;

public class SettingMemory {

    private boolean dragging = false;
    private double renderWidth;
    private double renderWidth2;
    private float x, y;
    
    private SimpleAnimation animation = new SimpleAnimation(0.0F);
    
    public void setPosition(float x, float y) {
    	this.x = x;
    	this.y = y;
    }
    
	public void drawScreen(int mouseX, int mouseY) {
		
        double min = 2;
        double max = 16;
        double l = 140;

        renderWidth = (l) * (SoarLauncher.instance.settingsManager.getClientMemory() - min) / (max - min);
        renderWidth2 = (l) * (max - min) / (max - min);

        animation.setAnimation((float) renderWidth, 14);
        
        double diff = Math.min(l, Math.max(0, mouseX - (x - 70)));
        
        if (dragging) {
            if (diff == 0) {
            	SoarLauncher.instance.settingsManager.setClientMemory((int) min);
            }
            else {
                double newValue = roundToPlace(((diff / l) * (max - min) + min), 2);
                SoarLauncher.instance.settingsManager.setClientMemory((int) newValue);
            }
        }
        
        Gui.drawSmoothRound((float) (x - 70), (float) (y + 13), (float) (renderWidth2), 10, 9, new Color(245, 245, 245).getRGB());
        Gui.drawSmoothRound((float) (x - 70), (float) (y + 12), animation.getValue(), 11, 9, ColorUtils.getClientColor(0).getRGB());
        Gui.drawSmoothRound((float) (x) + animation.getValue() - 73, (float) (y + 8), 7, 20, 6, new Color(225, 225, 225).getRGB());
	}
	
    public void mouseClicked(int mouseX, int mouseY) {
        if (MouseUtils.isInside(mouseX, mouseY, x - 73, y + 10, renderWidth2 + 10, 20)) {
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
}
