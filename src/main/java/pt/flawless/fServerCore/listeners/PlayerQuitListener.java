package pt.flawless.fServerCore.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class PlayerQuitListener implements Listener {
    private final Plugin plugin;

    public PlayerQuitListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        String msg = plugin.getConfig().getString("quit-message", "");
        e.setQuitMessage(msg.isEmpty() ? null : ChatColor.translateAlternateColorCodes('&',
                msg.replace("{player}", e.getPlayer().getName())));
    }
}
