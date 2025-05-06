package pt.flawless.fServerCore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

// TODO: Move this file to centralized place
public class PlayerSpawnListener implements Listener {
    @EventHandler
    public void onPlayerSpawn(PlayerSpawnLocationEvent e) {
        // set player spawn
        e.setSpawnLocation(new Location(Bukkit.getServer().getWorld("world"), 137.517, 70, -46.536));
    }
}
