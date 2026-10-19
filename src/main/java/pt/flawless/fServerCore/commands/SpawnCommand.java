package pt.flawless.fServerCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

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
        FileConfiguration c = plugin.getConfig();
        String world = c.getString("spawn.world");
        if (world == null || Bukkit.getWorld(world) == null) {
            sender.sendMessage(ChatColor.RED + "O spawn não está disponível.");
            return true;
        }
        Player p = (Player) sender;
        p.teleport(new Location(Bukkit.getWorld(world),
                c.getDouble("spawn.x"), c.getDouble("spawn.y"), c.getDouble("spawn.z"),
                (float) c.getDouble("spawn.yaw"), (float) c.getDouble("spawn.pitch")));
        p.sendMessage(ChatColor.GREEN + "Foste teleportado para o spawn.");
        return true;
    }
}
