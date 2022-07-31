package soar;

import java.awt.Color;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import soar.auth.AuthProgress;
import soar.gui.Gui;
import soar.gui.GuiSplashScreen;
import soar.gui.SettingRam;
import soar.media.Media;
import soar.media.MediaManager;
import soar.utils.ClientUtils;
import soar.utils.ColorUtils;
import soar.utils.EnumInfo;
import soar.utils.EnumScene;
import soar.utils.FileUtils;
import soar.utils.FontUtils;
import soar.utils.Logger;
import soar.utils.RoundedUtils;
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
	
	public EnumInfo info;
	public EnumScene scene;
	public String checkInfo = "Check Files";
	
	private ArrayList<String> logs = new ArrayList<String>();
	
	private String version = "2.2.5";

	private String username, token, refreshToken, id;
	
	private float scrollY = 0;
	private Animation scrollAnimation = new Animation(0.0F);

	public Logger logger = new Logger();
    public static boolean loaded = false;
    private boolean load2 = false;
    
    public SettingRam settingRam;
    
	public Soar() {
		logger.info("Setting up...");
		this.setup();
		this.createDisplay();
	}
	
	@Override
	public void setup() {
		
		instance = this;
		
		super.setup();
		this.setTitle("Soar Launcher " + "v" + version);
		this.setWidth(960);
		this.setHeight(535);
		this.setMaxFPS(120);
		
		logger.info("Drawing Screen!");
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY) {
		
		Gui.drawGradientRound(0, 0, this.getWidth(), this.getHeight(), 0, ColorUtils.getClientColor(0).getRGB(), ColorUtils.getClientColor(90).getRGB(), ColorUtils.getClientColor(180).getRGB(), ColorUtils.getClientColor(270).getRGB());

		if(loaded && scene == EnumScene.MAIN) {
			
			int offsetY = 25;
			int changeLogY = 138;
			
			Gui.drawRectangle(this.getWidth() - 70, 0, this.getWidth(), this.getHeight(), new Color(20, 20, 20, 60).getRGB());
			
			FontUtils.regular_bold40.drawString(20, 10, "Soar Launcher", org.newdawn.slick.Color.white);
			
			for(Media m : mediaManager.getMedias()) {
				FontUtils.icon40.drawString(this.getWidth() - 55, offsetY, m.getFontID(), org.newdawn.slick.Color.white);
				offsetY+=70;
			}
			
			Gui.drawSmoothGradientRound(this.getWidth() / 2 + 140, this.getHeight() - 85, 240, 70, 16, new Color(255, 206, 13).getRGB(), new Color(255, 191, 4).getRGB(), new Color(255, 191, 4).getRGB(), new Color(255, 206, 13).getRGB());
			FontUtils.regular_bold30.drawString(this.getWidth() / 2 - (FontUtils.regular_bold30.getWidth(this.getInfo()) / 2) + 262, this.getHeight() - 70, this.getInfo(), new org.newdawn.slick.Color(112, 74, 26));
			
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
			FontUtils.icon40.drawString(this.getWidth() - 55, this.getHeight() - 55, "D", org.newdawn.slick.Color.white);
		}else if(!loaded){
			new Thread() {
				@Override
				public void run() {
					
					if(!load2) {
						load2 = true;
						
						logger.info("Loading theme...");
						ThemeParser.init();
						
						logger.info("Downloading Changelog...");
						
						try {
					        URL url = new URL("https://raw.githubusercontent.com/EldoDebug/Soar-Launcher/main/Changelog");
					        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
					        
					        String inputLine;
					        while ((inputLine = in.readLine()) != null) {
					            logs.add(inputLine);
					        }
					        in.close();
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
							info = EnumInfo.LOGIN;
						}else {
							info = EnumInfo.LAUNCH;
							authProgress.refreshTokenLogin();
						}
						
						logger.info("Loading Setting Ram");
						settingRam = new SettingRam();
						
						scene = EnumScene.MAIN;
						loaded = true;
					}
				}
			}.start();
		}
		
		if(scene == EnumScene.SETTINGS) {
			Gui.drawRound(this.getWidth() / 2 - (600 / 2), this.getHeight() / 2 - (400 / 2), 600, 400, 12, new Color(20, 20, 20, 60).getRGB());
			
			FontUtils.regular_bold40.drawString(200, 75, "Settings", org.newdawn.slick.Color.white);
			FontUtils.icon40.drawString(this.getWidth() - 240, 85, "E", org.newdawn.slick.Color.white);
			FontUtils.regular22.drawString(200, 130, "Memory: " + settingRam.currentRam + "GB");
			settingRam.setPosition(280, 160);
			settingRam.drawScreen(mouseX, mouseY);
			
			RoundedUtils.drawRound(200, 210, 180, 50, 8,  new Color(20, 20, 20, 60).getRGB());
			FontUtils.regular22.drawString(230, 223, checkInfo, org.newdawn.slick.Color.white);
			
			RoundedUtils.drawRound(200, 280, 180, 50, 8,  new Color(20, 20, 20, 60).getRGB());
			FontUtils.regular22.drawString(250, 293, "Logout", org.newdawn.slick.Color.white);
		}
		
		GuiSplashScreen.drawScreen();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY) {
		
		int offsetY = 25;
		Desktop desktop = Desktop.getDesktop();
		
		if(scene == EnumScene.MAIN) {
			
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
			
			if(MouseUtils.isInsideClick(mouseX, mouseY, this.getWidth() / 2 + 140, this.getHeight() - 85, 240, 70, ClickType.LEFT)) {
				if(info.equals(EnumInfo.LAUNCH)) {
					new Thread() {
						@Override
						public void run() {
							try {
								info = EnumInfo.LAUNCHING;
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
				}else {
					if(info.equals(EnumInfo.LOGIN)) {
						logger.info("Login...");
						authProgress.webViewLogin();
					}
				}
			}
			
			if(MouseUtils.isInsideClick(mouseX, mouseY, this.getWidth() - 55, this.getHeight() - 55, 40, 40, ClickType.LEFT)) {
				scene = EnumScene.SETTINGS;
			}
		}

		if(scene == EnumScene.SETTINGS) {
			if(MouseUtils.isInside(mouseX, mouseY, this.getWidth() - 240, 80, 50, 50)) {
				settingRam.save();
				scene = EnumScene.MAIN;
			}
			
			if(MouseUtils.isInside(mouseX, mouseY, 200, 210, 180, 50) && checkInfo.equals("Check Files")) {
				checkInfo = "Checking...";
				ClientUtils.checkFiles();
			}
			
			if(MouseUtils.isInside(mouseX, mouseY, 200, 280, 180, 50)) {
				info = EnumInfo.LOGIN;
				authProgress.webViewLogin();
			}
			
			settingRam.mouseClicked(mouseX, mouseY);
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		if(scene == EnumScene.SETTINGS) {
			settingRam.mouseReleased(mouseX, mouseY);
		}
	}
	
	private String getInfo() {
		
		if(info.equals(EnumInfo.DOWNLOADING)) {
			return "Downloading...";
		}else if(info.equals(EnumInfo.LAUNCH)) {
			return "Launch";
		}else if(info.equals(EnumInfo.LAUNCHING)) {
			return "Launching...";
		}else if(info.equals(EnumInfo.LOADING)) {
			return "Loading...";
		}else if(info.equals(EnumInfo.LOGIN)) {
			return "Please Login";
		}
		
		return "Error";
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
