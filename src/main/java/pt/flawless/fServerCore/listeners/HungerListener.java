package pt.flawless.fServerCore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerListener implements Listener {
    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        // Só bloqueia descidas; comer continua a funcionar.
        if (e.getFoodLevel() < p.getFoodLevel()) e.setCancelled(true);
    }
}
