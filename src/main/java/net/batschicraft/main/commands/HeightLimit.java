package net.batschicraft.main.commands;

import net.batschicraft.main.HeightLimiter;
import net.batschicraft.main.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HeightLimit implements CommandExecutor {
    Config heightLimit;
    HeightLimiter instance;

    public HeightLimit(Config heightLimit, HeightLimiter instance) {
        this.heightLimit = heightLimit;
        this.instance = instance;
    }

    Double settedhight;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        settedhight = heightLimit.getHeightLimiter().getDouble(heightLimit.getHeightLimitValue());
        if (sender.hasPermission("height.read")) {
            sender.sendMessage(HeightLimiter.getPrefix() + ChatColor.GREEN + ChatColor.BOLD + "The setted Heightlimit is: " + ChatColor.DARK_RED + ChatColor.BOLD + settedhight);
        } else if (!sender.hasPermission("height.read")) {
            sender.sendMessage(HeightLimiter.getPrefix() + ChatColor.DARK_RED + ChatColor.BOLD + "You don't have the permission to read out the setted maximum height");
        }
        return false;
    }
}
