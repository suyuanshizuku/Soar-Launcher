package soar.media;

public class Media {
	
	private String name, url, fontID;
	
	public Media(String name, String url, String fontID) {
		this.name = name;
		this.url = url;
		this.fontID = fontID;
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
}
