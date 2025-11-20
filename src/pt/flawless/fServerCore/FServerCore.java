package pt.flawless.fServerCore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pt.flawless.fServerCore.listeners.PlayerJoinListener;
import pt.flawless.fServerCore.listeners.PlayerQuitListener;
import pt.flawless.fServerCore.listeners.PlayerSpawnListener;
import pt.flawless.fapi.logs.FConsoleLogger;

public class FServerCore extends JavaPlugin {
    private static Plugin plugin;
    FConsoleLogger consoleLogger = new FConsoleLogger(this.getName());

    @Override
    public void onEnable() {
        plugin = this;

        registerEvents();

        consoleLogger.sendEnablePluginMessage();
    }

    @Override
    public void onDisable() {
        consoleLogger.sendDisablePluginMessage();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerSpawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }

    public static Plugin getMainPlugin() {
        return plugin;
    }
}