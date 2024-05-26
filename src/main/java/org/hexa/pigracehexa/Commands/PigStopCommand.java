package org.hexa.pigracehexa.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.hexa.pigracehexa.Manager.PigManager;

public class PigStopCommand implements CommandExecutor {

    private final PigManager pigManager;

    public PigStopCommand(PigManager pigManager) {
        this.pigManager = pigManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        pigManager.stopRidingPigs();
        return true;
    }
}
