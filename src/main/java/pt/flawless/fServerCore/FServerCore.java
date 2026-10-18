package pt.flawless.fServerCore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pt.flawless.fServerCore.commands.SetSpawnCommand;
import pt.flawless.fServerCore.listeners.HungerListener;
import pt.flawless.fServerCore.listeners.NoPermissionListener;
import pt.flawless.fServerCore.listeners.PlayerDeathListener;
import pt.flawless.fServerCore.listeners.PlayerJoinListener;
import pt.flawless.fServerCore.listeners.PlayerQuitListener;
import pt.flawless.fServerCore.listeners.PlayerSpawnListener;
import pt.flawless.fServerCore.listeners.ServerListPingListener;
import pt.flawless.fapi.logs.FConsoleLogger;

public class FServerCore extends JavaPlugin {
    private static Plugin plugin;
    FConsoleLogger consoleLogger = new FConsoleLogger(this.getName());

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();
        // Acrescenta chaves novas a configs já existentes.
        getConfig().options().copyDefaults(true);
        saveConfig();
        getCommand("setspawn").setExecutor(new SetSpawnCommand(this));
        registerEvents();
        AutoMessages.start(this);

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