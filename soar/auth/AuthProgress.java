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
import soar.utils.EnumInfo;
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
					Soar.instance.logger.info("Login...");
					Soar.instance.setUsername(acc.getProfile().getName());
					Soar.instance.setId(acc.getProfile().getId());
					Soar.instance.setToken(acc.getAccessToken());
					Soar.instance.setRefreshToken(acc.getRefreshToken());
					Soar.instance.authProgress.save();
					Soar.instance.logger.info("Success Login!");
					firstLogin = false;
					Soar.instance.info = EnumInfo.LAUNCH;
				} catch (MicrosoftAuthenticationException e) {
					firstLogin = true;
					Soar.instance.info = EnumInfo.LOGIN;
					Soar.instance.logger.error("Field Login!");
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	public void refreshTokenLogin() {
		Soar.instance.info = EnumInfo.LOADING;
		MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
		try {
			Soar.instance.logger.info("Login...");
			MicrosoftAuthResult acc = authenticator.loginWithRefreshToken(Soar.instance.getRefreshToken());
			Soar.instance.setToken(acc.getAccessToken());
			Soar.instance.logger.info("Success Login!");
			firstLogin = false;
			Soar.instance.info = EnumInfo.LAUNCH;
		} catch (MicrosoftAuthenticationException e) {
			firstLogin = true;
			Soar.instance.info = EnumInfo.LOGIN;
			Soar.instance.logger.info("Field Login!");
			e.printStackTrace();
		}
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
			Soar.instance.logger.info("File does not exist!");
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
			Soar.instance.logger.info("File does not exist!");
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
