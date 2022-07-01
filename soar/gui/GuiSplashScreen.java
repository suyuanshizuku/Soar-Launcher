package soar.gui;

import java.awt.Color;

import soar.Soar;
import soar.utils.FontUtils;
import soar.utils.TimerUtils;
import soar.utils.animation.Animation;

public class GuiSplashScreen {

	private static Animation animation = new Animation(0.0F);
	private static Animation animation2 = new Animation(0.0F);
	private static Animation animation3 = new Animation(255F);
	
	private static TimerUtils timer = new TimerUtils();
	
	private static boolean b1 = true, b2 = false, b3 = false, b4 = true;
	
	public static void drawScreen() {
		
		Gui.drawRectangle(0, 0, Soar.instance.getWidth(), Soar.instance.getHeight(), new Color(0, 0, 0, (int) animation3.getValue()).getRGB());
		
		animation.setAnimation(b1 ? 255 : 0, 4);
		animation2.setAnimation(b2 ? 255 : 0, 4);
		animation3.setAnimation(b4 ? 255 : 0, 4);
		
		if(Soar.loaded) {
			b1 = false;
			if(animation.getValue() < 1) {
				b2 = true;
				if(!b3) {
					timer.reset();
					b3 = true;
				}
				if(timer.delay(3500)) {
					b2 = false;
				}
				if(timer.delay(5000)) {
					b4 = false;
				}
			}
		}
		
		FontUtils.regular40.drawString((Soar.instance.getWidth() / 2) - (FontUtils.regular40.getWidth("Loading SoarLauncher...") / 2), (Soar.instance.getHeight() / 2) - (FontUtils.regular40.getHeight("Loading SoarLauncher...") / 2) - 10, "Loading SoarLauncher", new org.newdawn.slick.Color(255, 255, 255, (int) animation.getValue()));
		FontUtils.regular40.drawString((Soar.instance.getWidth() / 2) - (FontUtils.regular40.getWidth("Welcome to SoarLauncher!") / 2), (Soar.instance.getHeight() / 2) - (FontUtils.regular40.getHeight("Welcome to SoarLauncher!") / 2) - 10, "Welcome to SoarLauncher!", new org.newdawn.slick.Color(255, 255, 255, (int) animation2.getValue()));
	}
}
