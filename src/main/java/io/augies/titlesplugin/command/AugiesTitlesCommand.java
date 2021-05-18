package io.augies.titlesplugin.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class AugiesTitlesCommand extends TitlesCommand {

    public AugiesTitlesCommand() {
        super(
                "The top-level AugiesTitles command",
                "/augiestitles",
                "augiestitles",
                "augiestitles",
                Collections.singletonList("atitles")
        );
        this.setPlayerOnly(true);
    }

    @Override
    public void setup() {
        //Nothing needs done here
    }

    @Override
    public boolean execute(Player player, String label, List<String> args) {
        player.sendMessage("Yo");
        return false;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        return false;
    }
}
