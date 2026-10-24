package pt.flawless.fServerCore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import io.papermc.paper.event.player.AsyncPlayerSpawnLocationEvent;

// TODO: Move this file to centralized place
public class PlayerSpawnListener implements Listener {
    private final Plugin plugin;

    public PlayerSpawnListener(Plugin plugin) {
        this.plugin = plugin;
    }

    /** Spawn configurado, ou null se o mundo não existe. */
    private Location spawn() {
        FileConfiguration c = plugin.getConfig();
        if (Bukkit.getWorld(c.getString("spawn.world")) == null) return null;
        return new Location(Bukkit.getWorld(c.getString("spawn.world")),
                c.getDouble("spawn.x"), c.getDouble("spawn.y"), c.getDouble("spawn.z"),
                (float) c.getDouble("spawn.yaw"), (float) c.getDouble("spawn.pitch"));
    }

    @EventHandler
    public void onPlayerSpawn(AsyncPlayerSpawnLocationEvent e) {
        // Mundo em falta: mantém o spawn que o servidor escolheu.
        Location l = spawn();
        if (l != null) e.setSpawnLocation(l);
    }

    // HIGHEST para ganhar ao Multiverse; a cama continua a valer.
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawn(PlayerRespawnEvent e) {
        Location l = spawn();
        if (l != null && !e.isBedSpawn()) e.setRespawnLocation(l);
    }
}
