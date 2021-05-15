package io.augies.titlesplugin.manager.io.storage;

import io.augies.titlesplugin.TitlesPlugin;
import io.augies.titlesplugin.config.StorageConfiguration;
import io.augies.titlesplugin.config.StorageType;
import io.augies.titlesplugin.model.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class StorageManager {
    private final TitlesPlugin plugin;
    private final StorageConfiguration storageConfiguration;
    private StorageTypeManager storageTypeManager;

    public StorageManager(){
        plugin = TitlesPlugin.getInstance();
        storageConfiguration = plugin.getJsonManager().getStorageConfiguration();
        loadStorage();
        onEnable();
    }

    public void onEnable(){
        storageTypeManager.onEnable();
    }

    public void onDisable(){
        storageTypeManager.onDisable();
    }

    private void loadStorage(){
        if(storageConfiguration.storageType == StorageType.LOCAL){
            storageTypeManager = new JsonStorageManager();
        }else if(storageConfiguration.storageType == StorageType.MYSQL){
            storageTypeManager = new MySqlManager();
        }else{
            plugin.logError("Unable to load storage type manager! Unloading Plugin...");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    public List<Title> getAllTitles(){
        return storageTypeManager.getAllTitles();
    }

    public List<Title> getAllTitlesForPlayer(Player player){
        return storageTypeManager.getTitlesForPlayer(player);
    }

    public int getTokensForPlayer(Player player){
        return storageTypeManager.getTokensForPlayer(player);
    }
}
