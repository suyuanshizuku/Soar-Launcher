package me.eldodebug.soarlauncher.management.setting;

public class SettingsManager {

	private int clientMemory;
	
	public SettingsManager() {
		clientMemory = 3;
	}

	public int getClientMemory() {
		return clientMemory;
	}

	public void setClientMemory(int clientMemory) {
		this.clientMemory = clientMemory;
	}
}
