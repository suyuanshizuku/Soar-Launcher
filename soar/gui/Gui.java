package soar.gui;

import soar.utils.RenderUtils;
import soar.utils.RoundedUtils;

public class Gui {
	
	public static void drawRectangle(int x, int y, int width, int height, int color) {
		RenderUtils.drawRectangle(x, y, width, height, color);
	}
	
	public static void drawRectangle(float x, float y, float width, float height, int color) {
		RenderUtils.drawRectangle(x, y, width, height, color);
	}
	
	public static void drawRectangle(double x, double y, double width, double height, int color) {
		RenderUtils.drawRectangle(x, y, width, height, color);
	}
	
	public static void drawRectangleOutline(int x, int y, int width, int height, float lineWidth, int color) {
		RenderUtils.drawRectangleOutline(x, y, width, height, lineWidth, color);
	}
	
	public static void drawRectangleOutline(float x, float y, float width, float height, float lineWidth, int color) {
		RenderUtils.drawRectangleOutline(x, y, width, height, lineWidth, color);
	}
	
	public static void drawRectangleOutline(double x, double y, double width, double height, float lineWidth, int color) {
		RenderUtils.drawRectangleOutline(x, y, width, height, lineWidth, color);
	}
	
	public static void drawRound(int x, int y, int width, int height, int radius, int color) {
		RoundedUtils.drawRound(x, y, width, height, radius, color);
	}
	
	public static void drawRound(float x, float y, float width, float height, int radius, int color) {
		RoundedUtils.drawRound(x, y, width, height, radius, color);
	}
	
	public static void drawRound(double x, double y, double width, double height, int radius, int color) {
		RoundedUtils.drawRound(x, y, width, height, radius, color);
	}
	
	public static void drawRoundUnderRect(int x, int y, int width, int height, int radius, int color) {
		RoundedUtils.drawRoundUnderRect(x, y, width, height, radius, color);
	}
	
	public static void drawRoundUnderRect(float x, float y, float width, float height, int radius, int color) {
		RoundedUtils.drawRoundUnderRect(x, y, width, height, radius, color);
	}
	
	public static void drawRoundUnderRect(double x, double y, double width, double height, int radius, int color) {
		RoundedUtils.drawRoundUnderRect(x, y, width, height, radius, color);
	}
	
	public static void drawSmoothRound(int x, int y, int width, int height, int radius, int color) {
		RoundedUtils.drawSmoothRound(x, y, width, height, radius, color);
	}
	
	public static void drawSmoothRound(float x, float y, float width, float height, int radius, int color) {
		RoundedUtils.drawSmoothRound(x, y, width, height, radius, color);
	}
	
	public static void drawSmoothRound(double x, double y, double width, double height, int radius, int color) {
		RoundedUtils.drawSmoothRound(x, y, width, height, radius, color);
	}
	
	public static void drawGradientRound(double x, double y, double width, double height, int radius, int color1, int color2, int color3, int color4) {
		RoundedUtils.drawGradientRound(x, y, width, height, radius, color1, color2, color3, color4);
	}
	
	public static void drawSmoothGradientRound(double x, double y, double width, double height, int radius, int color1, int color2, int color3, int color4) {
		RoundedUtils.drawSmoothGradientRound(x, y, width, height, radius, color1, color2, color3, color4);
	}
}
