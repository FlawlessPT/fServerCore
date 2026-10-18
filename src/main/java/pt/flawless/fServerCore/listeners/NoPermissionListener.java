package pt.flawless.fServerCore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pt.flawless.fapi.messages.Messages;

/** Troca a mensagem padrão do Bukkit ("I'm sorry, but you do not have permission...") pela do fAPI. */
public class NoPermissionListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String label = e.getMessage().substring(1).split(" ")[0];
        Command cmd = ((CraftServer) Bukkit.getServer()).getCommandMap().getCommand(label);
        if (cmd == null) return;
        String perm = cmd.getPermission();
        if (perm != null && !e.getPlayer().hasPermission(perm)) {
            e.setCancelled(true);
            Messages.noPermission(e.getPlayer());
        }
    }
}
