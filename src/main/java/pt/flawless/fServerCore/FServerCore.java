package pt.flawless.fServerCore;

import org.bukkit.Bukkit;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import pt.flawless.fServerCore.config.DropConfirm;
import pt.flawless.fServerCore.listeners.DropConfirmListener;
import pt.flawless.fapi.items.Items;
import pt.flawless.fapi.items.ItemsApi;
import pt.flawless.fapi.items.ItemsUnavailableException;
import pt.flawless.fapi.prefs.Pref;
import pt.flawless.fapi.prefs.PrefOption;
import pt.flawless.fapi.prefs.Prefs;
import pt.flawless.fapi.prefs.PrefsUnavailableException;
import org.bukkit.plugin.java.JavaPlugin;
import pt.flawless.fServerCore.commands.SetSpawnCommand;
import pt.flawless.fServerCore.commands.SpawnCommand;
import pt.flawless.fServerCore.commands.SpawnVipCommand;
import pt.flawless.fServerCore.listeners.DropLabelListener;
import pt.flawless.fServerCore.listeners.HungerListener;
import pt.flawless.fServerCore.listeners.NoPermissionListener;
import pt.flawless.fServerCore.listeners.PlayerDeathListener;
import pt.flawless.fServerCore.listeners.PlayerJoinListener;
import pt.flawless.fServerCore.listeners.PlayerQuitListener;
import pt.flawless.fServerCore.listeners.PlayerSpawnListener;
import pt.flawless.fServerCore.listeners.ServerListPingListener;
import pt.flawless.fapi.logs.FConsoleLogger;
import pt.flawless.fapi.reload.Reloads;
import org.bukkit.scheduler.BukkitTask;

public class FServerCore extends JavaPlugin {
    private static Plugin plugin;
    private BukkitTask autoMessages;
    private DropConfirmListener dropConfirm;
    FConsoleLogger consoleLogger = new FConsoleLogger(this.getName());

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();
        // Acrescenta chaves novas a configs já existentes.
        getConfig().options().copyDefaults(true);
        saveConfig();
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        getCommand("spawn").setExecutor(new SpawnCommand(this));
        getCommand("spawnvip").setExecutor(new SpawnVipCommand(this));
        registerEvents();
        registerDropConfirm();
        autoMessages = AutoMessages.start(this);
        // as restantes mensagens lêem o config a cada uso; só as automáticas guardam a secção
        Reloads.register(this, () -> {
            reloadConfig();
            if (autoMessages != null) autoMessages.cancel();
            autoMessages = AutoMessages.start(this);
            if (dropConfirm != null) dropConfirm.setConfig(DropConfirm.from(getConfig()));
        });

        consoleLogger.sendEnablePluginMessage();
    }

    @Override
    public void onDisable() {
        consoleLogger.sendDisablePluginMessage();
    }

    private void registerDropConfirm() {
        if (Bukkit.getPluginManager().getPlugin("fPrefs") == null) {
            getLogger().info("fPrefs indisponível: confirmação de drop desligada");
            return;
        }
        try {
            var prefs = Prefs.get();
            prefs.register(new Pref("drop-confirm", "§eConfirmar drop", Material.HOPPER,
                    List.of("§7Pede para carregares em Q outra vez", "§7antes de largar um item."),
                    List.of(new PrefOption("especiais", "§aItens especiais"),
                            new PrefOption("todos", "§6Todos os itens"),
                            new PrefOption("nenhum", "§cNenhum")),
                    "especiais"));
            ItemsApi items = null;
            if (Bukkit.getPluginManager().getPlugin("fCustomItems") != null) {
                try {
                    items = Items.get();
                } catch (ItemsUnavailableException ignored) {
                    // sem itens personalizados: só valem os materiais da config
                }
            }
            dropConfirm = new DropConfirmListener(this, prefs, items, DropConfirm.from(getConfig()));
            Bukkit.getPluginManager().registerEvents(dropConfirm, this);
        } catch (PrefsUnavailableException ex) {
            getLogger().info("fPrefs indisponível: confirmação de drop desligada");
        }
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerSpawnListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ServerListPingListener(this), this);
        Bukkit.getPluginManager().registerEvents(new HungerListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        Bukkit.getPluginManager().registerEvents(new NoPermissionListener(), this);
        Bukkit.getPluginManager().registerEvents(new DropLabelListener(this), this);
    }

    public static Plugin getMainPlugin() {
        return plugin;
    }
}