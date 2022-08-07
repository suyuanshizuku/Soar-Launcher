package me.eldodebug.soarlauncher.gui;

import java.awt.Color;

import me.eldodebug.soarlauncher.SoarLauncher;
import me.eldodebug.soarlauncher.utils.animation.normal.Animation;
import me.eldodebug.soarlauncher.utils.animation.normal.Direction;
import me.eldodebug.soarlauncher.utils.animation.normal.impl.DecelerateAnimation;
import me.eldodebug.soarlauncher.utils.font.FontUtils;

public class GuiSplashScreen {

	private static Animation backgroundAnimation;
	private static Animation fontAnimation;
	private static Animation fontUpdateAnimation;
	private static boolean loadedBackground = false;
	private static boolean loadedWelcomeMessage = false;
	private static boolean loadedUpdateMessage = false;
	
	public static void drawScreen() {

		String text = "Welcome to Soar Launcher!";
		String textUpddate = "Please update the Soar Launcher!";
		
		if(!loadedWelcomeMessage) {
			loadedWelcomeMessage = true;
			fontAnimation = new DecelerateAnimation(1000, 255);
		}

		if(SoarLauncher.instance.loaded) {
			fontAnimation.setDirection(Direction.BACKWARDS);
			
			if(SoarLauncher.instance.isUpdate()) {
				if(fontAnimation.isDone(Direction.BACKWARDS) && !loadedUpdateMessage) {
					loadedUpdateMessage = true;
					fontUpdateAnimation = new DecelerateAnimation(1000, 255);
				}
			}else {
				if(fontAnimation.isDone(Direction.BACKWARDS) && !loadedBackground) {
					loadedBackground = true;
					backgroundAnimation = new DecelerateAnimation(1000, 255);
				}
			}
		}
		
		Gui.drawRectangle(0, 0, SoarLauncher.instance.getWidth(), SoarLauncher.instance.getHeight(), new Color(0, 0, 0, 255 - (loadedBackground ? (int) backgroundAnimation.getValue() : 0)).getRGB());
		FontUtils.drawString(FontUtils.regular40, text, SoarLauncher.instance.getWidth() / 2 - (FontUtils.regular40.getWidth(text) / 2), SoarLauncher.instance.getHeight() / 2 - (FontUtils.regular40.getHeight(text) / 2) - 5, new Color(255, 255, 255, (loadedWelcomeMessage ? (int) fontAnimation.getValue() : 0)));
		FontUtils.drawString(FontUtils.regular40, textUpddate, SoarLauncher.instance.getWidth() / 2 - (FontUtils.regular40.getWidth(textUpddate) / 2), SoarLauncher.instance.getHeight() / 2 - (FontUtils.regular40.getHeight(text) / 2) - 5, new Color(255, 255, 255, (loadedUpdateMessage ? (int) fontUpdateAnimation.getValue() : 0)));
	}
}
