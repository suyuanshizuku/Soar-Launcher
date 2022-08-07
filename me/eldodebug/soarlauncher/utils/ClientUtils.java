package me.eldodebug.soarlauncher.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import me.eldodebug.soarlauncher.SoarLauncher;
import me.eldodebug.soarlauncher.management.scene.Scene;
import me.eldodebug.soarlauncher.management.scene.impl.SettingsScene;
import me.eldodebug.soarlauncher.utils.minecraft.MinecraftLibrary;
import me.eldodebug.soarlauncher.utils.minecraft.MinecraftNativeLibrary;

public class ClientUtils {

	private static String SOAR_CLIENT_JAR_URL = "https://github.com/EldoDebug/Soar-Launcher/releases/download/SoarLauncher-Files/SoarClient.jar";
	private static String SOAR_LITE_CLIENT_JAR_URL = "https://github.com/EldoDebug/Soar-Launcher/releases/download/SoarLauncher-Files/SoarLiteClient.jar";
	private static String SOAR_CLIENT_JSON_URL = "https://github.com/EldoDebug/Soar-Launcher/releases/download/SoarLauncher-Files/SoarClient.json";
	private static String SOAR_LITE_CLIENT_JSON_URL = "https://github.com/EldoDebug/Soar-Launcher/releases/download/SoarLauncher-Files/SoarLiteClient.json";
	private static String SOAR_JAVA_URL = "https://github.com/EldoDebug/Soar-Launcher/releases/download/SoarLauncher-Files/Soar-Java.zip";
	private static String SOAR_LITE_JAVA_URL = "https://github.com/EldoDebug/Soar-Launcher/releases/download/SoarLauncher-Files/Soar-Lite-Java.zip";
	private static String INDEX_URL = "https://github.com/EldoDebug/Soar-Launcher/releases/download/SoarLauncher-Files/1.8.json";
	private static String SOAR_VERSION_URL = "https://raw.githubusercontent.com/EldoDebug/Soar-Launcher/main/data/SoarClient/Version";
	private static String SOAR_LITE_VERSION_URL = "https://raw.githubusercontent.com/EldoDebug/Soar-Launcher/main/data/SoarLiteClient/Version";
	private static String SOAR_LAUNCHER_VERSION_URL = "https://raw.githubusercontent.com/EldoDebug/Soar-Launcher/main/data/Launcher/Version";
	
