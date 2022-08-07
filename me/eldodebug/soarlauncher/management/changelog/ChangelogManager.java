package me.eldodebug.soarlauncher.management.changelog;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import me.eldodebug.soarlauncher.utils.Logger;

public class ChangelogManager {

	private ArrayList<String> soarChangelogs = new ArrayList<String>();
	private ArrayList<String> soarLiteChangelogs = new ArrayList<String>();
	
	private String SOAR_CHANGELOG_URL = "https://raw.githubusercontent.com/EldoDebug/Soar-Launcher/main/data/SoarClient/Changelog";
	private String SOAR_LITE_CHANGELOG_URL = "https://raw.githubusercontent.com/EldoDebug/Soar-Launcher/main/data/SoarLiteClient/Changelog";
	
	public ChangelogManager() {
		
		//Download Soar Change log
		Logger.info("Downloading Soar Change log");
		
		try {
	        URL url = new URL(SOAR_CHANGELOG_URL);
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	        
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	        	soarChangelogs.add(inputLine);
	        }
	        in.close();
		}catch(Exception e) {
			Logger.error(e.getMessage());
		}
		
		//Download Soar Change log
		Logger.info("Downloading Soar Lite Change log");
		
		try {
	        URL url = new URL(SOAR_LITE_CHANGELOG_URL);
	        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	        
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	        	soarLiteChangelogs.add(inputLine);
	        }
	        in.close();
		}catch(Exception e) {
			Logger.error(e.getMessage());
		}
	}

	public ArrayList<String> getSoarChangelogs() {
		return soarChangelogs;
	}

	public ArrayList<String> getSoarLiteChangelogs() {
		return soarLiteChangelogs;
	}
}
