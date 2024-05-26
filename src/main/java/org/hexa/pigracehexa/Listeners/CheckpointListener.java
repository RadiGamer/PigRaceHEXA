package org.hexa.pigracehexa.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.hexa.pigracehexa.Manager.CheckpointManager;
import org.hexa.pigracehexa.Manager.PigManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CheckpointListener implements Listener {

    private final CheckpointManager checkpointManager;
    private final PigManager pigManager;
    private final Map<UUID, Map<UUID, Boolean>> playerCheckpointStatus = new HashMap<>();

    public CheckpointListener(CheckpointManager checkpointManager, PigManager pigManager) {
        this.checkpointManager = checkpointManager;
        this.pigManager = pigManager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();
        Map<UUID, Boolean> checkpoints = playerCheckpointStatus.getOrDefault(playerId, new HashMap<>());

        for (Entity entity : player.getNearbyEntities(1, 5, 10)) {  // Near a thin wall
            if (entity instanceof Interaction && entity.getScoreboardTags().contains("checkpoint") && player.getVehicle() instanceof org.bukkit.entity.Pig) {
                UUID checkpointId = entity.getUniqueId();
                if (!checkpoints.getOrDefault(checkpointId, false)) {
                    Location checkpointLocation = entity.getLocation();
                    checkpointManager.updateCheckpoint(player, checkpointLocation);
                    pigManager.updatePlayerCheckpoint(player, checkpointLocation);
                    player.sendMessage(ChatColor.GOLD + "Haz alcanzado un nuevo punto de control.");
                    checkpoints.put(checkpointId, true);
                }
            }
        }
        playerCheckpointStatus.put(playerId, checkpoints);
    }
}
