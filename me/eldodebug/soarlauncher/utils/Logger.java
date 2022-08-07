package me.eldodebug.soarlauncher.utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.eldodebug.soarlauncher.SoarLauncher;

public class Logger {

	public static ArrayList<String> logs = new ArrayList<String>();
    private static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    
    public static void info(String message) {
    	logs.add(new String("[INFO] " + message));
    	print("[INFO] " + message);
    }
    
    public static void warn(String message) {
    	logs.add(new String("[WARN] " + message));
    	print("[WARN] " + message);
    }
    
    public static void error(String message) {
    	logs.add(new String("[ERROR] " + message));
    	print("[ERROR] " + message);
    }
    
    private static void print(String message) {
    	Date date = new Date();
    	System.out.println("[" + formatter.format(date) + "] " + message);
    }
    
    public static void save() {
		try {
			PrintWriter pw = new PrintWriter(SoarLauncher.instance.fileManager.getLogFile());
			for (String str : logs) {
				pw.println(str);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
}
