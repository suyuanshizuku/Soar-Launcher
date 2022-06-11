package soar.media;

public class Media {
	
	private String name, url, fontID;
	private org.newdawn.slick.Color color;
	
	public Media(String name, String url, String fontID, org.newdawn.slick.Color color) {
		this.name = name;
		this.url = url;
		this.fontID = fontID;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getFontID() {
		return fontID;
	}

	public void setFontID(String fontID) {
		this.fontID = fontID;
	}

	public org.newdawn.slick.Color getColor() {
		return color;
	}

	public void setColor(org.newdawn.slick.Color color) {
		this.color = color;
	}
}
