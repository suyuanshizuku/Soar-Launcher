package soar.auth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import soar.Soar;
import soar.openauth.microsoft.MicrosoftAuthResult;
import soar.openauth.microsoft.MicrosoftAuthenticationException;
import soar.openauth.microsoft.MicrosoftAuthenticator;
import soar.utils.FileUtils;

public class AuthProgress {

	private boolean firstLogin;
	private File dataFile;
	
	public AuthProgress() {
		
		dataFile = new File(FileUtils.launcherDir, "Account.txt");
		
		if(!dataFile.exists()) {
			firstLogin = true;
		}else {
			firstLogin = false;
		}
	}
	
	public void webViewLogin() {
		new Thread() {
			@Override
			public void run() {
				MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
				try {
					MicrosoftAuthResult acc = authenticator.loginWithWebview();
					Soar.instance.setUsername(acc.getProfile().getName());
					Soar.instance.setId(acc.getProfile().getId());
					Soar.instance.setToken(acc.getAccessToken());
					Soar.instance.setRefreshToken(acc.getRefreshToken());
					Soar.instance.authProgress.save();
					firstLogin = false;
					Soar.instance.setInfo("Launch");
				} catch (MicrosoftAuthenticationException e) {
					firstLogin = true;
					Soar.instance.setInfo("Please Login");
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void refreshTokenLogin() {
		Soar.instance.setInfo("Loading...");
		new Thread() {
			@Override
			public void run() {
				MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
				try {
					MicrosoftAuthResult acc = authenticator.loginWithRefreshToken(Soar.instance.getRefreshToken());
					Soar.instance.setToken(acc.getAccessToken());
					firstLogin = false;
					Soar.instance.setInfo("Launch");
				} catch (MicrosoftAuthenticationException e) {
					firstLogin = true;
					Soar.instance.setInfo("Please Login");
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void save() {
		
		ArrayList<String> toSave = new ArrayList<String>();
		
		toSave.add("Username:" + Soar.instance.getUsername());
		toSave.add("ID:" + Soar.instance.getId());
		toSave.add("Token:" + Soar.instance.getToken());
		toSave.add("RefreshToken:" + Soar.instance.getRefreshToken());
		
		try {
			PrintWriter pw = new PrintWriter(this.dataFile);
			for (String str : toSave) {
				pw.println(str);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		
		ArrayList<String> lines = new ArrayList<String>();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.dataFile));
			String line = reader.readLine();
			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (String s : lines) {
			String[] args = s.split(":");
			
			if (s.toLowerCase().startsWith("username:")) {
				Soar.instance.setUsername(args[1]);
			}
			
			if (s.toLowerCase().startsWith("id:")) {
				Soar.instance.setId(args[1]);
			}
			
			if (s.toLowerCase().startsWith("token:")) {
				Soar.instance.setToken(args[1]);
			}
			
			if (s.toLowerCase().startsWith("refreshtoken:")) {
				Soar.instance.setRefreshToken(args[1]);
			}
		}
	}

	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}
}
