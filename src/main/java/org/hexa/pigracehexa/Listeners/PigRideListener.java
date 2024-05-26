package org.hexa.pigracehexa.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.hexa.pigracehexa.Manager.CheckpointManager;
import org.hexa.pigracehexa.Manager.PigManager;

public class PigRideListener implements Listener {

    private final PigManager pigManager;

    public PigRideListener(PigManager pigManager) {
        this.pigManager = pigManager;
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        if (player.getVehicle() instanceof Pig) {
            pigManager.respawnPigAtCheckpoint(player);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity() instanceof Pig) {
            Pig pig = (Pig) event.getEntity();
            if (pig.getPassenger() instanceof Player) {
                Player player = (Player) pig.getPassenger();
                pigManager.respawnPigAtCheckpoint(player);
            }
        }
    }
}
