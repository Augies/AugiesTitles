package io.augies.titlesplugin.command;

import io.augies.titlesplugin.TitlesPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.PluginIdentifiableCommand;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class TitlesCommand extends Command implements PluginIdentifiableCommand, ExecutableCommand {
    private final TitlesPlugin plugin;
    private boolean playerOnly = false;
    private boolean hidden = false;
    protected final TitlesCommand parent;
    private String permission = "";
    private final int commandLevel;
    private final Map<String,TitlesCommand> childCommands;
    private final Map<String, TitlesCommand> childAliases;
    private final String usage;
    private final String topLabel;
    private final String label;

    /**
     * Top-level command
     * @param description the description of the command
     * @param usage the String describing the command's usage
     * @param label the label for the command
     * @param permission the permission required for command execution
     * @param aliases the aliases for the command
     */
    protected TitlesCommand(String description, String usage, String label, String permission, List<String> aliases){
        super(label, description, usage, aliases);
        plugin = TitlesPlugin.getInstance();
        this.commandLevel = 0;
        this.childAliases = new LinkedHashMap<>();
        this.childCommands = new LinkedHashMap<>();
        this.parent = null;
        this.usage = usage;
        this.permission = permission;
        this.label = label;
        this.topLabel = label;
        if (plugin.getCommand(label) == null) {
            plugin.getCommandsManager().registerCommand(this);
        }
        setup();
    }

    /**
     * Command with a parent command
     * @param parent the parent command
     * @param description the description of the command
     * @param usage the string describing the command's usage
     * @param label the label for the command
     * @param permission the permission required to execute the command
     * @param aliases the aliases for the command
     */
    protected TitlesCommand(TitlesCommand parent, String description, String usage, String label, String permission, List<String> aliases){
        super(label, description, usage, aliases);
        plugin = TitlesPlugin.getInstance();
        this.childAliases = new LinkedHashMap<>();
        this.childCommands = new LinkedHashMap<>();
        this.parent = parent;
        this.usage = usage;
        this.permission = permission;
        this.label = label;
        this.topLabel = parent.getTopLabel() == null ? parent.label : parent.getTopLabel();
        this.commandLevel = parent.getCommandLevel() + 1;
        parent.addChildCommand(this);

        setup();
    }

    public void addChildCommand(TitlesCommand command){
        this.childCommands.put(command.label, command);
        for(String alias : command.getAliases()){
            this.childAliases.put(alias, command);
        }
    }

    public void setPlayerOnly(boolean playerOnly){
        this.playerOnly = playerOnly;
    }

    public void setHidden(boolean hidden){
        this.hidden = hidden;
    }

    public boolean isPlayerOnly() {
        return playerOnly;
    }

    public boolean isHidden() {
        return hidden;
    }

    public TitlesCommand getParent() {
        return parent;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    public Map<String, TitlesCommand> getChildCommands() {
        return childCommands;
    }

    public Map<String, TitlesCommand> getChildAliases() {
        return childAliases;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public String getTopLabel(){
        return this.topLabel;
    }

    public int getCommandLevel(){
        return this.commandLevel;
    }
}
