package org.hexa.pigracehexa.Manager;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class FinishManager {

    private final Map<Player, Integer> finishPositions = new HashMap<>();
    private int currentPosition = 1;

    public int recordFinish(Player player) {
        if (!finishPositions.containsKey(player)) {
            finishPositions.put(player, currentPosition);
            return currentPosition++;
        }
        return finishPositions.get(player);
    }
}
