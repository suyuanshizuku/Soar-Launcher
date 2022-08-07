package me.eldodebug.soarlauncher.gui;

import org.lwjgl.opengl.GL11;

public class Gui {

	public static void drawRectangle(double x, double y, double width, double height, int color) {
		
		float f = (color >> 24 & 0xFF) / 255.0F;
		float f1 = (color >> 16 & 0xFF) / 255.0F;
		float f2 = (color >> 8 & 0xFF) / 255.0F;
		float f3 = (color & 0xFF) / 255.0F;
		
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		
		GL11.glPushMatrix();
		GL11.glColor4f(f1, f2, f3, f);
		
        GL11.glBegin(7);
        GL11.glVertex2d(x + width, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y + height);
        GL11.glVertex2d(x + width, y + height);
        GL11.glEnd();
        GL11.glPopMatrix();
	}
	
	public static void drawRound(double x, double y, double width, double height, int radius, int color) {
		
		float f = (color >> 24 & 0xFF) / 255.0F;
		float f1 = (color >> 16 & 0xFF) / 255.0F;
		float f2 = (color >> 8 & 0xFF) / 255.0F;
		float f3 = (color & 0xFF) / 255.0F;
		
		GL11.glPushAttrib(0);
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		
		x *= 2.0D;
		y *= 2.0D;
		width *= 2.0D;
		height *= 2.0D;
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBegin(GL11.GL_POLYGON);
		
		int i;
		
		for(i = 0; i <= 90; i += 3) {
			GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
		}
		
		for(i = 90; i <= 180; i += 3) {
			GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + height - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
		}
		
		for (i = 0; i <= 90; i += 3) {
			GL11.glVertex2d(x + width - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + height - radius + Math.cos(i * Math.PI / 180.0D) * radius);
		}
		
		for (i = 90; i <= 180; i += 3) {
			GL11.glVertex2d(x + width - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius);
		}
		
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glScaled(2.0D, 2.0D, 2.0D);
		GL11.glPopAttrib();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public static void drawRoundUnderRect(double x, double y, double width, double height, int radius, int color) {
		
		float f = (color >> 24 & 0xFF) / 255.0F;
		float f1 = (color >> 16 & 0xFF) / 255.0F;
		float f2 = (color >> 8 & 0xFF) / 255.0F;
		float f3 = (color & 0xFF) / 255.0F;
		
		GL11.glPushAttrib(0);
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		
		x *= 2.0D;
		y *= 2.0D;
		width *= 2.0D;
		height *= 2.0D;
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBegin(GL11.GL_POLYGON);
		
		int i;
		
		for(i = 0; i <= 90; i += 3) {
			GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
		}
		
		for(i = 90; i <= 180; i += 3) {
			GL11.glVertex2d(x + 0 + Math.sin(i * Math.PI / 180.0D) * 0 * -1.0D, y + height - 0 + Math.cos(i * Math.PI / 180.0D) * 0 * -1.0D); 
		}
		
		for (i = 0; i <= 90; i += 3) {
			GL11.glVertex2d(x + width - 0 + Math.sin(i * Math.PI / 180.0D) * 0, y + height - 0 + Math.cos(i * Math.PI / 180.0D) * 0);
		}
		
		for (i = 90; i <= 180; i += 3) {
			GL11.glVertex2d(x + width - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius);
		}
		
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glScaled(2.0D, 2.0D, 2.0D);
		GL11.glPopAttrib();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	public static void drawSmoothRound(double x, double y, double width, double height, int radius, int color) {
		
		float f = (color >> 24 & 0xFF) / 255.0F;
		float f1 = (color >> 16 & 0xFF) / 255.0F;
		float f2 = (color >> 8 & 0xFF) / 255.0F;
		float f3 = (color & 0xFF) / 255.0F;
		
		GL11.glPushAttrib(0);
		GL11.glScaled(0.5D, 0.5D, 0.5D);
		
		x *= 2.0D;
		y *= 2.0D;
		width *= 2.0D;
		height *= 2.0D;
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glBegin(GL11.GL_POLYGON);
		
		int i;
		
		for(i = 0; i <= 90; i += 3) {
			GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
		}
		
		for(i = 90; i <= 180; i += 3) {
			GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + height - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
		}
		
		for (i = 0; i <= 90; i += 3) {
			GL11.glVertex2d(x + width - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + height - radius + Math.cos(i * Math.PI / 180.0D) * radius);
		}
		
		for (i = 90; i <= 180; i += 3) {
			GL11.glVertex2d(x + width - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius);
		}
		
		GL11.glEnd();
		GL11.glBegin(GL11.GL_LINE_LOOP);
		
		for(i = 0; i <= 90; i += 3) {
			GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
		}
		
		for(i = 90; i <= 180; i += 3) {
			GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + height - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
		}
		
		for (i = 0; i <= 90; i += 3) {
			GL11.glVertex2d(x + width - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + height - radius + Math.cos(i * Math.PI / 180.0D) * radius);
		}
		
		for (i = 90; i <= 180; i += 3) {
			GL11.glVertex2d(x + width - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius);
		}
		
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glScaled(2.0D, 2.0D, 2.0D);
		GL11.glPopAttrib();
		GL11.glLineWidth(1);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
    public static void drawSmoothGradientRound(double x, double y, double width, double height, float radius, int color, int color2, int color3, int color4) {
    	setColor(-1);
    	GL11.glDisable(GL11.GL_TEXTURE_2D);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	GL11.glEnable(GL11.GL_LINE_SMOOTH);
    	GL11.glShadeModel(GL11.GL_SMOOTH);
        
    	GL11.glPushAttrib(0);
    	GL11.glScaled(0.5D, 0.5D, 0.5D);
        x *= 2.0D;
        y *= 2.0D;
        width *= 2.0D;
        height *= 2.0D;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        setColor(color);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glBegin(9);
        int i;
        for (i = 0; i <= 90; i += 3)
        	GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
        setColor(color2);
        for (i = 90; i <= 180; i += 3)
        	GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + height - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
        setColor(color3);
        for (i = 0; i <= 90; i += 3)
        	GL11.glVertex2d(x + width - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + height - radius + Math.cos(i * Math.PI / 180.0D) * radius); 
        setColor(color4);
        for (i = 90; i <= 180; i += 3)
        	GL11.glVertex2d(x + width - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius); 
        GL11.glEnd();
		GL11.glBegin(GL11.GL_LINE_LOOP);
        for (i = 0; i <= 90; i += 3)
        	GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
        setColor(color2);
        for (i = 90; i <= 180; i += 3)
        	GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + height - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
        setColor(color3);
        for (i = 0; i <= 90; i += 3)
        	GL11.glVertex2d(x + width - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + height - radius + Math.cos(i * Math.PI / 180.0D) * radius); 
        setColor(color4);
        for (i = 90; i <= 180; i += 3)
        	GL11.glVertex2d(x + width - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius); 
        
		GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        GL11.glPopAttrib();
        

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(GL11.GL_FLAT);
        setColor(-1);
    }
    
    public static void drawGradientRound(double x, double y, double width, double height, float radius, int color, int color2, int color3, int color4) {
    	setColor(-1);
    	GL11.glDisable(GL11.GL_TEXTURE_2D);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    	GL11.glEnable(GL11.GL_LINE_SMOOTH);
    	GL11.glShadeModel(GL11.GL_SMOOTH);
        
    	GL11.glPushAttrib(0);
    	GL11.glScaled(0.5D, 0.5D, 0.5D);
        x *= 2.0D;
        y *= 2.0D;
        width *= 2.0D;
        height *= 2.0D;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        setColor(color);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glBegin(9);
        int i;
        for (i = 0; i <= 90; i += 3)
        	GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
        setColor(color2);
        for (i = 90; i <= 180; i += 3)
        	GL11.glVertex2d(x + radius + Math.sin(i * Math.PI / 180.0D) * radius * -1.0D, y + height - radius + Math.cos(i * Math.PI / 180.0D) * radius * -1.0D); 
        setColor(color3);
        for (i = 0; i <= 90; i += 3)
        	GL11.glVertex2d(x + width - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + height - radius + Math.cos(i * Math.PI / 180.0D) * radius); 
        setColor(color4);
        for (i = 90; i <= 180; i += 3)
        	GL11.glVertex2d(x + width - radius + Math.sin(i * Math.PI / 180.0D) * radius, y + radius + Math.cos(i * Math.PI / 180.0D) * radius); 
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glScaled(2.0D, 2.0D, 2.0D);
        GL11.glPopAttrib();
        

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glShadeModel(GL11.GL_FLAT);
        setColor(-1);
    }
    
    public static void setColor(int color) {
        float a = (color >> 24 & 0xFF) / 255.0F;
        float r = (color >> 16 & 0xFF) / 255.0F;
        float g = (color >> 8 & 0xFF) / 255.0F;
        float b = (color & 0xFF) / 255.0F;
        GL11.glColor4f(r, g, b, a);
    }
}
