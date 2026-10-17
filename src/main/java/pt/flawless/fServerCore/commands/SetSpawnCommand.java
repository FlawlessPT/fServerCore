package pt.flawless.fServerCore.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SetSpawnCommand implements CommandExecutor {
    private final Plugin plugin;

    public SetSpawnCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Só jogadores podem usar este comando.");
            return true;
        }
        if (args.length != 0) {
            sender.sendMessage(ChatColor.RED + "Uso: /setspawn");
            return true;
        }
        Player p = (Player) sender;
        Location l = p.getLocation();
        plugin.getConfig().set("spawn.world", l.getWorld().getName());
        plugin.getConfig().set("spawn.x", l.getX());
        plugin.getConfig().set("spawn.y", l.getY());
        plugin.getConfig().set("spawn.z", l.getZ());
        plugin.getConfig().set("spawn.yaw", (double) l.getYaw());
        plugin.getConfig().set("spawn.pitch", (double) l.getPitch());
        plugin.saveConfig();
        // Também define o ponto de renascimento do mundo.
        l.getWorld().setSpawnLocation(l.getBlockX(), l.getBlockY(), l.getBlockZ());
        p.sendMessage(ChatColor.GREEN + "Spawn definido para a tua posição.");
        return true;
    }
}
