package io.augies.titlesplugin.manager.io.storage;

import io.augies.titlesplugin.TitlesPlugin;
import io.augies.titlesplugin.model.PlayerToken;
import io.augies.titlesplugin.model.Title;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class StorageTypeManager {
    protected final TitlesPlugin plugin;


    protected StorageTypeManager(){
        plugin = TitlesPlugin.getInstance();
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract List<Title> getAllTitles();

    public abstract List<Title> getTitlesForPlayer(Player player);

    public abstract List<PlayerToken> getAllPlayerTokens();

    public abstract int getTokensForPlayer(Player player);

    public abstract Title getTitle(String identifier);

    protected abstract void confirmInitialization();
}
