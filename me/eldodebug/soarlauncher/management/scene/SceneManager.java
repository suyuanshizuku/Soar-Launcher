package me.eldodebug.soarlauncher.management.scene;

import java.util.ArrayList;

import me.eldodebug.soarlauncher.SoarLauncher;
import me.eldodebug.soarlauncher.management.scene.impl.SettingsScene;
import me.eldodebug.soarlauncher.management.scene.impl.SoarLiteScene;
import me.eldodebug.soarlauncher.management.scene.impl.SoarScene;
import me.eldodebug.soarlauncher.utils.Logger;

public class SceneManager {

	private ArrayList<Scene> scenes = new ArrayList<Scene>();
	
	public SceneManager() {
		Logger.info("Loading Scene Manager");
		scenes.add(new SettingsScene());
		scenes.add(new SoarScene());
		scenes.add(new SoarLiteScene());
	}

	public ArrayList<Scene> getScenes() {
		return scenes;
	}
	
	public Scene getScenesByName(String name) {
		return scenes.stream().filter(scene -> scene.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}
	
	public void setInfoToLogin() {
		for(Scene s : SoarLauncher.instance.sceneManager.getScenes()) {
			if(!s.getName().equals("Settings")) {
				s.setInfo("Please Login");
			}
		}
	}
	
	public void setInfoToLaunch() {
		for(Scene s : SoarLauncher.instance.sceneManager.getScenes()) {
			if(!s.getName().equals("Settings")) {
				s.setInfo("Launch");
			}
		}
	}
	
	public void setInfoToLoading() {
		for(Scene s : SoarLauncher.instance.sceneManager.getScenes()) {
			if(!s.getName().equals("Settings")) {
				s.setInfo("Loading...");
			}
		}
	}
}
