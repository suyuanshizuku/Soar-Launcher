package me.eldodebug.soarlauncher.management.auth;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import me.eldodebug.soarlauncher.SoarLauncher;
import me.eldodebug.soarlauncher.utils.Logger;
import openauth.microsoft.MicrosoftAuthResult;
import openauth.microsoft.MicrosoftAuthenticationException;
import openauth.microsoft.MicrosoftAuthenticator;

public class AuthManager {

	private String username;
	private String uuid;
	private String token;
	private String refreshToken;
	
	public AuthManager() {
		if(SoarLauncher.instance.fileManager.getAccountFile().length() == 0) {
			SoarLauncher.instance.sceneManager.setInfoToLogin();
		}else {
			loadAccounts();
		}
	}

	public void webViewLogin() {
		new Thread() {
			@Override
			public void run() {
				MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
				try {
					SoarLauncher.instance.sceneManager.setInfoToLoading();
					MicrosoftAuthResult acc = authenticator.loginWithWebview();
					Logger.info("Logging...");
					SoarLauncher.instance.authManager.setUsername(acc.getProfile().getName());
					SoarLauncher.instance.authManager.setUuid(acc.getProfile().getId());
					SoarLauncher.instance.authManager.setToken(acc.getAccessToken());
					SoarLauncher.instance.authManager.setRefreshToken(acc.getRefreshToken());
					saveAccounts();
					Logger.info("Success Login!");
					SoarLauncher.instance.sceneManager.setInfoToLaunch();
				} catch (MicrosoftAuthenticationException e) {
					SoarLauncher.instance.sceneManager.setInfoToLogin();
					Logger.error("Field Login!");
					Logger.error(e.getMessage());
				}
			}
		}.start();
	}
	
	public void refreshTokenLogin() {
		SoarLauncher.instance.sceneManager.setInfoToLoading();
		MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
		
		if(!SoarLauncher.instance.fileManager.getAccountFile().exists() || SoarLauncher.instance.fileManager.getAccountFile().length() == 0) {
			SoarLauncher.instance.sceneManager.setInfoToLogin();
			return;
		}
		
		try {
			Logger.info("Logging...");
			MicrosoftAuthResult acc = authenticator.loginWithRefreshToken(SoarLauncher.instance.authManager.getRefreshToken());
			SoarLauncher.instance.authManager.setToken(acc.getAccessToken());
			Logger.info("Success Login!");
			SoarLauncher.instance.sceneManager.setInfoToLaunch();
		} catch (MicrosoftAuthenticationException e) {
			SoarLauncher.instance.sceneManager.setInfoToLogin();
			Logger.error("Field Login!");
			Logger.error(e.getMessage());
		}
	}
	
	public void saveAccounts() {
		
		ArrayList<String> toSave = new ArrayList<String>();
		
		toSave.add("username:" + SoarLauncher.instance.authManager.getUsername());
		toSave.add("uuid:" + SoarLauncher.instance.authManager.getUuid());
		toSave.add("token:" + SoarLauncher.instance.authManager.getToken());
		toSave.add("refreshtoken:" + SoarLauncher.instance.authManager.getRefreshToken());
		
		try {
			PrintWriter pw = new PrintWriter(SoarLauncher.instance.fileManager.getAccountFile());
			for (String str : toSave) {
				pw.println(str);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			Logger.error(e.getMessage());
		}
	}
	
	public void loadAccounts() {

		ArrayList<String> lines = new ArrayList<String>();
		
		if(!SoarLauncher.instance.fileManager.getAccountFile().exists() || SoarLauncher.instance.fileManager.getAccountFile().length() == 0) {
			return;
		}
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(SoarLauncher.instance.fileManager.getAccountFile()));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			Logger.error(e.getMessage());
		}
		
		for (String s : lines) {
			String[] args = s.split(":");
			
			if(!StringUtils.containsIgnoreCase(lines.toString(), "username:") || !StringUtils.containsIgnoreCase(lines.toString(), "uuid:")
					|| !StringUtils.containsIgnoreCase(lines.toString(), "token:")|| !StringUtils.containsIgnoreCase(lines.toString(), "refreshtoken:")) {
				SoarLauncher.instance.sceneManager.setInfoToLogin();
			}else {
				if (s.toLowerCase().startsWith("username:")) {
					setUsername(args[1]);
				}
				if (s.toLowerCase().startsWith("uuid:")) {
					setUuid(args[1]);
				}
				if (s.toLowerCase().startsWith("token:")) {
					setToken(args[1]);
				}
				if (s.toLowerCase().startsWith("refreshtoken:")) {
					setRefreshToken(args[1]);
				}
			}
		}
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
}