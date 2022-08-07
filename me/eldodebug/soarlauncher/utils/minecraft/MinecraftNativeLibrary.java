package me.eldodebug.soarlauncher.utils.minecraft;

public class MinecraftNativeLibrary
{
    private final String linuxDownloadURL;
    private final String macosXDownloadURL;
    private final String windowsDownloadURL;
    private final String win64DownloadURL;
    private final String win32DownloadURL;
    
    public MinecraftNativeLibrary(final String linuxDownloadURL, final String macosXDownloadURL, final String windowsDownloadURL, final String win64DonwloadURL, final String win32DownloadURL) {
        this.linuxDownloadURL = linuxDownloadURL;
        this.macosXDownloadURL = macosXDownloadURL;
        this.windowsDownloadURL = windowsDownloadURL;
        this.win64DownloadURL = win64DonwloadURL;
        this.win32DownloadURL = win32DownloadURL;
    }
    
    public String getLinuxDownloadURL() {
        return this.linuxDownloadURL;
    }
    
    public String getMacosXDownloadURL() {
        return this.macosXDownloadURL;
    }
    
    public String getWindowsDownloadURL() {
        return this.windowsDownloadURL;
    }
    
    public String getWin64DownloadURL() {
        return this.win64DownloadURL;
    }
    
    public String getWin32DownloadURL() {
        return this.win32DownloadURL;
    }
}