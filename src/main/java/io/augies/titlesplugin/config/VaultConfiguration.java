package io.augies.titlesplugin.config;

import io.augies.titlesplugin.TitlesPlugin;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import static org.bukkit.Bukkit.getServer;

public class VaultConfiguration {
    private static Chat chat;
    private static Economy economy;
    private static Permission permission;

    public static boolean initializeVault(){
        if(!isVaultLoaded()){
            return false;
        }
        chat = getServer().getServicesManager().getRegistration(Chat.class).getProvider();
        economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        permission = getServer().getServicesManager().getRegistration(Permission.class).getProvider();
        return true;
    }

    public static boolean isVaultLoaded(){
        return TitlesPlugin.getInstance().getServer().getPluginManager().getPlugin("Vault") == null;
    }

    public static Chat getChat() {
        return chat;
    }

    public static Economy getEconomy() {
        return economy;
    }

    public static Permission getPermission() {
        return permission;
    }
}