    public static void launchClient(Scene scene, List<MinecraftLibrary> libs) {
        try {
        	scene.setInfo("Launching...");
            Logger.info("Launching " + scene.getName() + " Client");
            final List<String> launchCMD = new ArrayList<>();
            
            switch (OSType.getType()) {
	            case WINDOWS:{
	            	Logger.info("Set Windows Java!");
	            	if(scene.getName().equals("Soar")) {
		                launchCMD.add(new File(SoarLauncher.instance.fileManager.getSoarJavaDir(), "bin/java").getPath());
	            	}else if(scene.getName().equals("Soar Lite")){
		                launchCMD.add(new File(SoarLauncher.instance.fileManager.getSoarLiteJavaDir(), "bin/java").getPath());
	            	}else {
	            		Logger.error("You Use Not Found Client xD");
	            	}
	                break;
	            }
	            default:{
	            	Logger.info("Set OtherOS Java!");
	                launchCMD.add(System.getProperty("java.home", "/usr") + "/bin/java");
	                break;
	            }
            }
            
            launchCMD.add("-Xmx" + SoarLauncher.instance.settingsManager.getClientMemory() + "G");
            
        	if(scene.getName().equals("Soar")) {
                launchCMD.add("-Djava.library.path=" + SoarLauncher.instance.fileManager.getSoarNativesDir().getAbsolutePath());
        	}else if(scene.getName().equals("Soar Lite")) {
                launchCMD.add("-Djava.library.path=" + SoarLauncher.instance.fileManager.getSoarLiteNativesDir().getAbsolutePath());
        	}else {
        		Logger.error("You Use Not Found Client xD");
        	}

            final List<String> classpath = new ArrayList<>();
            
            for (final MinecraftLibrary lib : libs) {
            	
            	if(scene.getName().equals("Soar")) {
                    final File libFile = new File(SoarLauncher.instance.fileManager.getSoarLibrariesDir(), lib.getPath());
                    classpath.add(libFile.getAbsolutePath());
            	}else if(scene.getName().equals("Soar Lite")) {
                    final File libFile = new File(SoarLauncher.instance.fileManager.getSoarLiteLibrariesDir(), lib.getPath());
                    classpath.add(libFile.getAbsolutePath());
            	}else {
            		Logger.error("You Use Not Found Client xD");
            	}

            }
            
        	if(scene.getName().equals("Soar")) {
                classpath.add(SoarLauncher.instance.fileManager.getSoarDir().getAbsolutePath() + "/*");
        	}else if(scene.getName().equals("Soar Lite")) {
                classpath.add(SoarLauncher.instance.fileManager.getSoarLiteDir().getAbsolutePath() + "/*");
        	}else {
        		Logger.error("You Use Not Found Client xD");
        	}

            launchCMD.add("-cp");
            launchCMD.add(String.join(File.pathSeparator, classpath));
            launchCMD.add("net.minecraft.client.main.Main");
            launchCMD.add("--accessToken");
            launchCMD.add(SoarLauncher.instance.authManager.getToken());
            launchCMD.add("--version");
            launchCMD.add("SoarClient");
            launchCMD.add("--username");
            launchCMD.add(SoarLauncher.instance.authManager.getUsername());
            launchCMD.add("--uuid");
            launchCMD.add(SoarLauncher.instance.authManager.getUuid());
            
            String launchDirAfterUserFolder = null;
            Logger.info("Setting Minecraft Location...");
            switch (OSType.getType()) {
                case WINDOWS: {
                	Logger.info("Set Windows Location!");
                    launchDirAfterUserFolder = "AppData\\Roaming\\.minecraft\\";
                    break;
                }
                case MAC: {
                	Logger.info("Set Mac Location!");	
                    launchDirAfterUserFolder = "Library/Application Support/.minecraft";
                    break;
                }
                default: {
                	Logger.info("Set Other Location!");
                    launchDirAfterUserFolder = ".minecraft";
                    break;
                }
            }
            
            final File launchF = new File(SoarLauncher.instance.fileManager.getUserDir(), launchDirAfterUserFolder);
            final File assetsF = new File(launchF, "assets");

            launchCMD.add("--gameDir");
            launchCMD.add(launchF.getAbsolutePath());
            launchCMD.add("--assetsDir");
            launchCMD.add(assetsF.getAbsolutePath());
            launchCMD.add("-assetIndex");
            launchCMD.add("1.8");
            
            final Process proc = new ProcessBuilder(launchCMD).start();

            final BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String isLine;
            while ((isLine = reader.readLine()) != null) {
            	Logger.info(isLine);
            }
            final BufferedReader err = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String errLine;
            while ((errLine = err.readLine()) != null) {
            	Logger.error(errLine);
            }
            
            if (!proc.isAlive()) {
				scene.setInfo("Launch");
            }
        }
        catch (IOException e) {
        	scene.setInfo("Launch");
        	Logger.error(e.getMessage());
        }
    }
    
