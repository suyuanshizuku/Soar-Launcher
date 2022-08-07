package me.eldodebug.soarlauncher.management.scene.impl;

import java.awt.Color;
import java.io.File;

import org.lwjgl.opengl.GL11;

import me.eldodebug.soarlauncher.SoarLauncher;
import me.eldodebug.soarlauncher.gui.Gui;
import me.eldodebug.soarlauncher.management.scene.Scene;
import me.eldodebug.soarlauncher.utils.ClientUtils;
import me.eldodebug.soarlauncher.utils.animation.simple.SimpleAnimation;
import me.eldodebug.soarlauncher.utils.font.FontUtils;
import me.eldodebug.soarlauncher.utils.minecraft.MinecraftJson;
import me.eldodebug.soarlauncher.utils.minecraft.MinecraftJsonParser;
import me.eldodebug.soarlauncher.utils.mouse.MouseUtils;
import me.eldodebug.soarlauncher.utils.mouse.MouseUtils.Scroll;

public class SoarLiteScene extends Scene{
	
	private float scrollY;
	private SimpleAnimation scrollAnimation = new SimpleAnimation(0.0F);
	
	public SoarLiteScene() {
		super("Soar Lite", true);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		
		int offsetY = 0;
		int index = 1;
		
		FontUtils.drawString(FontUtils.regular_bold34, "Soar Lite Launcher", 65, 15, new Color(80, 80, 80));
		
		//Render Change log
		Gui.drawSmoothRound(80, 90, 350, 250, 12, -1);
		
		FontUtils.drawString(FontUtils.regular_bold24, "Change log", 90, 95, new Color(85, 85, 85));
		
		Gui.drawRectangle(80, 135, 350, 2, new Color(238, 240, 245).getRGB());
		
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor(55, 194, 376, 205);
		
		for(String s : SoarLauncher.instance.changelogManager.getSoarLiteChangelogs()) {
			FontUtils.drawString(FontUtils.regular22, s, 95, 140 + offsetY + scrollAnimation.getValue(), new Color(85, 85, 85));
			offsetY+=24;
			index++;
		}
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		
		//Launch Button
		Gui.drawSmoothRound(SoarLauncher.instance.getWidth() - 250, SoarLauncher.instance.getHeight() - 85, 225, 65, 12, -1);
		FontUtils.drawString(FontUtils.regular_bold24, this.getInfo(), SoarLauncher.instance.getWidth() - (FontUtils.regular_bold24.getWidth(this.getInfo()) / 2) - 138, SoarLauncher.instance.getHeight() - 73, new Color(85, 85, 85));
		
		//Scroll
		Scroll scroll = MouseUtils.scroll();
		
		if(scroll != null) {
        	switch (scroll) {
        	case DOWN:
        		if(scrollY > -((index - 9) * 24)) {
            		scrollY -=20;
        		}
        		break;
            case UP:
            	if(scrollY < -10) {
        			scrollY += 20;
            	}
        		break;
        	}
		}
		
		scrollAnimation.setAnimation(scrollY, 16);
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY) {
		if(MouseUtils.isInside(mouseX, mouseY, SoarLauncher.instance.getWidth() - 250, SoarLauncher.instance.getHeight() - 85, 225, 65)) {
			if(this.getInfo().equals("Please Login")) {
				SoarLauncher.instance.authManager.webViewLogin();
			}else if(this.getInfo().equals("Launch")) {
				this.setInfo("Launching...");
				
				new Thread() {
					@Override
					public void run() {
				        ClientUtils.downloadClient(SoarLauncher.instance.sceneManager.getScenesByName("Soar Lite"));
				        MinecraftJson json = MinecraftJsonParser.parseJson(new File(SoarLauncher.instance.fileManager.getSoarLiteDir(), "SoarLiteClient.json"));
				        ClientUtils.downloadJava(SoarLauncher.instance.sceneManager.getScenesByName("Soar Lite"));
						ClientUtils.downloadLibraries(SoarLauncher.instance.sceneManager.getScenesByName("Soar Lite"), json.getLibraries());
						ClientUtils.downloadNatives(SoarLauncher.instance.sceneManager.getScenesByName("Soar Lite"), json.getNativeLibraries());
						ClientUtils.launchClient(SoarLauncher.instance.sceneManager.getScenesByName("Soar Lite"), json.getLibraries());
					}
				}.start();
			}
		}
	}
}
