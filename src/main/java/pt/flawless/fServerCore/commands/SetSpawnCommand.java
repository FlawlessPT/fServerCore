package pt.flawless.fServerCore.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pt.flawless.fServerCore.config.Spawns;

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
        boolean vip = args.length == 1 && args[0].equalsIgnoreCase("vip");
        if (args.length > 1 || (args.length == 1 && !vip)) {
            sender.sendMessage(ChatColor.RED + "Uso: /setspawn [vip]");
            return true;
        }
        Player p = (Player) sender;
        Location l = p.getLocation();
        Spawns.save(plugin, vip, l);
        if (vip) {
            p.sendMessage(ChatColor.GREEN + "Spawn VIP definido para a tua posição.");
            return true;
        }
        // Também define o ponto de renascimento do mundo.
        l.getWorld().setSpawnLocation(l.getBlockX(), l.getBlockY(), l.getBlockZ());
        p.sendMessage(ChatColor.GREEN + "Spawn definido para a tua posição.");
        return true;
    }
}
