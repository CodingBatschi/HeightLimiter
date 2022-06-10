package net.batschicraft.main;


import net.batschicraft.main.listeners.MoveListener;
import net.batschicraft.main.commands.HeightLimit;
import net.batschicraft.main.commands.SetCommand;
import net.batschicraft.main.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class HeightLimiter extends JavaPlugin {

    @Override
    public void onEnable() {
        Config config = new Config(this);
        Bukkit.getLogger().fine("HeightLimiter is enabled");
        listenerRegistration(config);
        commandRegistration(config);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().fine("HeightBorder is disabled");
    }

    public static String getPrefix() {
        return ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "HeightBorder" + ChatColor.DARK_GRAY + "] ";
    }

    private void listenerRegistration(Config config) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new MoveListener(config, this), this);

    }

    private void commandRegistration(Config config) {
        getCommand("hbset").setExecutor(new SetCommand(config, this));
        getCommand("heightlimit").setExecutor(new HeightLimit(config, this));
    }
}

