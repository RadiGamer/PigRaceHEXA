package org.hexa.pigracehexa.Listeners;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.hexa.pigracehexa.Manager.FinishManager;
import org.hexa.pigracehexa.Manager.PigManager;

public class FinishListener implements Listener {

    private final PigManager pigManager;
    private final FinishManager finishManager;

    public FinishListener(PigManager pigManager, FinishManager finishManager) {
        this.pigManager = pigManager;
        this.finishManager = finishManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if (block != null && block.getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
            Player player = event.getPlayer();
            if (player.getVehicle() instanceof org.bukkit.entity.Pig) {
                int position = finishManager.recordFinish(player);
                launchFirework(player);
                pigManager.despawnPlayerPig(player);
                Bukkit.broadcastMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN + " ha terminado en " + ChatColor.AQUA + position + ". lugar.");
            }
        }
    }

    private void launchFirework(Player player) {
        Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation().add(0,2,0), EntityType.FIREWORK);
        FireworkMeta meta = firework.getFireworkMeta();
        meta.addEffect(FireworkEffect.builder()
                .with(FireworkEffect.Type.BALL_LARGE)
                .withColor(Color.YELLOW)
                .withFlicker()
                .build());
        meta.setPower(3);
        firework.setFireworkMeta(meta);
        firework.detonate(); // Detonate immediately to show the firework effect
    }
}
