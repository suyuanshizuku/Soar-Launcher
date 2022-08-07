package me.eldodebug.soarlauncher.utils;

import java.awt.Color;

import me.eldodebug.soarlauncher.SoarLauncher;

public class ColorUtils {
	
	public static Color getClientColor(int index, int alpha) {
		
		if(SoarLauncher.instance.colorManager.getRed() != 0 || SoarLauncher.instance.colorManager.getGreen() != 0
				|| SoarLauncher.instance.colorManager.getBlue() != 0 || SoarLauncher.instance.colorManager.getRed2() != 0
				|| SoarLauncher.instance.colorManager.getBlue2() != 0 || SoarLauncher.instance.colorManager.getGreen2() != 0) {
			
			return ColorUtils.interpolateColorsBackAndForth(15, index, new Color(SoarLauncher.instance.colorManager.getRed(), SoarLauncher.instance.colorManager.getGreen(), SoarLauncher.instance.colorManager.getBlue(), alpha), new Color(SoarLauncher.instance.colorManager.getRed2(), SoarLauncher.instance.colorManager.getGreen2(), SoarLauncher.instance.colorManager.getBlue2(), alpha), false);
		}
		
		return ColorUtils.interpolateColorsBackAndForth(15, index, new Color(56, 186, 187, alpha), new Color(54, 212, 176, alpha), false);
	}
	
	public static Color getClientColor(int index) {
		return ColorUtils.getClientColor(index, 255);
	}
	
	private static Color interpolateColorsBackAndForth(int speed, int index, Color start, Color end, boolean trueColor) {
        int angle = (int) (((System.currentTimeMillis()) / speed + index) % 360);
        angle = (angle >= 180 ? 360 - angle : angle) * 2;
        return trueColor ? ColorUtils.getInterpolateColorHue(start, end, angle / 360f) : ColorUtils.getInterpolateColor(start, end, angle / 360f);
    }
	
	public static Color getInterpolateColor(Color color1, Color color2, float amount) {
        amount = Math.min(1, Math.max(0, amount));
        return new Color(MathUtils.interpolateInt(color1.getRed(), color2.getRed(), amount), MathUtils.interpolateInt(color1.getGreen(), color2.getGreen(), amount), MathUtils.interpolateInt(color1.getBlue(), color2.getBlue(), amount), MathUtils.interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
	}
	
    private static Color getInterpolateColorHue(Color color1, Color color2, float amount) {
        amount = Math.min(1, Math.max(0, amount));

        float[] color1HSB = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
        float[] color2HSB = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);

        Color resultColor = Color.getHSBColor(MathUtils.interpolateFloat(color1HSB[0], color2HSB[0], amount), MathUtils.interpolateFloat(color1HSB[1], color2HSB[1], amount), MathUtils.interpolateFloat(color1HSB[2], color2HSB[2], amount));

        return new Color(resultColor.getRed(), resultColor.getGreen(), resultColor.getBlue(), MathUtils.interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
    }
}
