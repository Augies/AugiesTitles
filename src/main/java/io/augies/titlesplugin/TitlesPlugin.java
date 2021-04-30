package io.augies.titlesplugin;

import io.augies.titlesplugin.config.VaultConfiguration;
import io.augies.titlesplugin.manager.CommandManager;
import io.augies.titlesplugin.manager.io.DatabaseConnectionManager;
import io.augies.titlesplugin.manager.io.JsonManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class TitlesPlugin extends JavaPlugin {
    private static TitlesPlugin instance;

    private DatabaseConnectionManager databaseConnectionManager;

    private CommandManager commandsManager;
    private JsonManager jsonManager;

    @Override
    public void onEnable() {
        instance = this;

        if(!getDataFolder().exists()){
            logInfo("Performing first time plugin setup...");
            setup();
            logError("UNLOADING THE PLUGIN. PLEASE CONFIGURE AND RELOAD THE PLUGIN");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if(!VaultConfiguration.initializeVault()){
            logError("Vault is not loaded! Features of this plugin likely won't work!");
        }
        logInfo("Loading Commands Manager");
        this.commandsManager = new CommandManager();
        logInfo("Loading Json Manager");
        this.jsonManager = new JsonManager();
        logInfo("Loading Database Connection Manager");
        this.databaseConnectionManager = new DatabaseConnectionManager();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        instance = null;
        this.commandsManager = null;
        this.jsonManager = null;
        this.databaseConnectionManager = null;
        // Plugin shutdown logic
    }

    void setup(){
        logInfo("Creating json files...");
        new JsonManager();
    }

    public void logError(String error){
        getLogger().severe(error);
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

    public DatabaseConnectionManager getDatabaseConnectionManager() {
        return databaseConnectionManager;
    }
}
