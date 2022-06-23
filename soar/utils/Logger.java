package soar.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Logger {

	public ArrayList<String> logs = new ArrayList<String>();
    private SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    private File logFile = new File(FileUtils.logDir, "latest.log");
    
    public void info(String message) {
    	logs.add(new String("[INFO] " + message));
    	print("[INFO] " + message);
    }
    
    public void warn(String message) {
    	logs.add(new String("[WARN] " + message));
    	print("[WARN] " + message);
    }
    
    public void error(String message) {
    	logs.add(new String("[ERROR] " + message));
    	print("[ERROR] " + message);
    }
    
    private void print(String message) {
    	Date date = new Date();
    	System.out.println("[" + formatter.format(date) + "] " + message);
    }
    
    public void save() {
		try {
			PrintWriter pw = new PrintWriter(this.logFile);
			for (String str : logs) {
				pw.println(str);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
}
