package soar.utils.json;

import java.io.*;
import com.google.gson.*;
import java.util.*;

public class MinecraftJsonParser
{
    public static MinecraftJson parseJson(final File file) {
        try {
            final FileReader reader = new FileReader(file);
            final Gson gson = new Gson();
            final JsonObject json = gson.fromJson(reader, JsonObject.class);
            final JsonObject assetIndexObj = json.get("assetIndex").getAsJsonObject();
            final String assetIndex = assetIndexObj.get("id").getAsString();
            final String assetSHA1 = assetIndexObj.get("sha1").getAsString();
            final String assetURL = assetIndexObj.get("url").getAsString();
            final MinecraftAssetInfo assetInfo = new MinecraftAssetInfo(assetIndex, assetSHA1, assetURL);
            final List<MinecraftLibrary> libs = new ArrayList<MinecraftLibrary>();
            final List<MinecraftNativeLibrary> nativeLibs = new ArrayList<MinecraftNativeLibrary>();
            final JsonArray librariesObj = json.get("libraries").getAsJsonArray();
            for (final JsonElement elem : librariesObj) {
                final JsonObject downloadsObj = elem.getAsJsonObject().get("downloads").getAsJsonObject();
                if (downloadsObj.has("classifiers")) {
                    final JsonObject classifiersObj = downloadsObj.get("classifiers").getAsJsonObject();
                    String linuxDownloadURL = null;
                    String macosxDownloadURL = null;
                    String windowsDownloadURL = null;
                    String win32DownloadURL = null;
                    String win64DownloadURL = null;
                    if (classifiersObj.has("natives-linux")) {
                        final JsonObject linuxObj = classifiersObj.get("natives-linux").getAsJsonObject();
                        linuxDownloadURL = linuxObj.get("url").getAsString();
                    }
                    if (classifiersObj.has("natives-osx")) {
                        final JsonObject macosxObj = classifiersObj.get("natives-osx").getAsJsonObject();
                        macosxDownloadURL = macosxObj.get("url").getAsString();
                    }
                    if (classifiersObj.has("natives-windows")) {
                        final JsonObject windowsObj = classifiersObj.get("natives-windows").getAsJsonObject();
                        windowsDownloadURL = windowsObj.get("url").getAsString();
                    }
                    if (classifiersObj.has("natives-windows-32")) {
                        final JsonObject win32Obj = classifiersObj.get("natives-windows-32").getAsJsonObject();
                        win32DownloadURL = win32Obj.get("url").getAsString();
                    }
                    if (classifiersObj.has("natives-windows-64")) {
                        final JsonObject win64Obj = classifiersObj.get("natives-windows-64").getAsJsonObject();
                        win64DownloadURL = win64Obj.get("url").getAsString();
                    }
                    final MinecraftNativeLibrary nativeLib = new MinecraftNativeLibrary(linuxDownloadURL, macosxDownloadURL, windowsDownloadURL, win32DownloadURL, win64DownloadURL);
                    nativeLibs.add(nativeLib);
                }
                else {
                    if (!downloadsObj.has("artifact")) {
                        continue;
                    }
                    final JsonObject artifactObj = downloadsObj.get("artifact").getAsJsonObject();
                    final MinecraftLibrary lib = new MinecraftLibrary(elem.getAsJsonObject().get("name").getAsString(), artifactObj.get("url").getAsString(), artifactObj.get("path").getAsString());
                    libs.add(lib);
                }
            }
            return new MinecraftJson(assetInfo, libs, nativeLibs, json.get("mainClass").getAsString());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
