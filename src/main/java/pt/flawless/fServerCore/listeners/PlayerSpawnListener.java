package pt.flawless.fServerCore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

// TODO: Move this file to centralized place
public class PlayerSpawnListener implements Listener {
    private final Plugin plugin;

    public PlayerSpawnListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerSpawn(PlayerSpawnLocationEvent e) {
        FileConfiguration c = plugin.getConfig();
        // Mundo em falta: mantém o spawn que o servidor escolheu.
        if (Bukkit.getWorld(c.getString("spawn.world")) == null) return;
        e.setSpawnLocation(new Location(Bukkit.getWorld(c.getString("spawn.world")),
                c.getDouble("spawn.x"), c.getDouble("spawn.y"), c.getDouble("spawn.z"),
                (float) c.getDouble("spawn.yaw"), (float) c.getDouble("spawn.pitch")));
    }
}
