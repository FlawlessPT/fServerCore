package pt.flawless.fServerCore.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemMergeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/** Mostra "nome xQuantidade" por cima dos itens dropados. */
public class DropLabelListener implements Listener {
    private final Plugin plugin;

    public DropLabelListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onSpawn(ItemSpawnEvent e) {
        label(e.getEntity());
    }

    // A quantidade só muda depois do evento, por isso a etiqueta atualiza-se no tick seguinte.
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMerge(ItemMergeEvent e) {
        relabelLater(e.getTarget());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPickup(EntityPickupItemEvent e) {
        relabelLater(e.getItem());
    }

    private void relabelLater(Item item) {
        plugin.getServer().getScheduler().runTask(plugin, () -> {
            if (item.isValid()) label(item);
        });
    }

    static void label(Item item) {
        ItemStack stack = item.getItemStack();
        item.customName(stack.effectiveName().append(Component.text(" x" + stack.getAmount())));
        item.setCustomNameVisible(true);
    }
}