    public static void downloadNatives(Scene scene, final List<MinecraftNativeLibrary> nativeLibraries) {
    	
    	File file;
    	
    	if(scene.getName().equals("Soar")) {
    		file = SoarLauncher.instance.fileManager.getSoarNativesDir();
    	}else if(scene.getName().equals("Soar Lite")) {
    		file = SoarLauncher.instance.fileManager.getSoarLiteNativesDir();
    	}else {
    		file = SoarLauncher.instance.fileManager.getSoarLiteNativesDir();
    	}
    	
		Logger.info("Downloading Natives...");
		
        if (file.listFiles().length == 0) {
        	scene.setInfo("Downloading...");
            for (final MinecraftNativeLibrary library : nativeLibraries) {
                final OSType osType = OSType.getType();
                if (osType == OSType.WINDOWS) {
                    if (library.getWin32DownloadURL() != null && library.getWin64DownloadURL() != null) {
                        if (OSType.getArch().equals("x86")) {
                            FileUtils.downloadFile(library.getWin32DownloadURL(), new File(file, library.getWin32DownloadURL().substring(library.getWin32DownloadURL().lastIndexOf("/") + 1)));
                            FileUtils.unzip(new File(file, library.getWin32DownloadURL().substring(library.getWin32DownloadURL().lastIndexOf("/") + 1)), file);
                            new File(file, library.getWin32DownloadURL().substring(library.getWin32DownloadURL().lastIndexOf("/") + 1)).delete();
                        }
                        else {
                            FileUtils.downloadFile(library.getWin64DownloadURL(), new File(file, library.getWin64DownloadURL().substring(library.getWin64DownloadURL().lastIndexOf("/") + 1)));
                            FileUtils.unzip(new File(file, library.getWin64DownloadURL().substring(library.getWin64DownloadURL().lastIndexOf("/") + 1)), file);
                            new File(file, library.getWin64DownloadURL().substring(library.getWin64DownloadURL().lastIndexOf("/") + 1)).delete();
                        }
                    }
                    if (library.getWindowsDownloadURL() == null) {
                        continue;
                    }
                    FileUtils.downloadFile(library.getWindowsDownloadURL(), new File(file, library.getWindowsDownloadURL().substring(library.getWindowsDownloadURL().lastIndexOf("/") + 1)));
                    FileUtils.unzip(new File(file, library.getWindowsDownloadURL().substring(library.getWindowsDownloadURL().lastIndexOf("/") + 1)), file);
                    new File(file, library.getWindowsDownloadURL().substring(library.getWindowsDownloadURL().lastIndexOf("/") + 1)).delete();
                }
                else if (osType == OSType.MAC) {
                    if (library.getMacosXDownloadURL() == null) {
                        continue;
                    }
                    FileUtils.downloadFile(library.getMacosXDownloadURL(), new File(file, library.getMacosXDownloadURL().substring(library.getMacosXDownloadURL().lastIndexOf("/") + 1)));
                    FileUtils.unzip(new File(file, library.getMacosXDownloadURL().substring(library.getMacosXDownloadURL().lastIndexOf("/") + 1)), file);
                    new File(file, library.getMacosXDownloadURL().substring(library.getMacosXDownloadURL().lastIndexOf("/") + 1)).delete();
                }
                else if (osType == OSType.LINUX) {
                    if (library.getLinuxDownloadURL() == null) {
                        continue;
                    }
                    FileUtils.downloadFile(library.getLinuxDownloadURL(), new File(file, library.getLinuxDownloadURL().substring(library.getLinuxDownloadURL().lastIndexOf("/") + 1)));
                    FileUtils.unzip(new File(file, library.getLinuxDownloadURL().substring(library.getLinuxDownloadURL().lastIndexOf("/") + 1)), file);
                    new File(file, library.getLinuxDownloadURL().substring(library.getLinuxDownloadURL().lastIndexOf("/") + 1)).delete();
                }
            }
        }
    }
    
    public static void downloadLibraries(Scene scene, final List<MinecraftLibrary> libraries) {
    	
    	File file;
    	
    	Logger.info("Downloading Client Library");
    	
    	if(scene.getName().equals("Soar")) {
    		file = SoarLauncher.instance.fileManager.getSoarLibrariesDir();
    	}else if(scene.getName().equals("Soar Lite")) {
    		file = SoarLauncher.instance.fileManager.getSoarLiteLibrariesDir();
    	}else {
    		file = SoarLauncher.instance.fileManager.getSoarLiteLibrariesDir();
    	}
    	
        for (final MinecraftLibrary library : libraries) {
            if (!new File(file, library.getPath()).getParentFile().exists()) {
                new File(file, library.getPath()).getParentFile().mkdirs();
            }
            if (!new File(file, library.getPath()).exists()) {
                FileUtils.downloadFile(library.getUrl(), new File(file, library.getPath()));
            }
        }
    }
    
    public static void downloadJava(Scene scene) {
    	
    	File file;
    	String url;
    	File launcherDir;
    	File javaDir;
    	
    	if(scene.getName().equals("Soar")) {
    		file = SoarLauncher.instance.fileManager.getSoarJavaDir();
    		url = SOAR_JAVA_URL;
    		launcherDir = SoarLauncher.instance.fileManager.getSoarDir();
    		javaDir = SoarLauncher.instance.fileManager.getSoarJavaDir();
    	}else if(scene.getName().equals("Soar Lite")) {
    		file = SoarLauncher.instance.fileManager.getSoarLiteJavaDir();
    		launcherDir = SoarLauncher.instance.fileManager.getSoarLiteDir();
    		javaDir = SoarLauncher.instance.fileManager.getSoarLiteJavaDir();
    		url = SOAR_LITE_JAVA_URL;
    	}else {
    		file = SoarLauncher.instance.fileManager.getSoarLiteJavaDir();
    		launcherDir = SoarLauncher.instance.fileManager.getSoarDir();
    		javaDir = SoarLauncher.instance.fileManager.getSoarJavaDir();
    		url = SOAR_JAVA_URL;
    	}
    	
    	if(file.listFiles().length == 0) {
    		Logger.info("Downloading " + scene.getName() + " Java");
    		FileUtils.downloadFile(url, new File(launcherDir, "java.zip"));
    		FileUtils.unzip(new File(launcherDir, "java.zip"), javaDir);
    	}
    }
    
