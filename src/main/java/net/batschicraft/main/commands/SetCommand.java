package net.batschicraft.main.commands;

import net.batschicraft.main.HeightLimiter;
import net.batschicraft.main.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCommand implements CommandExecutor {
    Config heightLimit;
    HeightLimiter instance;

    public SetCommand(Config heightLimit, HeightLimiter instance) {
        this.heightLimit = heightLimit;
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("height.set")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (command.getName().equals("hbset")) {
                    if (args.length == 1) {
                        String zahl = args[0];
                        boolean isZahl = zahl.matches("[+-]?\\d*(\\.\\d+)?");
                        if (isZahl) {
                            heightLimit.setHeightLimitValue(args[0]);
                            player.sendMessage(HeightLimiter.getPrefix() + ChatColor.DARK_RED + ChatColor.BOLD + "New maximum height set:" + ChatColor.DARK_GRAY + ChatColor.BOLD + args[0]);
                        } else {
                            player.sendMessage("§4§lplease enter a valid number");
                        }
                    }
                    if (args.length == 0) {
                        player.sendMessage("§4§lPlease enter a valid number");
                    }
                }
            } else if (command.getName().equals("hbset")) {
                if (args.length == 1) {
                    String zahl = args[0];
                    boolean isZahl = zahl.matches("[+-]?\\d*(\\.\\d+)?");
                    if (isZahl) {
                        heightLimit.setHeightLimitValue(args[0]);
                        Bukkit.getLogger().info("You have set a new height: " + args[0]);
                    }
                } else {
                    Bukkit.getLogger().info("you have to enter a valid number");
                }
            }

        } else if (!sender.hasPermission("height.set"))
            sender.sendMessage("§4§lYou do not have the permission to perform this command");
        return true;
    }
}






