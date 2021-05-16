package io.augies.titlesplugin.manager.io.storage;

import io.augies.titlesplugin.TitlesPlugin;
import io.augies.titlesplugin.model.PlayerToken;
import io.augies.titlesplugin.model.Title;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class StorageTypeManager {
    protected final TitlesPlugin plugin;

    protected StorageTypeManager() {
        plugin = TitlesPlugin.getInstance();
    }

    public abstract void onEnable();

    public abstract void onDisable();

    protected abstract void confirmInitialization();

    public abstract List<Title> getAllTitles();

    public abstract List<Title> getTitlesForPlayer(Player player);

    public abstract Title getTitle(String identifier);

    public abstract boolean createTitle(String identifier, String title);

    public abstract boolean createGroupTitle(String group, String title);

    public abstract boolean removeTitle(String identifier);

    public abstract List<PlayerToken> getAllPlayerTokens();

    public abstract int getTokensForPlayer(Player player);

    public abstract boolean setTokensForPlayer(Player player, int amount);

    public boolean addPlayerTokens(Player player, int amount) {
        return setTokensForPlayer(player, getTokensForPlayer(player) + amount);
    }

    public boolean subtractPlayerTokens(Player player, int amount) {
        int tokens = getTokensForPlayer(player);
        if (amount > tokens) {
            plugin.logWarning("Trying to set player tokens for player " + player.getName() + " to negative. Ignoring operation");
            return false;
        }
        return setTokensForPlayer(player, tokens - amount);
    }
}