    public static void downloadClient(Scene scene) {
    	Logger.info("Downloading Client Files...");
    	
    	File file;
    	String name;
    	String urlJar;
    	String urlJson;
    	
    	if(scene.getName().equals("Soar")) {
    		file = SoarLauncher.instance.fileManager.getSoarDir();
    		name = "SoarClient";
    		urlJar = SOAR_CLIENT_JAR_URL;
    		urlJson = SOAR_CLIENT_JSON_URL;
    	}else if(scene.getName().equals("Soar Lite")) {
    		file = SoarLauncher.instance.fileManager.getSoarLiteDir();
    		name = "SoarLiteClient";
    		urlJar = SOAR_LITE_CLIENT_JAR_URL;
    		urlJson = SOAR_LITE_CLIENT_JSON_URL;
    	}else {
    		file = SoarLauncher.instance.fileManager.getSoarLiteDir();
    		name = "SoarClient";
    		urlJar = SOAR_CLIENT_JAR_URL;
    		urlJson = SOAR_CLIENT_JSON_URL;
    	}
    	
    	if(!(new File(file, name + ".jar").exists()) || !(new File(file, name + ".json").exists()) || ClientUtils.checkClientUpdate(scene)) {
        	scene.setInfo("Downloading...");
        	
    		FileUtils.downloadFile(urlJar, new File(file, name + ".jar"));
    		FileUtils.downloadFile(urlJson, new File(file, name + ".json"));
        	
        	if(!SoarLauncher.instance.fileManager.getMinecraftDir().exists()) {
        		SoarLauncher.instance.fileManager.getMinecraftDir().mkdir();
        		new File(SoarLauncher.instance.fileManager.getMinecraftDir(), "assets").mkdir();
        		new File(SoarLauncher.instance.fileManager.getMinecraftDir(), "assets/indexes").mkdir();
        		FileUtils.downloadFile(INDEX_URL, new File(SoarLauncher.instance.fileManager.getMinecraftDir(), "assets/indexes/1.8.json"));
        	}
        	
        	if(!new File(SoarLauncher.instance.fileManager.getMinecraftDir(), "assets/indexes/1.8.json").exists()) {
        		FileUtils.downloadFile(INDEX_URL, new File(SoarLauncher.instance.fileManager.getMinecraftDir(), "assets/indexes/1.8.json"));
        	}
    	}
    }
    
    @SuppressWarnings("resource")
	private static boolean checkClientUpdate(Scene scene) {
    	
    	String clientDir;
    	String url2;
    	
    	if(scene.getName().equals("Soar")) {
    		clientDir = "soar";
    		url2 = SOAR_VERSION_URL;
    	}else if(scene.getName().equals("Soar Lite")) {
    		clientDir = "soar lite";
    		url2 = SOAR_LITE_VERSION_URL;
    	}else {
    		clientDir = "soar";
    		url2 = SOAR_VERSION_URL;
    	}
    	
    	Logger.info("Checking Client update...");
    	
    	
		try {
	        URL url = new URL(url2);
	        Scanner s = new Scanner(url.openStream());
	        
	        while (s.hasNext()) {
	            String[] s2 = s.nextLine().split(":");
	            if(!(new File(SoarLauncher.instance.fileManager.getAppdata(), ".minecraft/" + clientDir + "/temp/" + s2[0] + ".ver").exists())) {
	            	Logger.info("Detect Client Update!");
	            	return true;
	            }
	        }
		}catch(Exception e) {
			SoarLauncher.instance.sceneManager.setInfoToLaunch();
        	Logger.error("Failed Check Client Update");
        	Logger.error(e.getMessage());
		}
    	return false;
    }
    
