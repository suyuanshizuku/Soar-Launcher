package soar.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JOptionPane;

public class FileUtils {
	
	public static File userDir = new File(System.getProperty("user.home"));
	public static File launcherDir = new File(FileUtils.userDir, ".soarclient");
	public static File logDir = new File(FileUtils.launcherDir, "log");
	public static File clientFolder = new File(FileUtils.launcherDir, "game");
	public static File assetsFolder = new File(FileUtils.launcherDir, "assets");
	public static File javaFolder = new File(FileUtils.launcherDir, "java");
	public static File nativesFolder = new File(FileUtils.clientFolder, "natives");
	public static File librariesFolder = new File(FileUtils.clientFolder, "libraries");
	public static String appdata = System.getenv("APPDATA");
	public static File minecraftFolder = new File(FileUtils.appdata + "\\.minecraft");
    
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
            JOptionPane.showMessageDialog(null, "Could not download file from: " + url + "\nPlease check your internet connection", e.getClass().getName(), 0);
            e.printStackTrace();
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
            JOptionPane.showMessageDialog(null, "Could not extract file: " + file.getName(), "Error: " + e.getClass().getName(), 0);
            e.printStackTrace();
        }
    }
}
