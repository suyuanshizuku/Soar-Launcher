package soar.media;

import java.util.ArrayList;

public class MediaManager {

	public ArrayList<Media> medias = new ArrayList<Media>();
	
	public MediaManager() {
		medias.add(new Media("Discord", "https://discord.gg/NX8nffeJct", "B"));
		medias.add(new Media("Github", "https://github.com/EldoDebug", "C"));
		medias.add(new Media("Twitter", "https://twitter.com/EldoDebug", "I"));
		medias.add(new Media("Youtube", "https://www.youtube.com/channel/UCJYcrccMmfPw3-0EZneKNEA", "J"));
	}
	
	public ArrayList<Media> getMedias() {
		return this.medias;
	}
}
