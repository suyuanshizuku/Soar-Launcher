package soarlauncher.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;

import soarlauncher.SoarLauncher;
import soarlauncher.config.Config;

public class ClientUtils {

	private static final String DATA_WINDOWS = "https://github.com/EldoDebug/Soar-Launcher/releases/download/Soar-Data/data-windows.zip";
	private static final String DATA_LINUX = "https://github.com/EldoDebug/Soar-Launcher/releases/download/Soar-Data/data-linux.zip";
	private static final String DATA_MAC = "https://github.com/EldoDebug/Soar-Launcher/releases/download/Soar-Data/data-mac.zip";
	
	public static File getUserDir() {
		OSType os = OSType.getType();
		File userDir;
		
		switch(os) {
			case WINDOWS:
				userDir = new File(System.getProperty("user.home"));
				break;
			case MAC:
				userDir = new File(System.getProperty("user.home"), "Library" + File.separator + "Application Support");
				break;
			case LINUX:
				userDir = new File(System.getProperty("user.home"));
				break;
			default:
				userDir = new File(System.getProperty("user.home"));
				break;
		}
		
		return userDir;
	}
	
	public static void downloadData() {
		
		OSType os = OSType.getType();
		File dir = FileUtils.MAIN_DIR;
		File data = new File(dir, "data.zip");
		
		switch(os) {
			case WINDOWS:
				FileUtils.downloadFile(DATA_WINDOWS, data);
				FileUtils.unzip(data, dir);
				break;
			case LINUX:
				FileUtils.downloadFile(DATA_LINUX, data);
				FileUtils.unzip(data, dir);
				setParms();
				break;
			case MAC:
				FileUtils.downloadFile(DATA_MAC, data);
				FileUtils.unzip(data, dir);
				setParms();
				break;
			default:
				ClientUtils.showDialog(DialogType.ERROR, "OS could not detect");
				break;
		}
	}
	
	public static void downloadOptifine() {
		
		File optifine = new File(FileUtils.MODS_DIR, "Optifine.jar");
		
		if(!optifine.exists()) {
			FileUtils.downloadFile("https://github.com/EldoDebug/Soar-Launcher/releases/download/Soar-Data/Optifine.jar", optifine);
		}
	}
	
	public static void downloadSoar() {
		
		File soar = new File(FileUtils.MODS_DIR, "Soar-Release.jar");
		
		if(!soar.exists() || ClientUtils.checkUpdate()) {
			FileUtils.downloadFile("https://github.com/EldoDebug/Soar-Launcher/releases/download/Soar-Data/Soar-Release.jar", soar);
		}
	}
	
	private static void setParms() {
		
		OSType os = OSType.getType();
		File javaFile;
		
		if(os.equals(OSType.MAC)) {
			javaFile = new File(ClientUtils.getUserDir(), File.separator + ".soarclient" + File.separator + "soar" + File.separator + "java" + File.separator + "Home" + File.separator + "bin" + File.separator + "java");
	        Set<PosixFilePermission> perms;
			try {
				perms = new HashSet<>(Files.getPosixFilePermissions(javaFile.toPath()));
		        perms.add(PosixFilePermission.OWNER_EXECUTE);
		        perms.add(PosixFilePermission.GROUP_EXECUTE);
		        perms.add(PosixFilePermission.OTHERS_EXECUTE);
		        Files.setPosixFilePermissions(javaFile.toPath(), perms);
			} catch (IOException e) {
				ClientUtils.showDialog(DialogType.ERROR, e.getMessage());
			}
		}
		
		if(os.equals(OSType.LINUX)) {
			javaFile = new File(ClientUtils.getUserDir(), File.separator + ".soarclient" + File.separator + "soar" + File.separator + "java" + File.separator + "bin" + File.separator + "java");
	        Set<PosixFilePermission> perms;
			try {
				perms = new HashSet<>(Files.getPosixFilePermissions(javaFile.toPath()));
		        perms.add(PosixFilePermission.OWNER_EXECUTE);
		        perms.add(PosixFilePermission.GROUP_EXECUTE);
		        perms.add(PosixFilePermission.OTHERS_EXECUTE);
		        Files.setPosixFilePermissions(javaFile.toPath(), perms);
			} catch (IOException e) {
				ClientUtils.showDialog(DialogType.ERROR, e.getMessage());
			}
		}
	}
	
	public static void showDialog(DialogType dialogType, String message) {
		
		String title = "";
		int id = 1;
		
		switch(dialogType) {
			case INFO:
				title = "Info";
				id = 1;
				break;
			case WARN:
				title = "Warn";
				id = 2;
				break;
			case ERROR:
				title = "Error";
				id = 0;
				break;
		}
		
		JOptionPane.showMessageDialog(SoarLauncher.getInstance(), message, title, id);
	}
	
