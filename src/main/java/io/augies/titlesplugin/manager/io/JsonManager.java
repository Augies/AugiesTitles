package io.augies.titlesplugin.manager.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.augies.titlesplugin.TitlesPlugin;
import io.augies.titlesplugin.config.DatabaseConnectionConfiguration;
import io.augies.titlesplugin.util.IoUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;

public class JsonManager {
    private final TitlesPlugin plugin;
    private final File pluginFolder;
    private static final String databaseConnectionConfigPath = "databaseConfig.json";

    private DatabaseConnectionConfiguration databaseConnectionConfiguration;

    private final Gson gson;

    public JsonManager(){
        plugin = TitlesPlugin.getInstance();
        pluginFolder = plugin.getDataFolder();
        gson = new GsonBuilder().setPrettyPrinting().create();
        reloadJsonConfigurations();
    }

    public void reloadJsonConfigurations(){
        if(!pluginFolder.exists()){
            pluginFolder.mkdir();
        }
        databaseConnectionConfiguration = loadDatabaseConnectionConfiguration();
    }

    public <T> T loadConfiguration(String path, Class<T> clazz){
        T config = null;
        String fileLocation = IoUtils.getPathOfFileInFolder(pluginFolder, path);
        try {
            File configFile = new File(fileLocation);
            if(configFile.exists()){
                config = gson.fromJson(Files.newBufferedReader(configFile.toPath()), clazz);
            }else{
                plugin.logInfo("Intializing " + path + "...");
                config = clazz.getDeclaredConstructor().newInstance();
                Writer writer = new FileWriter(fileLocation);
                gson.toJson(config, writer);
                writer.flush();
                writer.close();
            }
        } catch (Exception e) { //This can have like 5 different exceptions thrown
            e.printStackTrace();
        }
        return config;
    }

    public DatabaseConnectionConfiguration loadDatabaseConnectionConfiguration() {
        return loadConfiguration(databaseConnectionConfigPath, DatabaseConnectionConfiguration.class);
    }

    public DatabaseConnectionConfiguration getDatabaseConnectionConfiguration(){
        return this.databaseConnectionConfiguration;
    }
}
