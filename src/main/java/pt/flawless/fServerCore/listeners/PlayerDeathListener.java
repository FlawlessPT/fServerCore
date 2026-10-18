package pt.flawless.fServerCore.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

public class PlayerDeathListener implements Listener {
    private final Plugin plugin;

    public PlayerDeathListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        String msg = plugin.getConfig().getString(killer != null ? "kill-message" : "death-message", "");
        if (msg.isEmpty()) {
            e.setDeathMessage(null);
            return;
        }
        msg = msg.replace("{player}", e.getEntity().getName());
        if (killer != null) msg = msg.replace("{killer}", killer.getName());
        e.setDeathMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
}
