package soarlauncher.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {

	/*
	 * Launcher directory
	 */
	public static final File LAUNCHER_DIR = new File(ClientUtils.getUserDir(), ".soarclient");
	
	/*
	 * Soar Client directory
	 */
	public static final File MAIN_DIR = new File(LAUNCHER_DIR, "soar");
	public static final File JAVA_DIR = new File(MAIN_DIR, "java");
	public static final File NATIVES_DIR = new File(MAIN_DIR, "natives");
	public static final File LIBRARIES_DIR = new File(MAIN_DIR, "libraries");
	public static final File ASSETS_DIR = new File(MAIN_DIR, "assets");
	public static final File MODS_DIR = new File(MAIN_DIR, "mods");
	
	/*
	 * Launcher configuration
	 */
	public static final File CONFIG_FILE = new File(LAUNCHER_DIR, "Config.txt");
	
	/*
	 * Create need directory, files
	 */
	public static void createDir() {
		
		//Create launcher directory
		createDir(LAUNCHER_DIR);
		
		//Soar Client Directory
		createDir(MAIN_DIR);
		createDir(JAVA_DIR);
		createDir(NATIVES_DIR);
		createDir(LIBRARIES_DIR);
		createDir(ASSETS_DIR);
		createDir(MODS_DIR);
		
		//Launcher Configuration
		createFile(CONFIG_FILE);
	}
	
	private static void createDir(File file) {
		if(!file.exists()) {
			file.mkdir();
		}
	}
	
	private static void createFile(File file) {
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				ClientUtils.showDialog(DialogType.ERROR, e.getMessage());
			}
		}
	}    
	
    public static File downloadFile(final String url, final File destination) {
        try {
            final URL downloadURL = new URL(url);
            final HttpURLConnection httpConnection = (HttpURLConnection)downloadURL.openConnection();
            final InputStream is = httpConnection.getInputStream();
            final FileOutputStream fos = new FileOutputStream(destination);
            final byte[] buffer = new byte[1024];
            int len1;
            while ((len1 = is.read(buffer)) > 0) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();
            
            return destination;
        }
        catch (IOException e) {
        	ClientUtils.showDialog(DialogType.ERROR, "Could not download file from: " + url + "\nPlease check your internet connection");
            return destination;
        }
    }
    
    public static void unzip(final File file, final File dest) {
        try {
            final ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                final File f = new File(dest, ze.getName());
                if (ze.isDirectory()) {
                    f.mkdirs();
                }
                else {
                    final FileOutputStream fos = new FileOutputStream(f);
                    final byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
            }
        }
        catch (Exception e) {
        	ClientUtils.showDialog(DialogType.ERROR, "Could not extract file: " + file.getName());
        }
    }
    
    public static void deleteFiles(String path) {
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
