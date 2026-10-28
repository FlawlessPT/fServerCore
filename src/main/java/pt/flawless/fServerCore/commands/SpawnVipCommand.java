package pt.flawless.fServerCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pt.flawless.fServerCore.config.Spawns;

public class SpawnVipCommand implements CommandExecutor {
    private final Plugin plugin;

    public SpawnVipCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 1) {
            sender.sendMessage(ChatColor.RED + "Uso: /spawnvip [jogador]");
            return true;
        }
        Player target;
        if (args.length == 1) {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Jogador não encontrado.");
                return true;
            }
        } else if (sender instanceof Player p) {
            target = p;
        } else {
            sender.sendMessage(ChatColor.RED + "Uso: /spawnvip <jogador>");
            return true;
        }
        Location l = Spawns.get(plugin, true);
        if (l == null) {
            sender.sendMessage(ChatColor.RED + "O spawn VIP não está definido.");
            return true;
        }
        target.teleport(l);
        target.sendMessage(ChatColor.GREEN + "Foste teleportado para o spawn VIP.");
        if (target != sender) sender.sendMessage(ChatColor.GREEN + target.getName() + " foi teleportado para o spawn VIP.");
        return true;
    }
}
