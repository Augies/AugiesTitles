package io.augies.titlesplugin.manager.io;

import com.google.gson.Gson;
import io.augies.titlesplugin.TitlesPlugin;
import io.augies.titlesplugin.config.DatabaseConnectionConfiguration;
import io.augies.titlesplugin.util.IoUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonManager {
    private final TitlesPlugin plugin;
    private final File pluginFolder;
    private static final String databaseConnectionConfigPath = "databaseConfig.json";

    private DatabaseConnectionConfiguration databaseConnectionConfiguration;

    private final Gson gson;

    public JsonManager(){
        plugin = TitlesPlugin.getInstance();
        pluginFolder = plugin.getDataFolder();
        gson = new Gson();
        reloadJsonConfigurations();
    }

    public void reloadJsonConfigurations(){
        databaseConnectionConfiguration = loadDatabaseConnectionConfiguration();
    }

    public <T> T loadConfiguration(String path, Class<T> clazz){
        T config = null;
        String fileLocation = IoUtils.getPathOfFileInFolder(pluginFolder, path);
        try {
            config = gson.fromJson(Files.newBufferedReader(Paths.get(fileLocation)), clazz);
            if(config==null){
                plugin.getLogger().info("Intializing " + path + "...");
                config = clazz.newInstance();
                gson.toJson(config, new FileWriter(fileLocation));
            }
        } catch (IOException e) {
            plugin.logError(e.getMessage());
        } catch (InstantiationException | IllegalAccessException ignored) {
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
