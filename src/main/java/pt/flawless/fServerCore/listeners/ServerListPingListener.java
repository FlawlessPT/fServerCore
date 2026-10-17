package pt.flawless.fServerCore.listeners;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.Plugin;

public class ServerListPingListener implements Listener {
    private final Plugin plugin;

    public ServerListPingListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        FileConfiguration c = plugin.getConfig();
        String motd = c.getString("motd.line1", "") + "\n" + c.getString("motd.line2", "");
        e.setMotd(ChatColor.translateAlternateColorCodes('&', motd));
    }
}
