package pt.flawless.fServerCore;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pt.flawless.fServerCore.listeners.PlayerJoinListener;
import pt.flawless.fServerCore.listeners.PlayerQuitListener;
import pt.flawless.fServerCore.listeners.PlayerSpawnListener;

public class Main extends JavaPlugin {
    private Plugin plugin;

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerSpawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }

    @Override
    public void onEnable() {
        registerEvents();
        this.plugin = this;
        ConsoleCommandSender b = Bukkit.getConsoleSender();
        b.sendMessage("§e");
        b.sendMessage("§e[fServerCore] Plugin ativado com sucesso!");
        b.sendMessage("§e");
    }

    @Override
    public void onDisable() {
        ConsoleCommandSender b = Bukkit.getConsoleSender();
        b.sendMessage("§c");
        b.sendMessage("§c[fServerCore] Plugin desativado com sucesso!");
        b.sendMessage("§c");
    }

    public Plugin getPlugin() {
        return this.plugin;
    }
}