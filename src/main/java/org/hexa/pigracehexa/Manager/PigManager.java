package org.hexa.pigracehexa.Manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PigManager {

    private final Map<UUID, Pig> playerPigs = new HashMap<>();
    private final Map<UUID, Location> playerCheckpoints = new HashMap<>();
    private final Map<UUID, Integer> taskIDs = new HashMap<>();
    private final JavaPlugin plugin;

    public PigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void startRidingPig(Player player) {
        Location location = player.getLocation();
        Pig pig = (Pig) player.getWorld().spawnEntity(location, EntityType.PIG);
        pig.setSaddle(true);
        pig.addPassenger(player);
        playerPigs.put(player.getUniqueId(), pig);

        ItemStack itemStack = new ItemStack(Material.CARROT_ON_A_STICK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItemInMainHand(itemStack);

        int taskId = new BukkitRunnable() {
            @Override
            public void run() {
                if (player.isOnline() && player.getVehicle() instanceof Pig) {
                    player.sendActionBar("§e§lPresiona §a§lSHIFT §e§lpara regresar al checkpoint");
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L).getTaskId();

        taskIDs.put(player.getUniqueId(), taskId);
    }

    public void stopRidingPigs() {
        for (UUID playerId : playerPigs.keySet()) {
            Player player = Bukkit.getPlayer(playerId);
            if (player != null && player.getVehicle() instanceof Pig) {
                player.getVehicle().remove();
            }
            Bukkit.getScheduler().cancelTask(taskIDs.get(playerId));
        }
        playerPigs.clear();
        taskIDs.clear();
    }

    public void despawnAllPigs() {
        for (Pig pig : playerPigs.values()) {
            pig.remove();
        }
        for (int taskId : taskIDs.values()) {
            Bukkit.getScheduler().cancelTask(taskId);
        }
        playerPigs.clear();
        taskIDs.clear();
    }

    public void despawnPlayerPig(Player player) {
        UUID playerId = player.getUniqueId();
        Pig pig = playerPigs.remove(playerId);
        if (pig != null) {
            pig.remove();
        }
        Bukkit.getScheduler().cancelTask(taskIDs.remove(playerId));
    }

    public void respawnPigAtCheckpoint(Player player) {
        UUID playerId = player.getUniqueId();
        Pig pig = playerPigs.remove(playerId);
        if (pig != null) {
            pig.remove();
        }
        Bukkit.getScheduler().cancelTask(taskIDs.remove(playerId));

        Location checkpoint = playerCheckpoints.get(playerId);
        if (checkpoint != null) {
            player.teleport(checkpoint);
            Bukkit.getScheduler().runTaskLater(plugin, () -> startRidingPig(player), 1L);
        }
        if(checkpoint==null){
            Bukkit.getScheduler().runTaskLater(plugin, () -> startRidingPig(player), 1L);
        }
    }

    public void updatePlayerCheckpoint(Player player, Location checkpoint) {
        if (checkpoint != null) {
            playerCheckpoints.put(player.getUniqueId(), checkpoint);
        }
    }
}
