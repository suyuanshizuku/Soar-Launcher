package soar.utils.mouse;

import org.lwjgl.input.Mouse;

public class MouseUtils {
	
	private static boolean currentClick, clicked, released;
	
	public static boolean isClicked() {
		
		if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1) || Mouse.isButtonDown(2) || Mouse.isButtonDown(3) || Mouse.isButtonDown(4)) {
			currentClick = true;
		}else {
			currentClick = false;
			clicked = false;
			released = true;
		}
		
		if(currentClick == true && clicked == false) {
			clicked = true;
			return true;
		}
		
		return false;
	}
	
	public static boolean isReleased() {
		
		if(released == true) {
			released = false;
			return true;
		}
		
		return false;
	}
	
	public static boolean isClick(ClickType clickType) {
		
		int mouseID = 0;
		
		if(clickType.equals(ClickType.LEFT)) {
			mouseID = 0;
		}
		
		if(clickType.equals(ClickType.MIDDLE)) {
			mouseID = 2;
		}
		
		if(clickType.equals(ClickType.RIGHT)) {
			mouseID = 3;
		}
		
		return Mouse.isButtonDown(mouseID);
	}
	
    public static boolean isInside(int mouseX, int mouseY, double x, double y, double width, double height) {
        return (mouseX > x && mouseX < x + width) && (mouseY > y && mouseY < y + height);
    }
    
    public static boolean isInsideClick(int mouseX, int mouseY, double x, double y, double width, double height, ClickType clickType) {
    	
    	if(clickType.equals(ClickType.LEFT)) {
            return (mouseX > x && mouseX < x + width) && (mouseY > y && mouseY < y + height) && Mouse.isButtonDown(0);
    	}
    	
    	if(clickType.equals(ClickType.MIDDLE)) {
            return (mouseX > x && mouseX < x + width) && (mouseY > y && mouseY < y + height) && Mouse.isButtonDown(2);
    	}
    	
        return (mouseX > x && mouseX < x + width) && (mouseY > y && mouseY < y + height) && Mouse.isButtonDown(1);
    }
}
