package io.augies.titlesplugin.manager;

import io.augies.titlesplugin.TitlesPlugin;
import io.augies.titlesplugin.command.TitlesCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * General command management and structures based off of BentoBox command design
 * See: https://bentobox.world
 */
public class CommandManager {
    private final Map<String, TitlesCommand> commands = new HashMap<>();
    private SimpleCommandMap commandMap;

    public void registerCommand(TitlesCommand command) {
        commands.put(command.getLabel(), command);
        // Use reflection to obtain the commandMap method in Bukkit's server.
        try{
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            commandMap = (SimpleCommandMap) commandMapField.get(Bukkit.getServer());

            String commandPrefix = "augiestitles";

            if (!commandMap.register(commandPrefix, command)) {
                TitlesPlugin.getInstance().logError("Failed to register command " + commandPrefix + " " + command.getLabel());
            }
        }
        catch(Exception exception){
            Bukkit.getLogger().severe("Bukkit server commandMap method is not there! This means no commands can be registered!");
        }
    }

    public void unregisterCommands() {
        // Use reflection to obtain the knownCommands in the commandMap
        try {
            @SuppressWarnings("unchecked")
            Map<String, Command> knownCommands = (Map<String, Command>) commandMap.getClass().getMethod("getKnownCommands").invoke(commandMap);
            //noinspection SuspiciousMethodCalls
            knownCommands.values().removeIf(commands.values()::contains);
            // Not sure if this is needed, but it clears out all references
            commands.values().forEach(c -> c.unregister(commandMap));
            // Zap everything
            commands.clear();
        } catch(Exception e){
            Bukkit.getLogger().severe("Known commands reflection was not possible, AugiesTitles is now unstable, so restart server!");
        }
    }
}
