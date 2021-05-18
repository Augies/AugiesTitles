package io.augies.titlesplugin.manager.io.storage;

import io.augies.titlesplugin.TitlesPlugin;
import io.augies.titlesplugin.config.StorageConfiguration;
import io.augies.titlesplugin.config.StorageType;
import io.augies.titlesplugin.model.PlayerToken;
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
            storageTypeManager = new MySqlStorageManager();
        }else{
            plugin.logError("Unable to load storage type manager! Unloading Plugin...");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }
    }

    public List<Title> getAllTitles() {
        return storageTypeManager.getAllTitles();
    }

    public List<Title> getAllTitlesForPlayer(Player player) {
        return storageTypeManager.getTitlesForPlayer(player);
    }

    public List<Title> getTitlesForPlayer(Player player) {
        return storageTypeManager.getTitlesForPlayer(player);
    }

    public Title getTitle(String identifier) {
        return storageTypeManager.getTitle(identifier);
    }

    public boolean createTitle(String identifier, String title) {
        return storageTypeManager.createTitle(identifier, title);
    }

    public boolean createGroupTitle(String group, String title) {
        return storageTypeManager.createGroupTitle(group, title);
    }

    public boolean removeTitle(String identifier) {
        return storageTypeManager.removeTitle(identifier);
    }

    public List<PlayerToken> getAllPlayerTokens() {
        return storageTypeManager.getAllPlayerTokens();
    }

    public int getTokensForPlayer(Player player) {
        return storageTypeManager.getTokensForPlayer(player);
    }

    public boolean setTokensForPlayer(Player player, int amount) {
        return storageTypeManager.setTokensForPlayer(player, amount);
    }

    public boolean addPlayerTokens(Player player, int amount) {
        return storageTypeManager.addPlayerTokens(player, amount);
    }

    public boolean subtractPlayerTokens(Player player, int amount) {
        return storageTypeManager.subtractPlayerTokens(player, amount);
    }
}
