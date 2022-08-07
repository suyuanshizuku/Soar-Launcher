package me.eldodebug.soarlauncher.utils;

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
        	Logger.error("Could not download file from: " + url + "\nPlease check your internet connection");
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
        	Logger.error("Could not extract file: " + file.getName());
        }
    }
}
