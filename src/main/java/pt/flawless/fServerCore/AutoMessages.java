package pt.flawless.fServerCore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/** Envia as mensagens de {@code auto-messages} do config.yml a todos os jogadores, de forma periódica. */
public class AutoMessages extends BukkitRunnable {
    private final ConfigurationSection cfg;
    private int next = 0;

    private AutoMessages(ConfigurationSection cfg) {
        this.cfg = cfg;
    }

    public static void start(Plugin plugin) {
        ConfigurationSection cfg = plugin.getConfig().getConfigurationSection("auto-messages");
        if (cfg == null || !cfg.getBoolean("enabled") || cfg.getStringList("messages").isEmpty()) return;
        long ticks = Math.max(1, cfg.getLong("interval", 300)) * 20L;
        new AutoMessages(cfg).runTaskTimer(plugin, ticks, ticks);
    }

    @Override
    public void run() {
        List<String> messages = cfg.getStringList("messages");
        if (messages.isEmpty()) return;
        int i = cfg.getBoolean("random") ? ThreadLocalRandom.current().nextInt(messages.size())
                : next++ % messages.size();
        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
                cfg.getString("prefix", "") + messages.get(i)));
        Bukkit.broadcastMessage(" ");
    }
}
