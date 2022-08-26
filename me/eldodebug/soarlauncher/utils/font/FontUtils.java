package me.eldodebug.soarlauncher.utils.font;

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

	private static boolean loaded = false;
	
	public static UnicodeFont regular20;
	public static UnicodeFont regular22;
	public static UnicodeFont regular30;
	public static UnicodeFont regular40;
	public static UnicodeFont regular_bold24;
	public static UnicodeFont regular_bold34;
	public static UnicodeFont icon26;
	
	public static Font regular20_;
	public static Font regular22_;
	public static Font regular30_;
	public static Font regular40_;
	public static Font regular_bold24_;
	public static Font regular_bold34_;
	public static Font icon26_;
	
	@SuppressWarnings("unchecked")
	public static void setup() {
		
        Map<String, Font> locationMap = new HashMap<>();
        InputStream input = null;
        
		if(!loaded) {
	        
			try {
				input = new BufferedInputStream(new FileInputStream(new File("assets/regular.ttf")));
				locationMap.put(new File("assets/regular.ttf").getAbsolutePath(), regular20_ = Font.createFont(0, input));
				regular20_ = regular20_.deriveFont(Font.PLAIN, 20);
				regular20 = new UnicodeFont(regular20_);
				regular20.getEffects().add(new ColorEffect(Color.WHITE));
				regular20.addAsciiGlyphs();
				regular20.loadGlyphs();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			try {
				input = new BufferedInputStream(new FileInputStream(new File("assets/regular.ttf")));
				locationMap.put(new File("assets/regular.ttf").getAbsolutePath(), regular30_ = Font.createFont(0, input));
				regular30_ = regular30_.deriveFont(Font.PLAIN, 30);
				regular30 = new UnicodeFont(regular30_);
				regular30.getEffects().add(new ColorEffect(Color.WHITE));
				regular30.addAsciiGlyphs();
				regular30.loadGlyphs();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			try {
				input = new BufferedInputStream(new FileInputStream(new File("assets/regular.ttf")));
				locationMap.put(new File("assets/regular.ttf").getAbsolutePath(), regular40_ = Font.createFont(0, input));
				regular40_ = regular40_.deriveFont(Font.PLAIN, 40);
				regular40 = new UnicodeFont(regular40_);
				regular40.getEffects().add(new ColorEffect(Color.WHITE));
				regular40.addAsciiGlyphs();
				regular40.loadGlyphs();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			try {
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
				input = new BufferedInputStream(new FileInputStream(new File("assets/regular_bold.ttf")));
				locationMap.put(new File("assets/regular_bold.ttf").getAbsolutePath(), regular_bold34_ = Font.createFont(0, input));
				regular_bold34_ = regular_bold34_.deriveFont(Font.PLAIN, 34);
				regular_bold34 = new UnicodeFont(regular_bold34_);
				regular_bold34.getEffects().add(new ColorEffect(Color.WHITE));
				regular_bold34.addAsciiGlyphs();
				regular_bold34.loadGlyphs();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			try {
				input = new BufferedInputStream(new FileInputStream(new File("assets/regular_bold.ttf")));
				locationMap.put(new File("assets/regular_bold.ttf").getAbsolutePath(), regular_bold24_ = Font.createFont(0, input));
				regular_bold24_ = regular_bold24_.deriveFont(Font.PLAIN, 30);
				regular_bold24 = new UnicodeFont(regular_bold24_);
				regular_bold24.getEffects().add(new ColorEffect(Color.WHITE));
				regular_bold24.addAsciiGlyphs();
				regular_bold24.loadGlyphs();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			try {
				input = new BufferedInputStream(new FileInputStream(new File("assets/icon.ttf")));
				locationMap.put(new File("assets/icon.ttf").getAbsolutePath(), icon26_ = Font.createFont(0, input));
				icon26_ = icon26_.deriveFont(Font.PLAIN, 26);
				icon26 = new UnicodeFont(icon26_);
				icon26.getEffects().add(new ColorEffect(Color.WHITE));
				icon26.addAsciiGlyphs();
				icon26.loadGlyphs();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			loaded = true;
		}
	}
	
	public static void drawString(UnicodeFont unicodeFont, String text, float x, float y, Color color) {
		unicodeFont.drawString(x, y, text, new org.newdawn.slick.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
	}
}
