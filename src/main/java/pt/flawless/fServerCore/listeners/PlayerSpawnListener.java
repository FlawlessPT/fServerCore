package pt.flawless.fServerCore.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import io.papermc.paper.event.player.AsyncPlayerSpawnLocationEvent;
import pt.flawless.fServerCore.config.Spawns;

// TODO: Move this file to centralized place
public class PlayerSpawnListener implements Listener {
    private final Plugin plugin;

    public PlayerSpawnListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerSpawn(AsyncPlayerSpawnLocationEvent e) {
        // Mundo em falta: mantém o spawn que o servidor escolheu.
        Location l = Spawns.get(plugin, false);
        if (l != null) e.setSpawnLocation(l);
    }

    // O evento de spawn é assíncrono e sem jogador: os VIP vão para o spawn VIP logo após a entrada.
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPermission(Spawns.VIP_PERMISSION)) return;
        Location l = Spawns.get(plugin, true);
        if (l != null) e.getPlayer().teleport(l);
    }

    // HIGHEST para ganhar ao Multiverse; a cama continua a valer.
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawn(PlayerRespawnEvent e) {
        Location l = Spawns.forPlayer(plugin, e.getPlayer());
        if (l != null && !e.isBedSpawn()) e.setRespawnLocation(l);
    }
}
