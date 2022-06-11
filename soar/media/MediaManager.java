package soar.media;

import java.util.ArrayList;

import soar.media.impl.MediaDiscord;
import soar.media.impl.MediaGithub;
import soar.media.impl.MediaTwitter;
import soar.media.impl.MediaYoutube;

public class MediaManager {

	public ArrayList<Media> medias = new ArrayList<Media>();
	
	public MediaManager() {
		medias.add(new MediaDiscord());
		medias.add(new MediaGithub());
		medias.add(new MediaTwitter());
		medias.add(new MediaYoutube());
	}
	
	public ArrayList<Media> getMedias() {
		return this.medias;
	}
}
