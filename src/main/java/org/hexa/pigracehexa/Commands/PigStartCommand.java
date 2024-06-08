package org.hexa.pigracehexa.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hexa.pigracehexa.Manager.PigManager;

public class PigStartCommand implements CommandExecutor {

    private final PigManager pigManager;

    public PigStartCommand(PigManager pigManager) {
        this.pigManager = pigManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE) {
                    player.setGameMode(GameMode.ADVENTURE);
                    pigManager.startRidingPig(player);
                }
            }
            sender.sendMessage(ChatColor.GREEN + "Todos los jugadores en modo supervivencia y aventura están ahora montando un cerdo.");
        } else {
            for (String playerName : args) {
                Player player = Bukkit.getPlayer(playerName);
                if (player != null) {
                    player.setGameMode(GameMode.ADVENTURE);
                    pigManager.startRidingPig(player);
                } else {
                    sender.sendMessage(ChatColor.RED + "El jugador " + playerName + " no está en línea.");
                }
            }
        }
        return true;
    }
}
