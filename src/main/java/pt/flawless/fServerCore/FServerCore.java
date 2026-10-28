package pt.flawless.fServerCore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pt.flawless.fServerCore.commands.SetSpawnCommand;
import pt.flawless.fServerCore.commands.SpawnCommand;
import pt.flawless.fServerCore.commands.SpawnVipCommand;
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
        autoMessages = AutoMessages.start(this);
        // as restantes mensagens lêem o config a cada uso; só as automáticas guardam a secção
        Reloads.register(this, () -> {
            reloadConfig();
            if (autoMessages != null) autoMessages.cancel();
            autoMessages = AutoMessages.start(this);
        });

        consoleLogger.sendEnablePluginMessage();
    }

    @Override
    public void onDisable() {
        consoleLogger.sendDisablePluginMessage();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerSpawnListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ServerListPingListener(this), this);
        Bukkit.getPluginManager().registerEvents(new HungerListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        Bukkit.getPluginManager().registerEvents(new NoPermissionListener(), this);
    }

    public static Plugin getMainPlugin() {
        return plugin;
    }
}