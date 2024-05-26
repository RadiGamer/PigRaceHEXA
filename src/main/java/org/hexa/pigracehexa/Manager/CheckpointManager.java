package org.hexa.pigracehexa.Manager;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CheckpointManager {

    private final Map<UUID, Location> playerCheckpoints = new HashMap<>();

    public void createCheckpoint(Player player) {
        Location location = player.getLocation();
        Interaction interaction = (Interaction) player.getWorld().spawnEntity(location, EntityType.INTERACTION);
        interaction.setInteractionWidth(10);  // Width of the wall
        interaction.setInteractionHeight(5); // Height of the wall
        interaction.addScoreboardTag("checkpoint");
        interaction.setPersistent(true);
    }

    public void updateCheckpoint(Player player, Location location) {
        playerCheckpoints.put(player.getUniqueId(), location);
    }

    public Location getCheckpoint(UUID playerId) {
        return playerCheckpoints.get(playerId);
    }
}
