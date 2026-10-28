package pt.flawless.fServerCore.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class Spawns {
    public static final String VIP_PERMISSION = "fservercore.vip";

    private Spawns() {}

    /** Spawn guardado na secção (`spawn` ou `spawn-vip`), ou null se não existe ou o mundo falta. */
    public static Location get(Plugin plugin, boolean vip) {
        ConfigurationSection c = plugin.getConfig().getConfigurationSection(vip ? "spawn-vip" : "spawn");
        if (c == null || c.getString("world") == null || Bukkit.getWorld(c.getString("world")) == null) return null;
        return new Location(Bukkit.getWorld(c.getString("world")),
                c.getDouble("x"), c.getDouble("y"), c.getDouble("z"),
                (float) c.getDouble("yaw"), (float) c.getDouble("pitch"));
    }

    /** Spawn do jogador: o VIP se for VIP e estiver definido, senão o normal. */
    public static Location forPlayer(Plugin plugin, Player p) {
        Location l = p.hasPermission(VIP_PERMISSION) ? get(plugin, true) : null;
        return l != null ? l : get(plugin, false);
    }

    public static void save(Plugin plugin, boolean vip, Location l) {
        String s = vip ? "spawn-vip" : "spawn";
        plugin.getConfig().set(s + ".world", l.getWorld().getName());
        plugin.getConfig().set(s + ".x", l.getX());
        plugin.getConfig().set(s + ".y", l.getY());
        plugin.getConfig().set(s + ".z", l.getZ());
        plugin.getConfig().set(s + ".yaw", (double) l.getYaw());
        plugin.getConfig().set(s + ".pitch", (double) l.getPitch());
        plugin.saveConfig();
    }
}
