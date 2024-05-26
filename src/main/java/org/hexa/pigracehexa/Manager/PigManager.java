package org.hexa.pigracehexa.Manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.hexa.pigracehexa.PigRaceHEXA;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PigManager {

    private final Map<UUID, Pig> playerPigs = new HashMap<>();
    private final Map<UUID, Location> playerCheckpoints = new HashMap<>();
    private final PigRaceHEXA plugin;

    public PigManager(PigRaceHEXA plugin) {
        this.plugin = plugin;
    }

    public void startRidingPig(Player player) {
        Location location = player.getLocation();
        Pig pig = (Pig) player.getWorld().spawnEntity(location, EntityType.PIG);
        pig.setSaddle(true);
        pig.addPassenger(player);
        player.getInventory().addItem(new ItemStack(Material.CARROT_ON_A_STICK));
        playerPigs.put(player.getUniqueId(), pig);
    }

    public void stopRidingPigs() {
        for (UUID playerId : playerPigs.keySet()) {
            Player player = Bukkit.getPlayer(playerId);
            if (player != null) {
                player.getVehicle().remove();
            }
        }
        playerPigs.clear();
    }

    public void despawnAllPigs() {
        for (Pig pig : playerPigs.values()) {
            pig.remove();
        }
        playerPigs.clear();
    }

    public void despawnPlayerPig(Player player) {
        UUID playerId = player.getUniqueId();
        Pig pig = playerPigs.remove(playerId);
        if (pig != null) {
            pig.remove();
        }
    }

    public void respawnPigAtCheckpoint(Player player) {
        UUID playerId = player.getUniqueId();
        Pig pig = playerPigs.remove(playerId);
        if (pig != null) {
            pig.remove();
        }

        Location checkpoint = playerCheckpoints.get(playerId);
        if (checkpoint != null) {
            player.teleport(checkpoint);
            Bukkit.getScheduler().runTaskLater(plugin, () -> startRidingPig(player), 1L);
        }
    }

    public void updatePlayerCheckpoint(Player player, Location checkpoint) {
        if (checkpoint != null) {
            playerCheckpoints.put(player.getUniqueId(), checkpoint);
        }
    }
}

