package me.eldodebug.soarlauncher.management.scene.impl;

import java.awt.Color;

import me.eldodebug.soarlauncher.SoarLauncher;
import me.eldodebug.soarlauncher.gui.Gui;
import me.eldodebug.soarlauncher.gui.setting.SettingMemory;
import me.eldodebug.soarlauncher.management.scene.Scene;
import me.eldodebug.soarlauncher.utils.ClientUtils;
import me.eldodebug.soarlauncher.utils.font.FontUtils;
import me.eldodebug.soarlauncher.utils.mouse.ClickType;
import me.eldodebug.soarlauncher.utils.mouse.MouseUtils;

public class SettingsScene extends Scene{

	private SettingMemory memorySettings = new SettingMemory();
	public static String progress = "Check Files";
	
	public SettingsScene() {
		super("Settings", false);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		
		memorySettings.setPosition(160, 115);
		
		//Draw Text
		FontUtils.drawString(FontUtils.regular_bold34, "Soar Launcher Settings", 65, 15, new Color(80, 80, 80));

		//Draw Background
		Gui.drawSmoothRound(75, 75, 300, 300, 12, -1);
		
		//Settings Memory
		FontUtils.drawString(FontUtils.regular22, "Memory: " + SoarLauncher.instance.settingsManager.getClientMemory() + "GB", 90, 90, new Color(85, 85,85));
		memorySettings.drawScreen(mouseX, mouseY);
		
		//Settings Logout
		Gui.drawRound(90, 160, 180, 50, 12, new Color(238, 240, 245).getRGB());
		FontUtils.drawString(FontUtils.regular22, "Logout", 144, 172, new Color(85, 85, 85));
		
		//Settings Check File
		Gui.drawRound(90, 230, 180, 50, 12, new Color(238, 240, 245).getRGB());
		FontUtils.drawString(FontUtils.regular22, progress, 124, 242, new Color(85, 85, 85));
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY) {
		memorySettings.mouseClicked(mouseX, mouseY);
		
		if(MouseUtils.isInsideClick(mouseX, mouseY, 90, 160, 180, 50, ClickType.LEFT)) {
			SoarLauncher.instance.authManager.webViewLogin();
		}
		
		if(MouseUtils.isInside(mouseX, mouseY, 90, 230, 180, 50)) {
			progress = "Checking...";
			new Thread() {
				@Override
				public void run() {
					ClientUtils.checkFiles();
				}
			}.start();
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		memorySettings.mouseReleased(mouseX, mouseY);
	}
}
