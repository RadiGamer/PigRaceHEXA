package org.hexa.pigracehexa;


import org.bukkit.plugin.java.JavaPlugin;
import org.hexa.pigracehexa.Commands.PigCheckpointCommand;
import org.hexa.pigracehexa.Commands.PigStartCommand;
import org.hexa.pigracehexa.Commands.PigStopCommand;
import org.hexa.pigracehexa.Listeners.CheckpointListener;
import org.hexa.pigracehexa.Listeners.FinishListener;
import org.hexa.pigracehexa.Listeners.PigRideListener;
import org.hexa.pigracehexa.Manager.CheckpointManager;
import org.hexa.pigracehexa.Manager.FinishManager;
import org.hexa.pigracehexa.Manager.PigManager;

public class PigRaceHEXA extends JavaPlugin {

    private PigManager pigManager;
    private CheckpointManager checkpointManager;
    private FinishManager finishManager;

    @Override
    public void onEnable() {
        checkpointManager = new CheckpointManager();
        pigManager = new PigManager(this);
        finishManager = new FinishManager();

        getCommand("pigstart").setExecutor(new PigStartCommand(pigManager));
        getCommand("pigstop").setExecutor(new PigStopCommand(pigManager));
        getCommand("pigcheckpoint").setExecutor(new PigCheckpointCommand(checkpointManager));

        getServer().getPluginManager().registerEvents(new CheckpointListener(checkpointManager, pigManager), this);
        getServer().getPluginManager().registerEvents(new PigRideListener(pigManager), this);
        getServer().getPluginManager().registerEvents(new FinishListener(pigManager,finishManager),this);
    }

    @Override
    public void onDisable() {
        pigManager.despawnAllPigs();
    }
}