	public static void launchSoar() throws IOException {
		
		OSType os = OSType.getType();
		
		List<String> launchCMD = new ArrayList<>();
		List<String> classpath = new ArrayList<>();
		
		String username = "SoarUser";
		String accessToken = "0";
		String uuid = "0";
		String version = "1.8.9";
        
		int memory = Config.MEMORY;
		
		//Set Java
		switch(os) {
			case WINDOWS:
		        launchCMD.add(new File(FileUtils.JAVA_DIR, "bin" + File.separator + "java").getAbsolutePath());
				break;
			case MAC:
		        launchCMD.add(new File(FileUtils.JAVA_DIR, "Home" + File.separator + "bin" + File.separator + "java").getAbsolutePath());
				break;
			case LINUX:
		        launchCMD.add(new File(FileUtils.JAVA_DIR, "bin" + File.separator + "java").getAbsolutePath());
				break;
			default:
		        launchCMD.add(new File(FileUtils.JAVA_DIR, "bin" + File.separator + "java").getAbsolutePath());
				break;
		}
		
		//Set Memory
		launchCMD.add("-Xmx" + memory + "G");
		
		//Set Natives
		launchCMD.add("-Djava.library.path=" + FileUtils.NATIVES_DIR.getAbsolutePath());
		
		//Add Libraries
		for(File f : FileUtils.LIBRARIES_DIR.listFiles()) {
			classpath.add(f.getAbsolutePath());
		}
		
		classpath.add(FileUtils.MAIN_DIR.getAbsolutePath() + "/*");
		
		//Set class path
		launchCMD.add("-cp");
		launchCMD.add(String.join(File.pathSeparator, classpath));
		
		//Set Main class
		launchCMD.add("net.minecraft.launchwrapper.Launch");
		
		//Set Access token
		launchCMD.add("--accessToken");
		launchCMD.add(accessToken);
		
		//Set version
		launchCMD.add("--version");
		launchCMD.add(version);
		
		//Set User name
		launchCMD.add("--username");
		launchCMD.add(username);
		launchCMD.add("--uuid");
		launchCMD.add(uuid);
		
		//Set Tweak Class
		launchCMD.add("--tweakClass");
		launchCMD.add("net.minecraftforge.fml.common.launcher.FMLTweaker");
		
		//Set Minecraft location
		launchCMD.add("--gameDir");
		launchCMD.add(FileUtils.MAIN_DIR.getAbsolutePath());
		launchCMD.add("--assetsDir");
		launchCMD.add(FileUtils.ASSETS_DIR.getAbsolutePath());
		launchCMD.add("--assetIndex");
		launchCMD.add("1.8");
		
		Process proc =  new ProcessBuilder(launchCMD).start();
		
		if(proc.isAlive()) {
			Config.save();
			System.exit(0);
		}
	}
	
	public static boolean isDownloaded() {
		
		/*
		 * Check Assets directory
		 */
		File assetsDir = FileUtils.ASSETS_DIR;
		File indexesDir = new File(assetsDir, "indexes");
		File logConfigsDir = new File(assetsDir, "log_configs");
		File objectsDir = new File(assetsDir, "objects");
		File indexesFile = new File(indexesDir, "1.8.json");
		File logConfigsFile = new File(logConfigsDir, "client-1.7.xml");
		
		if(!assetsDir.exists() || assetsDir.listFiles().length < 2) {
			return false;
		}
		
		if(!indexesDir.exists() || indexesDir.listFiles().length == 0) {
			return false;
		}
		
		if(!logConfigsDir.exists() || logConfigsDir.listFiles().length == 0) {
			return false;
		}
		
		if(!objectsDir.exists() || objectsDir.listFiles().length < 235) {
			return false;
		}
		
		if(!indexesFile.exists()) {
			return false;
		}
		
		if(!logConfigsFile.exists()) {
			return false;
		}
		
		/*
		 * Check Natives directory
		 */
		File nativesDir = FileUtils.NATIVES_DIR;
		
		if(!nativesDir.exists() || nativesDir.listFiles().length < 7) {
			return false;
		}
		
		/*
		 * Check libraries directory
		 */
		File librariesDir = FileUtils.LIBRARIES_DIR;
		
		if(!librariesDir.exists() || librariesDir.listFiles().length < 52) {
			return false;
		}
		
		return true;
	}
	
	/*
	 * Check Soar Client Update
	 */
	@SuppressWarnings("resource")
	private static boolean checkUpdate() {
		
		try {
			URL url = new URL("https://raw.githubusercontent.com/EldoDebug/Soar-Launcher/main/Version");
			Scanner s = new Scanner(url.openStream());
			
	        while (s.hasNext()) {
	        	String[] s2 = s.nextLine().split(":");
	        	 
	            if(!(new File(FileUtils.MAIN_DIR, "soar" + File.separator + "temp" + File.separator + s2[0] + ".ver").exists())) {
	            	return true;
	            }
	        }
	        
		}catch(Exception e) {
			ClientUtils.showDialog(DialogType.ERROR, e.getMessage());
		}
		
		return false;
	}
}
