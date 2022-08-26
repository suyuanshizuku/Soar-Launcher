package me.eldodebug.soarlauncher.management.scene.impl;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import me.eldodebug.soarlauncher.SoarLauncher;
import me.eldodebug.soarlauncher.gui.Gui;
import me.eldodebug.soarlauncher.management.auth.Account;
import me.eldodebug.soarlauncher.management.scene.Scene;
import me.eldodebug.soarlauncher.utils.ColorUtils;
import me.eldodebug.soarlauncher.utils.animation.simple.SimpleAnimation;
import me.eldodebug.soarlauncher.utils.font.FontUtils;
import me.eldodebug.soarlauncher.utils.mouse.ClickType;
import me.eldodebug.soarlauncher.utils.mouse.MouseUtils;
import me.eldodebug.soarlauncher.utils.mouse.MouseUtils.Scroll;

public class AccountManagerScene extends Scene{

	private boolean delete;
	private Account deleteAccount;
	private SimpleAnimation scrollAnimation = new SimpleAnimation(0.0F);
	
	private double scrollY;
	
	public AccountManagerScene() {
		super("Account", true);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY) {
		
		int offsetY = 0;
		int index = 0;
		
		FontUtils.drawString(FontUtils.regular_bold34, "Soar Account Manager", 65, 15, new Color(80, 80, 80));
		
		FontUtils.drawString(FontUtils.regular22, "Add +", SoarLauncher.instance.getWidth() - 100, 27, ColorUtils.getClientColor(0));
		
		if(MouseUtils.isInside(mouseX, mouseY, SoarLauncher.instance.getWidth() - 110, 10, 100, 60)) {
			Gui.drawRectangle(SoarLauncher.instance.getWidth() - 101, 54, 57, 2, ColorUtils.getClientColor(0).getRGB());
		}
		
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor(0, 0, 3000, 457);
		
		for(Account a : SoarLauncher.instance.accountManager.getAccounts()) {
			Gui.drawSmoothRound(75, 80 + offsetY + scrollAnimation.getValue(), 850, 85, 12, -1);
			FontUtils.drawString(FontUtils.regular30, a.getUsername(), 100, 105 + offsetY + scrollAnimation.getValue(), new Color(85, 85, 85));
			FontUtils.drawString(FontUtils.regular22, a.getInfo(), SoarLauncher.instance.getWidth() - (FontUtils.regular30.getWidth(a.getInfo()) / 2) - 130, 112 + offsetY + scrollAnimation.getValue(), a.getInfoColor());
			FontUtils.drawString(FontUtils.icon26, "M", SoarLauncher.instance.getWidth() - (FontUtils.regular30.getWidth("M") / 2) - 70, 110 + offsetY + scrollAnimation.getValue(), new Color(255, 45, 45));
			
			if(a.getInfo().equals("Success!") || a.getInfo().equals("Field!")) {
				if(a.timer.delay(3500)) {
					a.setInfo("");
					a.timer.reset();
				}
			}else {
				a.timer.reset();
			}
			
			index++;
			offsetY+=105;
		}
		
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		
		if(delete) {
			delete = false;
			SoarLauncher.instance.accountManager.getAccounts().remove(deleteAccount);
		}
		
		//Scroll
		Scroll scroll = MouseUtils.scroll();
		
		if(scroll != null) {
        	switch (scroll) {
        	case DOWN:
        		if(scrollY > -((index - 4) * 105)) {
            		scrollY -=25;
        		}
        		break;
            case UP:
            	if(scrollY < -10) {
        			scrollY += 25;
            	}
        		break;
        	}
		}
		
		scrollAnimation.setAnimation((float) scrollY, 16);
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY) {
		
		int offsetY = 0;
		
		if(!SoarLauncher.instance.open) {
			
			if(MouseUtils.isInsideClick(mouseX, mouseY, SoarLauncher.instance.getWidth() - 110, 10, 100, 60, ClickType.LEFT)) {
				SoarLauncher.instance.accountManager.webViewLogin();
			}
			
			for(Account a : SoarLauncher.instance.accountManager.getAccounts()) {
				
				if(MouseUtils.isInsideClick(mouseX, mouseY, SoarLauncher.instance.getWidth() - 90, 105 + offsetY, 40, 40, ClickType.LEFT)) {
					deleteAccount = a;
					delete = true;
				}
				
				if(MouseUtils.isInsideClick(mouseX, mouseY, 75, 80 + offsetY + scrollAnimation.getValue(), 800, 85, ClickType.LEFT)) {
					new Thread() {
						@Override
						public void run() {
							SoarLauncher.instance.accountManager.refreshTokenLogin(a, a.getRefreshToken());
						}
					}.start();
				}
				
				offsetY+=105;
			}
		}
	}
}
