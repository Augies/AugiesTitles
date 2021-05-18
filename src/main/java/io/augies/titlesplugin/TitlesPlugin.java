package io.augies.titlesplugin;

import io.augies.titlesplugin.manager.CommandManager;
import io.augies.titlesplugin.manager.VaultManager;
import io.augies.titlesplugin.manager.io.JsonManager;
import io.augies.titlesplugin.manager.io.storage.StorageManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class TitlesPlugin extends JavaPlugin {
    private static TitlesPlugin instance;

    private CommandManager commandsManager;
    private JsonManager jsonManager;
    private StorageManager storageManager;

    @Override
    public void onEnable() {
        instance = this;

        if(!getDataFolder().exists()){
            logInfo("Performing first time plugin setup...");
            setup();
        }

        if(!VaultManager.initializeVault()){
            logError("Vault is not loaded! Features of this plugin likely won't work!");
        }
        logInfo("Loading Commands Manager");
        commandsManager = new CommandManager();
        logInfo("Loading Json Manager");
        jsonManager = new JsonManager();
        logInfo("Loading Storage Manager");
        storageManager = new StorageManager();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        storageManager.onDisable();
        instance = null;
        commandsManager = null;
        jsonManager = null;
        storageManager = null;
        // Plugin shutdown logic
    }

    void setup(){
        logInfo("Creating json files...");
        new JsonManager();
    }

    public void logError(String error){
        getLogger().severe(error);
    }

    public void logError(Throwable error){
        getLogger().severe(error.getMessage());
    }

    public void logWarning(String warning){
        getLogger().warning(warning);
    }

    public void logInfo(String info){
        getLogger().info(info);
    }

    public static TitlesPlugin getInstance(){
        return instance;
    }

    public PluginCommand getCommand(String name) {
        PluginCommand command = getServer().getPluginCommand(name);

        if (command != null && command.getPlugin() == this) {
            return command;
        } else {
            return null;
        }
    }

    public CommandManager getCommandsManager() {
        return commandsManager;
    }

    public JsonManager getJsonManager() {
        return jsonManager;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }
}
