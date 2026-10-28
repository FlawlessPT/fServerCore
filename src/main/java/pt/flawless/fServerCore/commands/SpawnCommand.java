package pt.flawless.fServerCore.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pt.flawless.fServerCore.config.Spawns;

public class SpawnCommand implements CommandExecutor {
    private final Plugin plugin;

    public SpawnCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Só jogadores podem usar este comando.");
            return true;
        }
        if (args.length != 0) {
            sender.sendMessage(ChatColor.RED + "Uso: /spawn");
            return true;
        }
        Player p = (Player) sender;
        Location l = Spawns.forPlayer(plugin, p);
        if (l == null) {
            sender.sendMessage(ChatColor.RED + "O spawn não está disponível.");
            return true;
        }
        p.teleport(l);
        p.sendMessage(ChatColor.GREEN + "Foste teleportado para o spawn.");
        return true;
    }
}
