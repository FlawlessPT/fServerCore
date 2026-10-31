package pt.flawless.fServerCore.listeners;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pt.flawless.fServerCore.config.DropConfirm;
import pt.flawless.fapi.items.ItemsApi;
import pt.flawless.fapi.prefs.PrefsApi;

/** Pede para carregar em Q outra vez antes de largar itens, conforme a preferência {@code drop-confirm}. */
public class DropConfirmListener implements Listener {
    private record Pending(ItemStack item, long at) {}

    private final PrefsApi prefs;
    private final ItemsApi items; // null sem fCustomItems
    private volatile DropConfirm config;
    private final Map<UUID, Pending> pending = new ConcurrentHashMap<>();

    public DropConfirmListener(Plugin plugin, PrefsApi prefs, ItemsApi itemsOrNull, DropConfirm config) {
        this.prefs = prefs;
        this.items = itemsOrNull;
        this.config = config;
    }

    public void setConfig(DropConfirm config) {
        this.config = config;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        ItemStack stack = e.getItemDrop().getItemStack();
        DropConfirm cfg = config;
        String mode = prefs.get(p.getUniqueId(), "drop-confirm");
        boolean protectedItem = switch (mode) {
            case "todos" -> true;
            case "especiais" -> (items != null && items.idOf(stack).isPresent())
                    || cfg.materials().contains(stack.getType());
            default -> false;
        };
        if (!protectedItem) return;

        Pending prev = pending.get(p.getUniqueId());
        if (prev != null && prev.item().isSimilar(stack)
                && System.currentTimeMillis() - prev.at() <= cfg.windowMillis()) {
            pending.remove(p.getUniqueId());
            return;
        }
        e.setCancelled(true);
        pending.put(p.getUniqueId(), new Pending(stack.asQuantity(1), System.currentTimeMillis()));
        String name = PlainTextComponentSerializer.plainText().serialize(stack.effectiveName());
        p.sendMessage(cfg.message().replace('&', '§')
                .replace("{item}", name)
                .replace("{seconds}", String.valueOf(cfg.windowMillis() / 1000)));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        pending.remove(e.getPlayer().getUniqueId());
    }
}
