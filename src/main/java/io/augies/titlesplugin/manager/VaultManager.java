package io.augies.titlesplugin.manager;

import io.augies.titlesplugin.TitlesPlugin;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

/**
 * Loosely based off of example given on the github
 * repo for VaultAPI
 */
public class VaultManager {
    private static Chat chat;
    private static Economy economy;
    private static Permission permission;

    public static boolean initializeVault(){
        if(!isVaultLoaded()){
            return false;
        }
        return setupEconomy() || setupChat() || setupPermissions();
    }

    public static boolean isVaultLoaded(){
        return TitlesPlugin.getInstance().getServer().getPluginManager().getPlugin("Vault") != null;
    }

    private static boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    private static boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private static boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        permission = rsp.getProvider();
        return permission != null;
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
