package io.augies.titlesplugin.manager.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.augies.titlesplugin.TitlesPlugin;
import io.augies.titlesplugin.config.StorageConfiguration;
import io.augies.titlesplugin.util.IoUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;

public class JsonManager {
    private final TitlesPlugin plugin;
    private final File pluginFolder;
    private static final String databaseConnectionConfigPath = "databaseConfig.json";

    private StorageConfiguration storageConfiguration;

    private final Gson gson;

    public JsonManager(){
        plugin = TitlesPlugin.getInstance();
        pluginFolder = plugin.getDataFolder();
        gson = new GsonBuilder().setPrettyPrinting().create();
        reloadJsonFiles();
    }

    public void reloadJsonFiles(){
        if(!pluginFolder.exists()){
            pluginFolder.mkdir();
        }
        storageConfiguration = loadStorageConfiguration();
    }

    public <T> T loadJson(String path, Class<T> clazz){
        T object = null;
        String fileLocation = IoUtils.getPathOfFileInFolder(pluginFolder, path);
        try {
            File configFile = new File(fileLocation);
            if(configFile.exists()){
                object = gson.fromJson(Files.newBufferedReader(configFile.toPath()), clazz);
            }else{
                plugin.logInfo("Intializing " + path + "...");
                object = clazz.getDeclaredConstructor().newInstance();
                Writer writer = new FileWriter(fileLocation);
                gson.toJson(object, writer);
                writer.flush();
                writer.close();
            }
        } catch (Exception e) { //This can have like 5 different exceptions thrown
            e.printStackTrace();
        }
        return object;
    }

    public <T> T loadJson(File file, Class<T> clazz){
        return loadJson(file.getPath(), clazz);
    }

    public StorageConfiguration loadStorageConfiguration() {
        return loadJson(databaseConnectionConfigPath, StorageConfiguration.class);
    }

    public StorageConfiguration getStorageConfiguration(){
        return this.storageConfiguration;
    }
}
