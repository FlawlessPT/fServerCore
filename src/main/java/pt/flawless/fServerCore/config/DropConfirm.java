package pt.flawless.fServerCore.config;

import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

/** Secção {@code drop-confirm} do config.yml. */
public final class DropConfirm {
    private final long windowMillis;
    private final Set<Material> materials;
    private final String message;

    private DropConfirm(long windowMillis, Set<Material> materials, String message) {
        this.windowMillis = windowMillis;
        this.materials = materials;
        this.message = message;
    }

    public static DropConfirm from(FileConfiguration c) {
        Set<Material> mats = EnumSet.noneOf(Material.class);
        for (String name : c.getStringList("drop-confirm.materials")) {
            Material m = Material.matchMaterial(name);
            if (m == null) {
                Logger.getLogger("fServerCore").warning("drop-confirm.materials: material inválido '" + name + "'");
            } else {
                mats.add(m);
            }
        }
        long seconds = Math.max(1, c.getLong("drop-confirm.window-seconds", 10));
        return new DropConfirm(seconds * 1000, mats, c.getString("drop-confirm.message",
                "&eDropa novamente o item em &7{seconds}s &epara dropar &f{item}&e."));
    }

    public long windowMillis() { return windowMillis; }

    public Set<Material> materials() { return materials; }

    public String message() { return message; }
}
