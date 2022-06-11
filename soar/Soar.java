package soar;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import soar.gui.Gui;
import soar.media.Media;
import soar.media.MediaManager;
import soar.utils.ClientUtils;
import soar.utils.ColorUtils;
import soar.utils.FileUtils;
import soar.utils.FontUtils;
import soar.utils.json.MinecraftJson;
import soar.utils.json.MinecraftJsonParser;
import soar.utils.mouse.ClickType;
import soar.utils.mouse.MouseUtils;
import soar.utils.theme.ThemeParser;

public class Soar extends Base{

	public static Soar instance;
	public MediaManager mediaManager = new MediaManager();
	
	private String info;
	
	private ArrayList<String> logs = new ArrayList<String>();
	
	private String version = "2.0";
	
	public Soar() {
		this.setup();
		this.createDisplay();
	}
	
	@Override
	public void setup() {

		instance = this;
		
		ThemeParser.init();
		
		try {
	        URL url = new URL("https://pastebin.com/raw/zTpdMhrp");
	        Scanner s = new Scanner(url.openStream());
	        
	        while (s.hasNext()) {
	            String[] s2 = s.nextLine().split(":");
	            logs.add(s2[0]);
	        }
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		if(!FileUtils.launcherDir.exists()) {
			FileUtils.launcherDir.mkdir();
		}
		
		if(!FileUtils.clientFolder.exists()) {
			FileUtils.clientFolder.mkdir();
		}
		
		if(!FileUtils.librariesFolder.exists()) {
			FileUtils.librariesFolder.mkdir();
		}
		
		if(!FileUtils.nativesFolder.exists()) {
			FileUtils.nativesFolder.mkdir();
		}
		
		if(!FileUtils.assetsFolder.exists()) {
			FileUtils.assetsFolder.mkdir();
		}
		
		mediaManager = new MediaManager();
		info = "Launch";
		
		super.setup();
		this.setTitle("Soar Launcher " + "v" + version);
		this.setWidth(960);
		this.setHeight(535);
		this.setMaxFPS(60);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY) {
		
		int offsetY = 25;
		int changeLogY = 138;

		Gui.drawGradientRound(0, 0, this.getWidth(), this.getHeight(), 0, ColorUtils.getClientColor(0).getRGB(), ColorUtils.getClientColor(90).getRGB(), ColorUtils.getClientColor(180).getRGB(), ColorUtils.getClientColor(270).getRGB());
		Gui.drawRectangle(this.getWidth() - 70, 0, this.getWidth(), this.getHeight(), new Color(20, 20, 20, 60).getRGB());
		
		FontUtils.regular_bold40.drawString(20, 10, "Soar Launcher", org.newdawn.slick.Color.white);
		
		for(Media m : mediaManager.getMedias()) {
			FontUtils.icon40.drawString(this.getWidth() - 55, offsetY, m.getFontID(), org.newdawn.slick.Color.white);
			offsetY+=70;
		}
		
		Gui.drawSmoothGradientRound(this.getWidth() / 2 + 140, this.getHeight() - 85, 240, 70, 16, new Color(255, 206, 13).getRGB(), new Color(255, 191, 4).getRGB(), new Color(255, 191, 4).getRGB(), new Color(255, 206, 13).getRGB());
		FontUtils.regular_bold30.drawString(this.getWidth() / 2 - (FontUtils.regular_bold30.getWidth(info) / 2) + 262, this.getHeight() - 70, info, new org.newdawn.slick.Color(112, 74, 26));
		
		Gui.drawRound(35, 90, 350, 250, 12, new Color(20, 20, 20, 60).getRGB());
		Gui.drawRoundUnderRect(35, 90, 350, 45, 12, new Color(20, 20, 20, 60).getRGB());
		FontUtils.regular_bold30.drawString(45, 93, "Changelog");
		

		for(String s : logs) {
    		FontUtils.regular22.drawString(57, changeLogY, s);
            changeLogY+=25;
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY) {
		
		int offsetY = 25;
		Desktop desktop = Desktop.getDesktop();
        
		for(Media m : mediaManager.getMedias()) {
			
			if(MouseUtils.isInsideClick(mouseX, mouseY, this.getWidth() - 55, offsetY, 40, 40, ClickType.LEFT)) {
				try {
					desktop.browse(new URI(m.getUrl()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			offsetY+=70;
		}
		
		if((!info.equals("Launching..."))) {
			if((!info.equals("Downloading..."))) {
				if(MouseUtils.isInsideClick(mouseX, mouseY, this.getWidth() / 2 + 140, this.getHeight() - 85, 240, 70, ClickType.LEFT)) {
					
					new Thread() {
						@Override
						public void run() {
							try {
						        ClientUtils.downloadClient();
						        MinecraftJson json = MinecraftJsonParser.parseJson(new File(FileUtils.clientFolder, "SoarClient.json"));
						        ClientUtils.downloadJre();
								ClientUtils.downloadLibraries(json.getLibraries());
								ClientUtils.downloadNatives(json.getNativeLibraries());
								ClientUtils.launchClient(json.getLibraries());
							}catch(Exception e) {
								e.printStackTrace();
							}
						}
					}.start();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new Soar();
	}
	
	public String getInfo() {
		return this.info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
}
