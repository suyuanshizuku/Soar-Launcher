package soar.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import soar.Soar;
import soar.utils.json.MinecraftLibrary;
import soar.utils.json.MinecraftNativeLibrary;

public class ClientUtils {
	
    public static void launchClient(final List<MinecraftLibrary> libs) {
        try {
        	Soar.instance.setInfo("Launching...");
            String launchCMD = "";
            Soar.instance.logger.info("Launching Client...");
            
            switch (OSType.getType()) {
	            case WINDOWS:{
	                Soar.instance.logger.info("Set Windows Java!");
	                launchCMD = new File(FileUtils.launcherDir + File.separator + "java" + File.separator + "bin" + File.separator + "java").getPath();
	                break;
	            }
	            default:{
	                Soar.instance.logger.info("Set OtherOS Java!");
	                launchCMD = "java";
	                break;
	            }
            }
            
            launchCMD = launchCMD + " -Djava.library.path=\"" + FileUtils.nativesFolder.getAbsolutePath() + "\" -cp \"";
            
            for (final MinecraftLibrary lib : libs) {
                final File libFile = new File(FileUtils.librariesFolder, lib.getPath());
                launchCMD += libFile.getAbsolutePath();
                launchCMD += File.pathSeparator;
            }
            
            launchCMD = launchCMD + FileUtils.clientFolder.getAbsolutePath() + File.separator + "*\" ";
            launchCMD += "net.minecraft.client.main.Main ";
            launchCMD += "--accessToken " + Soar.instance.getToken() + " ";
            launchCMD += "--version SoarClient ";
            launchCMD += "--username " + Soar.instance.getUsername() + " ";
            launchCMD += "--uuid " + Soar.instance.getId() + " ";
            String launchDirAfterUserFolder = null;
            switch (OSType.getType()) {
                case WINDOWS: {
	                Soar.instance.logger.info("Set Windows Location!");
                    launchDirAfterUserFolder = "AppData\\Roaming\\.minecraft\\";
                    break;
                }
                case MAC: {
	                Soar.instance.logger.info("Set MAC Location!");
                    launchDirAfterUserFolder = "Library\\Application Support\\.minecraft\\";
                    break;
                }
                default: {
	                Soar.instance.logger.info("Set Other Location!");
                    launchDirAfterUserFolder = "\\.minecraft\\";
                    break;
                }
            }
            
            final File launchF = new File(FileUtils.userDir, launchDirAfterUserFolder);
            final File assetsF = new File(launchF, "assets");
            launchCMD = launchCMD + "--gameDir \"" + launchF.getAbsolutePath() + "\" ";
            launchCMD = launchCMD + "--assetsDir \"" + assetsF.getAbsolutePath() + "\" ";
            launchCMD += "--assetIndex 1.8 ";
            final Process proc = Runtime.getRuntime().exec(launchCMD);
            final BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String isLine;
            while ((isLine = reader.readLine()) != null) {
                Soar.instance.logger.info(isLine);
            }
            final BufferedReader err = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String errLine;
            while ((errLine = err.readLine()) != null) {
                Soar.instance.logger.error(errLine);
            }
            
            if (!proc.isAlive()) {
                Soar.instance.setInfo("Launch");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void downloadNatives(final List<MinecraftNativeLibrary> nativeLibraries) {
        if (FileUtils.nativesFolder.listFiles().length == 0) {
    		Soar.instance.setInfo("Downloading...");
            Soar.instance.logger.info("Downloading Natives...");
            for (final MinecraftNativeLibrary library : nativeLibraries) {
                final OSType osType = OSType.getType();
                if (osType == OSType.WINDOWS) {
                    if (library.getWin32DownloadURL() != null && library.getWin64DownloadURL() != null) {
                        if (OSType.getArch().equals("x86")) {
                            FileUtils.downloadFile(library.getWin32DownloadURL(), new File(FileUtils.nativesFolder, library.getWin32DownloadURL().substring(library.getWin32DownloadURL().lastIndexOf("/") + 1)));
                            FileUtils.unzip(new File(FileUtils.nativesFolder, library.getWin32DownloadURL().substring(library.getWin32DownloadURL().lastIndexOf("/") + 1)), FileUtils.nativesFolder);
                            new File(FileUtils.nativesFolder, library.getWin32DownloadURL().substring(library.getWin32DownloadURL().lastIndexOf("/") + 1)).delete();
                        }
                        else {
                            FileUtils.downloadFile(library.getWin64DownloadURL(), new File(FileUtils.nativesFolder, library.getWin64DownloadURL().substring(library.getWin64DownloadURL().lastIndexOf("/") + 1)));
                            FileUtils.unzip(new File(FileUtils.nativesFolder, library.getWin64DownloadURL().substring(library.getWin64DownloadURL().lastIndexOf("/") + 1)), FileUtils.nativesFolder);
                            new File(FileUtils.nativesFolder, library.getWin64DownloadURL().substring(library.getWin64DownloadURL().lastIndexOf("/") + 1)).delete();
                        }
                    }
                    if (library.getWindowsDownloadURL() == null) {
                        continue;
                    }
                    FileUtils.downloadFile(library.getWindowsDownloadURL(), new File(FileUtils.nativesFolder, library.getWindowsDownloadURL().substring(library.getWindowsDownloadURL().lastIndexOf("/") + 1)));
                    FileUtils.unzip(new File(FileUtils.nativesFolder, library.getWindowsDownloadURL().substring(library.getWindowsDownloadURL().lastIndexOf("/") + 1)), FileUtils.nativesFolder);
                    new File(FileUtils.nativesFolder, library.getWindowsDownloadURL().substring(library.getWindowsDownloadURL().lastIndexOf("/") + 1)).delete();
                }
                else if (osType == OSType.MAC) {
                    if (library.getMacosXDownloadURL() == null) {
                        continue;
                    }
                    FileUtils.downloadFile(library.getMacosXDownloadURL(), new File(FileUtils.nativesFolder, library.getMacosXDownloadURL().substring(library.getMacosXDownloadURL().lastIndexOf("/") + 1)));
                    FileUtils.unzip(new File(FileUtils.nativesFolder, library.getMacosXDownloadURL().substring(library.getMacosXDownloadURL().lastIndexOf("/") + 1)), FileUtils.nativesFolder);
                    new File(FileUtils.nativesFolder, library.getMacosXDownloadURL().substring(library.getMacosXDownloadURL().lastIndexOf("/") + 1)).delete();
                }
                else if (osType == OSType.LINUX) {
                    if (library.getLinuxDownloadURL() == null) {
                        continue;
                    }
                    FileUtils.downloadFile(library.getLinuxDownloadURL(), new File(FileUtils.nativesFolder, library.getLinuxDownloadURL().substring(library.getLinuxDownloadURL().lastIndexOf("/") + 1)));
                    FileUtils.unzip(new File(FileUtils.nativesFolder, library.getLinuxDownloadURL().substring(library.getLinuxDownloadURL().lastIndexOf("/") + 1)), FileUtils.nativesFolder);
                    new File(FileUtils.nativesFolder, library.getLinuxDownloadURL().substring(library.getLinuxDownloadURL().lastIndexOf("/") + 1)).delete();
                }
            }
        }
    }
    
    public static void downloadLibraries(final List<MinecraftLibrary> libraries) {
        for (final MinecraftLibrary library : libraries) {
            Soar.instance.setInfo("Downloading...");
            Soar.instance.logger.info("Downloading Client Library: " + library.getName());
            if (!new File(FileUtils.librariesFolder, library.getPath()).getParentFile().exists()) {
                new File(FileUtils.librariesFolder, library.getPath()).getParentFile().mkdirs();
            }
            if (new File(FileUtils.librariesFolder, library.getPath()).exists()) {
            }
            else {
                FileUtils.downloadFile(library.getUrl(), new File(FileUtils.librariesFolder, library.getPath()));
            }
        }
    }
    
    public static void downloadJava() {
    	if(!FileUtils.javaFolder.exists()) {
    		Soar.instance.setInfo("Downloading...");
        	Soar.instance.logger.info("Downloading Java...");
    		FileUtils.javaFolder.mkdir();
    		FileUtils.downloadFile("https://github.com/EldoDebug/Soar-Launcher/releases/download/SoarClient-Files/java.zip", new File(FileUtils.launcherDir, "java.zip"));
    		FileUtils.unzip(new File(FileUtils.launcherDir, "java.zip"), FileUtils.javaFolder);
        	Soar.instance.logger.info("Success Download Java Files!");
    	}
    }
    
    public static void downloadClient() {
    	Soar.instance.logger.info("Downloading Client Files...");
    	if(!(new File(FileUtils.clientFolder, "SoarClient.jar").exists()) || !(new File(FileUtils.clientFolder, "SoarClient.json").exists()) || ClientUtils.checkUpdate()) {
    		Soar.instance.setInfo("Downloading...");
    		Soar.instance.logger.info("Downloading SoarClient.jar");
    		FileUtils.downloadFile("https://github.com/EldoDebug/Soar-Launcher/releases/download/SoarClient-Files/SoarClient.jar", new File(FileUtils.clientFolder, "SoarClient.jar"));
    		Soar.instance.logger.info("Downloading SoarClient.json");
    		FileUtils.downloadFile("https://github.com/EldoDebug/Soar-Launcher/releases/download/SoarClient-Files/SoarClient.json", new File(FileUtils.clientFolder, "SoarClient.json"));
        	Soar.instance.logger.info("Success Download Client Files!");
    	}
    }
    
    @SuppressWarnings("resource")
	private static boolean checkUpdate() {
    	Soar.instance.logger.info("Checking Update...");
		try {
	        URL url = new URL("https://pastebin.com/raw/WG2RGHJ4");
	        Scanner s = new Scanner(url.openStream());
	        
	        while (s.hasNext()) {
	            String[] s2 = s.nextLine().split(":");
	            if(!(new File(FileUtils.appdata, ".minecraft/soar/temp/" + s2[0] + ".ver").exists())) {
	            	return true;
	            }
	        }
		}catch(Exception e) {
			Soar.instance.logger.error("Update check failed.");
			e.printStackTrace();
		}
    	return false;
    }
}
