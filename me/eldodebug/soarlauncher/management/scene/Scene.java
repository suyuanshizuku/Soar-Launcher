package me.eldodebug.soarlauncher.management.scene;

import me.eldodebug.soarlauncher.utils.animation.simple.SimpleAnimation;

public class Scene {

	private String name;
	private String info;
	public SimpleAnimation fontAnimation = new SimpleAnimation(0.0F);
	private boolean client;
	
	public Scene(String name, boolean client) {
		this.name = name;
		this.client = client;
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

	public boolean isClient() {
		return client;
	}

	public void setClient(boolean client) {
		this.client = client;
	}
}
