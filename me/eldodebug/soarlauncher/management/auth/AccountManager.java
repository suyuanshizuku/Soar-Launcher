package me.eldodebug.soarlauncher.management.auth;

import java.awt.Color;
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

public class AccountManager {
	
	public Account currentAccount;
	
	private ArrayList<Account> accounts = new ArrayList<Account>();

	public void webViewLogin() {
		new Thread() {
			@Override
			public void run() {
				MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
				try {
					SoarLauncher.instance.sceneManager.setInfoToLoading();
					MicrosoftAuthResult acc = authenticator.loginWithWebview();
					Logger.info("Logging...");
					accounts.add(new Account(acc.getProfile().getName(), acc.getProfile().getId(), acc.getAccessToken(), acc.getRefreshToken()));
					currentAccount = SoarLauncher.instance.accountManager.getAccountByUsername(acc.getProfile().getName());
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
			MicrosoftAuthResult acc = authenticator.loginWithRefreshToken(SoarLauncher.instance.accountManager.currentAccount.getRefreshToken());
			SoarLauncher.instance.accountManager.currentAccount.setToken(acc.getAccessToken());
			Logger.info("Success Login!");
			SoarLauncher.instance.sceneManager.setInfoToLaunch();
		} catch (MicrosoftAuthenticationException e) {
			SoarLauncher.instance.sceneManager.setInfoToLogin();
			Logger.error("Field Login!");
			Logger.error(e.getMessage());
		}
	}
	
	public void refreshTokenLogin(Account ac, String refreshToken) {
		SoarLauncher.instance.sceneManager.setInfoToLoading();
		MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
		ac.setInfoColor(new Color(85, 85, 85));
		
		ac.setInfo("Logging...");
		
		try {
			Logger.info("Logging...");
			MicrosoftAuthResult acc = authenticator.loginWithRefreshToken(refreshToken);
			
			SoarLauncher.instance.accountManager.currentAccount.setToken(acc.getAccessToken());
			currentAccount = SoarLauncher.instance.accountManager.getAccountByUsername(acc.getProfile().getName());
			
			Logger.info("Success Login!");
			ac.setInfoColor(new Color(40, 255, 40));
			ac.setInfo("Success!");
			SoarLauncher.instance.sceneManager.setInfoToLaunch();
		} catch (MicrosoftAuthenticationException e) {
			SoarLauncher.instance.sceneManager.setInfoToLogin();
			Logger.error("Field Login!");
			ac.setInfoColor(new Color(255, 40, 40));
			ac.setInfo("Field!");
			Logger.error(e.getMessage());
		}
	}
	
	public void saveAccounts() {
		
		ArrayList<String> toSave = new ArrayList<String>();
		
		toSave.add("currentaccount:" + currentAccount.getUsername() + ":" + currentAccount.getUuid() + ":" + currentAccount.getToken() + ":" + currentAccount.getRefreshToken());
		
		for(Account a : SoarLauncher.instance.accountManager.getAccounts()) {
			toSave.add("account:" + a.getUsername() + ":" + a.getUuid() + ":" + a.getToken() + ":" + a.getRefreshToken());
		}

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
			
			if(!StringUtils.containsIgnoreCase(lines.toString(), "account:") || !StringUtils.containsIgnoreCase(lines.toString(), "currentaccount:")) {
				SoarLauncher.instance.sceneManager.setInfoToLogin();
			}else {
				
				if (s.toLowerCase().startsWith("currentaccount:")) {
					SoarLauncher.instance.accountManager.currentAccount = new Account(args[1], args[2], args[3], args[4]);
				}
				
				if (s.toLowerCase().startsWith("account:")) {
					SoarLauncher.instance.accountManager.getAccounts().add(new Account(args[1], args[2], args[3], args[4]));
				}
			}
		}
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}
	
	public Account getAccountByUsername(String username) {
		return accounts.stream().filter(account -> account.getUsername().equalsIgnoreCase(username)).findFirst().orElse(null);
	}
}