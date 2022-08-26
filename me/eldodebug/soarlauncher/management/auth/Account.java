package me.eldodebug.soarlauncher.management.auth;

import java.awt.Color;

import me.eldodebug.soarlauncher.utils.TimerUtils;

public class Account {

	private String username;
	private String uuid;
	private String token;
	private String refreshToken;
	private String info;
	private Color infoColor;
	public TimerUtils timer;
	
	public Account(String username, String uuid, String token, String refreshToken) {
		this.username = username;
		this.uuid = uuid;
		this.token = token;
		this.refreshToken = refreshToken;
		this.info = "";
		this.infoColor = new Color(85, 85, 85);
		this.timer = new TimerUtils();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Color getInfoColor() {
		return infoColor;
	}

	public void setInfoColor(Color infoColor) {
		this.infoColor = infoColor;
	}
}
