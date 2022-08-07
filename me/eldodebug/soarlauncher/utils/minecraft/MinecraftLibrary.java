package me.eldodebug.soarlauncher.utils.minecraft;

public class MinecraftLibrary
{
    private final String name;
    private final String url;
    private final String path;
    
    public MinecraftLibrary(final String name, final String url, final String path) {
        this.name = name;
        this.url = url;
        this.path = path;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public String getPath() {
        return this.path;
    }
}