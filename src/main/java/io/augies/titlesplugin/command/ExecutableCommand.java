package io.augies.titlesplugin.command;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;

public interface ExecutableCommand {
    /**
     * Method for setting everything in the command up. Such as:
     * Registering sub-commands, permissions, whether the command can be run by players, etc.
     */
    void setup();

    default boolean canExecute(Player player, String label, List<String> args){
        return true;
    }

    boolean execute(Player player, String label, List<String> args);

    default Optional<List<String>> tabComplete(Player user, String alias, List<String> args){
        return Optional.empty();
    }
}
