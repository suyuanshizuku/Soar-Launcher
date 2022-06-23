package soar;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import soar.auth.AuthProgress;
import soar.gui.Gui;
import soar.media.Media;
import soar.media.MediaManager;
import soar.utils.ClientUtils;
import soar.utils.ColorUtils;
import soar.utils.FileUtils;
import soar.utils.FontUtils;
import soar.utils.Logger;
import soar.utils.animation.Animation;
import soar.utils.json.MinecraftJson;
import soar.utils.json.MinecraftJsonParser;
import soar.utils.mouse.ClickType;
import soar.utils.mouse.MouseUtils;
import soar.utils.mouse.Scroll;
import soar.utils.theme.ThemeParser;

public class Soar extends Base{

	public static Soar instance;
	public AuthProgress authProgress = new AuthProgress();
	public MediaManager mediaManager = new MediaManager();
	
	private String info;
	
	private ArrayList<String> logs = new ArrayList<String>();
	
	private String version = "2.1";

	private String username, token, refreshToken, id;
	
	private float scrollY = 0;
	private Animation scrollAnimation = new Animation(0.0F);

	public Logger logger = new Logger();
    
	public Soar() {
		logger.info("Setting up...");
		this.setup();
		this.createDisplay();
	}
	
	@Override
	public void setup() {
		
		instance = this;
		
		logger.info("Loading theme...");
		ThemeParser.init();
		
		logger.info("Downloading Changelog...");
		
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
		
		logger.info("Making dir...");
		
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
		
		if(!FileUtils.logDir.exists()) {
			FileUtils.logDir.mkdir();
		}
		
		logger.info("Loading AuthProgress...");
		authProgress = new AuthProgress();
		
		logger.info("Loading Medias...");
		mediaManager = new MediaManager();

		logger.info("Loading AuthProgress");
		authProgress.load();
		
		if(authProgress.isFirstLogin()) {
			info = "Please Login";
		}else {
			info = "Launch";
			authProgress.refreshTokenLogin();
		}
		
		super.setup();
		this.setTitle("Soar Launcher " + "v" + version);
		this.setWidth(960);
		this.setHeight(535);
		this.setMaxFPS(120);
		
		logger.info("Drawing Screen!");
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
		
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		GL11.glScissor(55, 195, 330, 205);
		
        final Scroll scroll = this.scroll();

        if(scroll != null && logs.size() > 8) {
        	switch (scroll) {
        	case DOWN:
        		if(scrollY > -(logs.size() * (25 - 8))) {
            		scrollY -=20;
        		}
        		break;
            case UP:
            	if(scrollY < -10) {
        			scrollY += 20;
            	}
        		break;
        	}
        }

        scrollAnimation.setAnimation(scrollY, 15);
        
		for(String s : logs) {
    		FontUtils.regular22.drawString(57, changeLogY + scrollAnimation.getValue(), s);
            changeLogY+=25;
		}
		
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		
		FontUtils.icon2_40.drawString(this.getWidth() - 55, this.getHeight() - 55, "B", org.newdawn.slick.Color.white);
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY) {
		
		int offsetY = 25;
		Desktop desktop = Desktop.getDesktop();
        
		for(Media m : mediaManager.getMedias()) {
			
			if(MouseUtils.isInsideClick(mouseX, mouseY, this.getWidth() - 55, offsetY, 40, 40, ClickType.LEFT)) {
				logger.info("Navigating to the browser...");
				
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
				if((!info.equals("Loading..."))) {
					if(MouseUtils.isInsideClick(mouseX, mouseY, this.getWidth() / 2 + 140, this.getHeight() - 85, 240, 70, ClickType.LEFT)) {
						if(info.equals("Please Login")) {
							logger.info("Login...");
							authProgress.webViewLogin();
						}else {
							new Thread() {
								@Override
								public void run() {
									try {
								        ClientUtils.downloadClient();
								        MinecraftJson json = MinecraftJsonParser.parseJson(new File(FileUtils.clientFolder, "SoarClient.json"));
								        ClientUtils.downloadJava();
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
		}
		
		if(MouseUtils.isInsideClick(mouseX, mouseY, this.getWidth() - 55, this.getHeight() - 55, 40, 40, ClickType.LEFT)) {
			info = "Please Login";
			authProgress.webViewLogin();
		}
	}
	
	public Scroll scroll() {
		int mouse = Mouse.getDWheel();

        if(mouse > 0) {
        	return Scroll.UP;
        }else if(mouse < 0) {
        	return Scroll.DOWN;
        }else {
        	return null;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
