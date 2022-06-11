package soar.utils.json;

public class MinecraftAssetInfo
{
    private final String assetIndex;
    private final String sha1;
    private final String url;
    
    public MinecraftAssetInfo(final String assetIndex, final String sha1, final String url) {
        this.assetIndex = assetIndex;
        this.sha1 = sha1;
        this.url = url;
    }
    
    public String getAssetIndex() {
        return this.assetIndex;
    }
    
    public String getSha1() {
        return this.sha1;
    }
    
    public String getUrl() {
        return this.url;
    }
}
