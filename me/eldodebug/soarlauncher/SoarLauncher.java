package me.eldodebug.soarlauncher;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import me.eldodebug.Base;
import me.eldodebug.soarlauncher.gui.Gui;
import me.eldodebug.soarlauncher.gui.GuiSplashScreen;
import me.eldodebug.soarlauncher.management.auth.AuthManager;
import me.eldodebug.soarlauncher.management.changelog.ChangelogManager;
import me.eldodebug.soarlauncher.management.color.ColorManager;
import me.eldodebug.soarlauncher.management.file.FileManager;
import me.eldodebug.soarlauncher.management.scene.Scene;
import me.eldodebug.soarlauncher.management.scene.SceneManager;
import me.eldodebug.soarlauncher.management.setting.SettingsManager;
import me.eldodebug.soarlauncher.utils.ClientUtils;
import me.eldodebug.soarlauncher.utils.ColorUtils;
import me.eldodebug.soarlauncher.utils.Logger;
import me.eldodebug.soarlauncher.utils.animation.normal.Animation;
import me.eldodebug.soarlauncher.utils.animation.normal.Direction;
import me.eldodebug.soarlauncher.utils.animation.normal.impl.DecelerateAnimation;
import me.eldodebug.soarlauncher.utils.animation.normal.impl.EaseInOutQuad;
import me.eldodebug.soarlauncher.utils.animation.simple.SimpleAnimation;
import me.eldodebug.soarlauncher.utils.font.FontUtils;
import me.eldodebug.soarlauncher.utils.mouse.ClickType;
import me.eldodebug.soarlauncher.utils.mouse.MouseUtils;

public class SoarLauncher extends Base{

	public static SoarLauncher instance;
	
	private String name = "Soar Launcher", version = "3.0";
	
	public FileManager fileManager;
	public SceneManager sceneManager;
	public ChangelogManager changelogManager;
	public SettingsManager settingsManager;
	public AuthManager authManager;
	public ColorManager colorManager;
	
	public String currentScene;
	
	private boolean open;
	private boolean loadedAnimation;
	public boolean loaded;
	private boolean update;
	
	private Animation animation;
	private Animation opacityAnimation;
	
	private float clientOffsetY = 0;
	private SimpleAnimation clientAnimation = new SimpleAnimation(0.0F);
	
	public SoarLauncher() {
		
		Logger.info("Start Launcher!");
		
		instance = this;
		
		open = false;
		loadedAnimation = false;
		loaded = false;
		update = false;
		
		this.init();
		this.setup();
		this.createDisplay();
	}
	
