package soar.utils;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class FontUtils {

	public static UnicodeFont regular22;
	public static UnicodeFont regular_bold30;
	public static UnicodeFont regular_bold40;
	public static UnicodeFont icon40;
	
	private static boolean loaded;
	
	@SuppressWarnings("unchecked")
	public static void setup() {
		
		Font icon40_;
		Font regular22_;
		Font regular_bold30_;
		Font regular_bold40_;
		
		if(!loaded) {
			
			try {
		        Map<String, Font> locationMap = new HashMap<>();
				InputStream input;
				input = new BufferedInputStream(new FileInputStream(new File("assets/regular_bold.ttf")));
				locationMap.put(new File("assets/regular_bold.ttf").getAbsolutePath(), regular_bold30_ = Font.createFont(0, input));
				regular_bold30_ = regular_bold30_.deriveFont(Font.PLAIN, 30);
				regular_bold30 = new UnicodeFont(regular_bold30_);
				regular_bold30.getEffects().add(new ColorEffect(Color.WHITE));
				regular_bold30.addAsciiGlyphs();
				regular_bold30.loadGlyphs();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			try {
		        Map<String, Font> locationMap = new HashMap<>();
				InputStream input;
				input = new BufferedInputStream(new FileInputStream(new File("assets/regular_bold.ttf")));
				locationMap.put(new File("assets/regular_bold.ttf").getAbsolutePath(), regular_bold40_ = Font.createFont(0, input));
				regular_bold40_ = regular_bold40_.deriveFont(Font.PLAIN, 40);
				regular_bold40 = new UnicodeFont(regular_bold40_);
				regular_bold40.getEffects().add(new ColorEffect(Color.WHITE));
				regular_bold40.addAsciiGlyphs();
				regular_bold40.loadGlyphs();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			try {
		        Map<String, Font> locationMap = new HashMap<>();
				InputStream input;
				input = new BufferedInputStream(new FileInputStream(new File("assets/regular.ttf")));
				locationMap.put(new File("assets/regular.ttf").getAbsolutePath(), regular22_ = Font.createFont(0, input));
				regular22_ = regular22_.deriveFont(Font.PLAIN, 22);
				regular22 = new UnicodeFont(regular22_);
				regular22.getEffects().add(new ColorEffect(Color.WHITE));
				regular22.addAsciiGlyphs();
				regular22.loadGlyphs();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			try {
		        Map<String, Font> locationMap = new HashMap<>();
				InputStream input;
				input = new BufferedInputStream(new FileInputStream(new File("assets/icon.ttf")));
				locationMap.put(new File("assets/icon.ttf").getAbsolutePath(), icon40_ = Font.createFont(0, input));
				icon40_ = icon40_.deriveFont(Font.PLAIN, 40);
				icon40 = new UnicodeFont(icon40_);
				icon40.getEffects().add(new ColorEffect(Color.WHITE));
				icon40.addAsciiGlyphs();
				icon40.loadGlyphs();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			loaded = true;
		}
	}
}
