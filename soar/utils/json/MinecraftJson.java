package soar.utils.json;

import java.util.*;

public class MinecraftJson
{
    private final MinecraftAssetInfo assetInfo;
    private final List<MinecraftLibrary> libraries;
    private final List<MinecraftNativeLibrary> nativeLibraries;
    private final String mainClass;
    
    public MinecraftJson(final MinecraftAssetInfo assetInfo, final List<MinecraftLibrary> libraries, final List<MinecraftNativeLibrary> nativeLibraries, final String mainClass) {
        this.assetInfo = assetInfo;
        this.libraries = libraries;
        this.nativeLibraries = nativeLibraries;
        this.mainClass = mainClass;
    }
    
    public MinecraftAssetInfo getAssetInfo() {
        return this.assetInfo;
    }
    
    public List<MinecraftLibrary> getLibraries() {
        return this.libraries;
    }
    
    public List<MinecraftNativeLibrary> getNativeLibraries() {
        return this.nativeLibraries;
    }
    
    public String getMainClass() {
        return this.mainClass;
    }
}