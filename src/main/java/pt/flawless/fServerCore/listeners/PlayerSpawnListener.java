package pt.flawless.fServerCore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;
import pt.flawless.fapi.enums.EMessageTypeEnum;
import pt.flawless.fapi.logs.FConsoleLogger;

// TODO: Move this file to centralized place
public class PlayerSpawnListener implements Listener {
    @EventHandler
    public void onPlayerSpawn(PlayerSpawnLocationEvent e) {
        e.setSpawnLocation(new Location(Bukkit.getServer().getWorld("world"), 137.517, 70, -46.536));
    }
}
