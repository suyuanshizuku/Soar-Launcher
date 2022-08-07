package me.eldodebug.soarlauncher.management.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import me.eldodebug.soarlauncher.SoarLauncher;
import me.eldodebug.soarlauncher.utils.Logger;

public class FileManager {
    
	//Main Directory
	private File userDir = new File(System.getProperty("user.home"));
	private File launcherDir = new File(userDir, ".soarlauncher");
	
	//Soar Clients Directory
	private File soarDir = new File(launcherDir, "soar");
	private File soarLiteDir = new File(launcherDir, "soarLite");
	
	//Soar Client
	private File soarAssetsDir = new File(soarDir, "assets");
	private File soarJavaDir = new File(soarDir, "java");
	private File soarNativesDir = new File(soarDir, "natives");
	private File soarLibrariesDir = new File(soarDir, "libraries");
	
	//Soar Lite Client
	private File soarLiteAssetsDir = new File(soarLiteDir, "assets");
	private File soarLiteJavaDir = new File(soarLiteDir, "java");
	private File soarLiteNativesDir = new File(soarLiteDir, "natives");
	private File soarLiteLibrariesDir = new File(soarLiteDir, "libraries");
	
	
	//Minecraft Directory
	private File appdata = new File(System.getenv("APPDATA"));
	private File minecraftDir = new File(appdata, ".minecraft");
	
	//Log
	private File logDir = new File(new File("soar logs").getAbsolutePath());
	private File logFile = new File(logDir, "launcher.log");
	
	//Account
	private File accountFile = new File(launcherDir, "Accounts.txt");
	
	//Client Configuration
	private File configFile = new File(launcherDir, "LauncherConfig.txt");
	
	//Soar Client Configuration
	private File soarConfigFile = new File(minecraftDir, "soar/Config.txt");
	
	public FileManager() {
		
		//Create Directories
		Logger.info("Loading FileManager");
		
		//Create Launucher Directory
		if(!launcherDir.exists()) {
			launcherDir.mkdir();
		}
		
		//Create Soar Directories
		if(!soarDir.exists()) {
			soarDir.mkdir();
		}
		if(!soarAssetsDir.exists()) {
			soarAssetsDir.mkdir();
		}
		if(!soarJavaDir.exists()) {
			soarJavaDir.mkdir();
		}
		if(!soarNativesDir.exists()) {
			soarNativesDir.mkdir();
		}
		if(!soarLibrariesDir.exists()) {
			soarLibrariesDir.mkdir();
		}
		
		//Create Soar Lite Directories
		if(!soarLiteDir.exists()) {
			soarLiteDir.mkdir();
		}
		if(!soarLiteAssetsDir.exists()) {
			soarLiteAssetsDir.mkdir();
		}
		if(!soarLiteJavaDir.exists()) {
			soarLiteJavaDir.mkdir();
		}
		if(!soarLiteNativesDir.exists()) {
			soarLiteNativesDir.mkdir();
		}
		if(!soarLiteLibrariesDir.exists()) {
			soarLiteLibrariesDir.mkdir();
		}
		
		//Create Log Directories
		if(!logDir.exists()) {
			logDir.mkdir();
		}
		
		if(!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				Logger.error(e.getMessage());
			}
		}
		
		//Create Accounts File
		if(!accountFile.exists()) {
			try {
				accountFile.createNewFile();
			} catch (IOException e) {
				Logger.error(e.getMessage());
			}
		}
		
		//Create Launcher Configuration
		if(!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				Logger.error(e.getMessage());
			}
		}
		
		//Create Soar Configuration
		if(!soarConfigFile.exists()) {
			try {
				soarConfigFile.createNewFile();
			} catch (IOException e) {
				Logger.error(e.getMessage());
			}
		}
	}
	
	public void saveClientSettings() {
		
		ArrayList<String> toSave = new ArrayList<String>();
		
		toSave.add("current-scene:" + SoarLauncher.instance.getCurrentScene());
		toSave.add("memory:" + SoarLauncher.instance.settingsManager.getClientMemory());
		
		try {
			PrintWriter pw = new PrintWriter(this.configFile);
			for (String str : toSave) {
				pw.println(str);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			Logger.error(e.getMessage());
		}
	}
	
	public void loadClientSettings() {

		ArrayList<String> lines = new ArrayList<String>();
		
		if(!this.configFile.exists()) {
			return;
		}
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(this.configFile));
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
			
			if(!StringUtils.containsIgnoreCase(lines.toString(), "current-scene:")) {
				SoarLauncher.instance.setCurrentScene("Soar");
			}else {
				if (s.toLowerCase().startsWith("current-scene:")) {
					SoarLauncher.instance.setCurrentScene(args[1]);
				}
			}
			
			if(!StringUtils.containsIgnoreCase(lines.toString(), "memory:")) {
				SoarLauncher.instance.settingsManager.setClientMemory(3);
			}else {
				if (s.toLowerCase().startsWith("memory:")) {
					if(StringUtils.isNumeric(args[1])) {
						SoarLauncher.instance.settingsManager.setClientMemory(Integer.parseInt(args[1]));
					}else {
						SoarLauncher.instance.settingsManager.setClientMemory(3);
					}
				}
			}
		}
	}

	public File getUserDir() {
		return userDir;
	}

	public File getLauncherDir() {
		return launcherDir;
	}

	public File getSoarDir() {
		return soarDir;
	}

	public File getSoarLiteDir() {
		return soarLiteDir;
	}

	public File getSoarAssetsDir() {
		return soarAssetsDir;
	}

	public File getSoarJavaDir() {
		return soarJavaDir;
	}

	public File getSoarNativesDir() {
		return soarNativesDir;
	}

	public File getSoarLibrariesDir() {
		return soarLibrariesDir;
	}

	public File getSoarLiteAssetsDir() {
		return soarLiteAssetsDir;
	}

	public File getSoarLiteJavaDir() {
		return soarLiteJavaDir;
	}

	public File getSoarLiteNativesDir() {
		return soarLiteNativesDir;
	}

	public File getSoarLiteLibrariesDir() {
		return soarLiteLibrariesDir;
	}

	public File getAppdata() {
		return appdata;
	}

	public File getMinecraftDir() {
		return minecraftDir;
	}

	public File getLogDir() {
		return logDir;
	}

	public File getLogFile() {
		return logFile;
	}

	public File getAccountFile() {
		return accountFile;
	}

	public void setAccountFile(File accountFile) {
		this.accountFile = accountFile;
	}

	public File getSoarConfigFile() {
		return soarConfigFile;
	}

	public void setSoarConfigFile(File soarConfigFile) {
		this.soarConfigFile = soarConfigFile;
	}
}
