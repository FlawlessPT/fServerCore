package pt.flawless.fServerCore.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class PlayerJoinListener implements Listener {
    private final Plugin plugin;

    public PlayerJoinListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        String msg = plugin.getConfig().getString("welcome-message", "");
        if (msg.isEmpty()) return;
        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
                msg.replace("{player}", e.getPlayer().getName())));
    }
}
