package pt.flawless.fServerCore;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pt.flawless.fServerCore.listeners.PlayerJoinListener;
import pt.flawless.fServerCore.listeners.PlayerQuitListener;
import pt.flawless.fServerCore.listeners.PlayerSpawnListener;
import pt.flawless.fapi.logs.FConsoleLogger;

public class Main extends JavaPlugin {
    private static Plugin plugin;

    @Override
    public void onEnable() {
        this.plugin = this;

        registerEvents();

        FConsoleLogger.sendEnablePlugin(plugin.getName());
    }

    @Override
    public void onDisable() {
        FConsoleLogger.sendDisablePlugin(plugin.getName());
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