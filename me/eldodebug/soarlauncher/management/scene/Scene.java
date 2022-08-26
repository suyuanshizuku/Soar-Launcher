package me.eldodebug.soarlauncher.management.scene;

import me.eldodebug.soarlauncher.utils.animation.simple.SimpleAnimation;

public class Scene {

	private String name;
	private String info;
	public SimpleAnimation fontAnimation = new SimpleAnimation(0.0F);
	private boolean showSide;
	
	public Scene(String name, boolean showSide) {
		this.name = name;
		this.showSide = showSide;
		this.info = "Launch";
	}

	public void drawScreen(int mouseX, int mouseY) {}
	
	public void mouseClicked(int mouseX, int mouseY) {}
	
	public void mouseReleased(int mouseX, int mouseY) {}
	
	public String getName() {
		return name;
	}
	
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isShowSide() {
		return showSide;
	}

	public void setShowSide(boolean showSide) {
		this.showSide = showSide;
	}
}
