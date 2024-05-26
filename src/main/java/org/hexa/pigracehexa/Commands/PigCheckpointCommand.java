package org.hexa.pigracehexa.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hexa.pigracehexa.Manager.CheckpointManager;

public class PigCheckpointCommand implements CommandExecutor {

    private final CheckpointManager checkpointManager;

    public PigCheckpointCommand(CheckpointManager checkpointManager) {
        this.checkpointManager = checkpointManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            checkpointManager.createCheckpoint(player);
            player.sendMessage("Punto de control creado.");
        } else {
            sender.sendMessage("Solo los jugadores pueden crear puntos de control.");
        }
        return true;
    }
}
