package me.eldodebug.soarlauncher.utils;

import org.lwjgl.opengl.GL11;

public class GlUtils {
    public static void startScale(float x, float y, float scale) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0);
        GL11.glScalef(scale, scale, 1);
        GL11.glTranslatef(-x, -y, 0);
    }
    
    public static void startScale(float x, float y, float width, float height, float scale) {
    	GL11.glPushMatrix();
    	GL11.glTranslatef((x + (x + width)) / 2, (y + (y + height)) / 2, 0);
    	GL11.glScalef(scale, scale, 1);
        GL11.glTranslatef(-(x + (x + width)) / 2, -(y + (y + height)) / 2, 0);
    }
    
    public static void stopScale() {
        GL11.glPopMatrix();
    }
}