	@Override
	public void setup() {
		Logger.info("Set up lwjgl...");
		super.setup();
		this.setTitle(name + " v" + version);
		this.setWidth(960);
		this.setHeight(535);
		this.setMaxFPS(120);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY) {
		
		int offsetY = 0;
		
		//DrawBackground
		Gui.drawRectangle(0, 0, this.getWidth(), this.getHeight(), new Color(238, 240, 245).getRGB());
		
		if(loaded) {
			//Render Scene
			for(Scene s : sceneManager.getScenes()) {
				if(currentScene.equals(s.getName())) {
					s.drawScreen(mouseX, mouseY);
				}
			}

			//Draw Side Bar
			Gui.drawRectangle(0, 0, 50 + (loadedAnimation ? animation.getValue() : 0), this.getHeight(), new Color(217, 223, 231).getRGB());
			
			Gui.drawRectangle(8, 15, 34, 3, new Color(255, 255, 255).getRGB());
			Gui.drawRectangle(8, 25, 34, 3, new Color(255, 255, 255).getRGB());
			Gui.drawRectangle(8, 35, 34, 3, new Color(255, 255, 255).getRGB());

			clientAnimation.setAnimation(clientOffsetY, 16);
			
			if(!currentScene.equals("Settings")) {
				Gui.drawSmoothRound(((loadedAnimation) ? (float) animation.getValue() : 0) - 110, 85 + clientAnimation.getValue(), 145, 38, 12, ColorUtils.getClientColor(0, (int) (loadedAnimation ? opacityAnimation.getValue() : 0)).getRGB());
			}
			
			for(Scene s : sceneManager.getScenes()) {
				
				s.fontAnimation.setAnimation(currentScene.equals(s.getName()) ? currentScene.equals("Settings") ? 0 : 175 : 0, 16);
				
				if(s.isClient()) {
					
					FontUtils.drawString(FontUtils.regular22, s.getName(), ((loadedAnimation) ? (float) animation.getValue() : 0) - 100, 90 + offsetY, new Color(80 + (int) s.fontAnimation.getValue(), 80 + (int) s.fontAnimation.getValue(), 80 + (int) s.fontAnimation.getValue(), (int) (loadedAnimation ? opacityAnimation.getValue() : 0)));
					
					if(currentScene.equals(s.getName())) {
						clientOffsetY = offsetY;
					}
					
					offsetY+=63;
				}
			}
			
			//Draw Settings
			FontUtils.drawString(FontUtils.icon26, "A", 12, this.getHeight() - 40, new Color(80, 80, 80));
			FontUtils.drawString(FontUtils.regular22, "Settings", 45 + ((loadedAnimation) ? (float) animation.getValue() : 0) - 120, this.getHeight() - 39, new Color(80, 80, 80, (int) (loadedAnimation ? opacityAnimation.getValue() : 0)));
			
			if(open) {
				if(!loadedAnimation) {
					animation = new EaseInOutQuad(250, 120);
					opacityAnimation = new DecelerateAnimation(250, 255);
					loadedAnimation = true;
				}
				animation.setDirection(Direction.FORWARDS);
				opacityAnimation.setDirection(Direction.FORWARDS);
			}else {
				if(loadedAnimation) {
					animation.setDirection(Direction.BACKWARDS);
					opacityAnimation.setDirection(Direction.BACKWARDS);
				}
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			if(open) {
				open = false;
			}
		}

		GuiSplashScreen.drawScreen();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY) {
		
		int offsetY = 0;
		
		if(!update) {
			if(MouseUtils.isInsideClick(mouseX, mouseY, 7, 10, 36, 32, ClickType.LEFT)) {
				open = !open;
			}
			
			if(MouseUtils.isInsideClick(mouseX, mouseY, 170, 0, this.getWidth(), this.getHeight(), ClickType.LEFT)) {
				open = false;
			}
			
			if(MouseUtils.isInside(mouseX, mouseY, 0, this.getHeight() - 50, 50 + (open ? 120 : 0), 50)) {
				currentScene = "Settings";
			}
			
			if(open) {
				for(Scene s : sceneManager.getScenes()) {
					if(s.isClient()) {
						if(MouseUtils.isInsideClick(mouseX, mouseY, 10, 85 + offsetY, 130, 38, ClickType.LEFT)) {
							currentScene = s.getName();
						}
						offsetY+=63;
					}
				}
			}
			
			if(loaded) {
				//Mouse Click Scene
				for(Scene s : sceneManager.getScenes()) {
					if(currentScene.equals(s.getName())) {
						s.mouseClicked(mouseX, mouseY);
					}
				}
			}
		}
	}
	
	private void init() {
		new Thread() {
			@Override
			public void run() {
				if(!loaded) {
					
					setCurrentScene("Soar");
					
					fileManager = new FileManager();
					sceneManager = new SceneManager();
					changelogManager = new ChangelogManager();
					settingsManager = new SettingsManager();
					authManager = new AuthManager();
					colorManager = new ColorManager();
					fileManager.loadClientSettings();
					
					authManager.refreshTokenLogin();
					
					if(ClientUtils.checkLauncherUpdate()) {
						update = true;
					}
					
					loaded = true;
				}
			}
		}.start();
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		
		//Mouse Release Scene
		if(loaded) {
			for(Scene s : sceneManager.getScenes()) {
				if(currentScene.equals(s.getName())) {
					s.mouseReleased(mouseX, mouseY);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new SoarLauncher();
	}

	public String getCurrentScene() {
		return currentScene;
	}

	public void setCurrentScene(String currentScene) {
		this.currentScene = currentScene;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}
}