    @SuppressWarnings("resource")
	public static boolean checkLauncherUpdate() {
    	
    	Logger.info("Checking Launcher update...");
    	
		try {
	        URL url = new URL(SOAR_LAUNCHER_VERSION_URL);
	        Scanner s = new Scanner(url.openStream());
	        
	        while (s.hasNext()) {
	            String[] s2 = s.nextLine().split(":");
	            if(!SoarLauncher.instance.getVersion().equals(s2[0])) {
	            	Logger.info("Detect Launcher Update!");
	            	return true;
	            }
	        }
		}catch(Exception e) {
			SoarLauncher.instance.sceneManager.setInfoToLaunch();
        	Logger.error("Failed Check Launcher Update");
        	Logger.error(e.getMessage());
		}
    	return false;
    }
    
    public static void checkFiles() {
    	
    	boolean soarJavaFlag = false;
    	boolean soarLiteJavaFlag = false;
    	
    	if(SoarLauncher.instance.fileManager.getSoarJavaDir().exists()) {
    		if(!new File(SoarLauncher.instance.fileManager.getSoarJavaDir(), "bin").exists() || new File(SoarLauncher.instance.fileManager.getSoarJavaDir(), "bin").length() == 0) {
    			soarJavaFlag = true;
    		}
    		if(!new File(SoarLauncher.instance.fileManager.getSoarJavaDir(), "legal").exists()) {
    			soarJavaFlag = true;
    		}
    		if(!new File(SoarLauncher.instance.fileManager.getSoarJavaDir(), "lib").exists() || new File(SoarLauncher.instance.fileManager.getSoarJavaDir(), "lib").length() == 0) {
    			soarJavaFlag = true;
    		}
    	}else if(!SoarLauncher.instance.fileManager.getSoarJavaDir().exists() || SoarLauncher.instance.fileManager.getSoarJavaDir().length() == 0){
			soarJavaFlag = true;
    	}
    	
    	if(soarJavaFlag) {
    		ClientUtils.deleteFiles(SoarLauncher.instance.fileManager.getSoarJavaDir().getPath());
    		SoarLauncher.instance.fileManager.getSoarJavaDir().mkdir();
    		ClientUtils.downloadJava(SoarLauncher.instance.sceneManager.getScenesByName("Soar"));
    	}
    	
    	if(SoarLauncher.instance.fileManager.getSoarLiteJavaDir().exists()) {
    		if(!new File(SoarLauncher.instance.fileManager.getSoarLiteJavaDir(), "bin").exists() || new File(SoarLauncher.instance.fileManager.getSoarLiteJavaDir(), "bin").length() == 0) {
    			soarLiteJavaFlag = true;
    		}
    		if(!new File(SoarLauncher.instance.fileManager.getSoarLiteJavaDir(), "legal").exists() || new File(SoarLauncher.instance.fileManager.getSoarLiteJavaDir(), "legal").length() == 0) {
    			soarLiteJavaFlag = true;
    		}
    		if(!new File(SoarLauncher.instance.fileManager.getSoarLiteJavaDir(), "lib").exists() || new File(SoarLauncher.instance.fileManager.getSoarLiteJavaDir(), "lib").length() == 0) {
    			soarLiteJavaFlag = true;
    		}
    		if(!new File(SoarLauncher.instance.fileManager.getSoarLiteJavaDir(), "conf").exists() || new File(SoarLauncher.instance.fileManager.getSoarLiteJavaDir(), "conf").length() == 0) {
    			soarLiteJavaFlag = true;
    		}
    	}else if(!SoarLauncher.instance.fileManager.getSoarLiteJavaDir().exists() || SoarLauncher.instance.fileManager.getSoarLiteJavaDir().length() == 0){
			soarLiteJavaFlag = true;
    	}
    	
    	if(soarLiteJavaFlag) {
    		ClientUtils.deleteFiles(SoarLauncher.instance.fileManager.getSoarLiteJavaDir().getPath());
    		SoarLauncher.instance.fileManager.getSoarLiteJavaDir().mkdir();
    		ClientUtils.downloadJava(SoarLauncher.instance.sceneManager.getScenesByName("Soar Lite"));
    	}
    	
    	
    	SettingsScene.progress = "Check Files";
    }
    
    private static void deleteFiles(String path) {
        File filePath = new File(path);
        String[] list = filePath.list();
        for(String file : list) {
            File f = new File(path + File.separator + file);
            if(f.isDirectory()) {
            	deleteFiles(path + File.separator + file);
            }else {
                f.delete();
            }
        }
        filePath.delete();
    }
}
