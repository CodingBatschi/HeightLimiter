package net.batschicraft.main.listeners;

import net.batschicraft.main.HeightLimiter;
import net.batschicraft.main.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;


public class MoveListener implements Listener {
    boolean istThrowed = false;
    boolean bypassing = false;
    boolean getted = true;
    Config heightLimit;
    HeightLimiter instance;
    HashMap<String, Location> locationMab = new HashMap<>();

    public MoveListener(Config heightLimit, HeightLimiter instance) {
        this.heightLimit = heightLimit;
        this.instance = instance;
    }

    Double settedhight;
    String bypassMassege;

    @EventHandler
    public void onThrow(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            if (!player.hasPermission("height.bypass")) {
                if (event.getEntity() instanceof EnderPearl) {
                    settedhight = heightLimit.getHeightLimiter().getDouble(heightLimit.getHeightLimitValue());
                    if (player.getLocation().getY() > settedhight - 32) {
                        if (player.getLocation().getY() < settedhight) {
                            istThrowed = true;
                            Location location = player.getLocation();
                            locationMab.put(player.getUniqueId().toString(), location);
                        }
                    }
                }
            }
        }
    }

        @EventHandler
        public void onMove (PlayerMoveEvent event){
            settedhight = heightLimit.getHeightLimiter().getDouble(heightLimit.getHeightLimitValue());
            Player player = event.getPlayer();
            if (!player.hasPermission("height.bypass")) {
                if (player.getLocation().getY() < settedhight) {
                    if ((player.getLocation().getY() > settedhight - 1 && getted)) {
                        Location location = player.getLocation();
                        locationMab.put(player.getUniqueId().toString(), location);
                        getted = false;
                        if (player.getLocation().getY() > settedhight - 1) {
                            if (player.getLocation().getY() < settedhight) {
                                Location loc = locationMab.get(player.getUniqueId().toString());
                                String wert = String.valueOf(Math.round(Float.parseFloat(String.valueOf(loc.getX()))) + Float.parseFloat(String.valueOf(loc.getZ()))); // loc aus locationsmab kriegen
                                if (wert != Math.round(Float.parseFloat(String.valueOf(player.getLocation().getX()))) + String.valueOf(player.getLocation().getZ()))
                                    getted = true;
                                locationMab.put(player.getUniqueId().toString(), player.getLocation());
                            }
                        }
                    }
                }
            }
        }

        @EventHandler
        public void onThrowed (PlayerMoveEvent event){
            Player player = event.getPlayer();
            if (istThrowed) {
                Location location = player.getLocation();
                locationMab.put(player.getUniqueId().toString(), player.getLocation());
            }
        }

        @EventHandler
        public void onMove2 (PlayerMoveEvent event){
            settedhight = heightLimit.getHeightLimiter().getDouble(heightLimit.getHeightLimitValue());
            Player player = event.getPlayer();
            if (!player.hasPermission("height.bypass")) {
                if (player.getLocation().getY() > settedhight) {
                    bypassMassege = heightLimit.getHeightLimiter().getString(heightLimit.getBypassMassege());
                    player.sendMessage(HeightLimiter.getPrefix() + bypassMassege);
                    Location location = locationMab.get(player.getUniqueId().toString());
                    player.teleport(location);
                    istThrowed = false;
                    player.playSound(player.getLocation(), Sound.ENTITY_CAT_HISS, 0.2f, 1);
                    Location location1 = player.getLocation();
                    location1.getWorld().playEffect(location1, Effect.ENDER_SIGNAL, 175, 1);
                    if (player.getLocation().getY() < settedhight - 1) {
                        getted = true;
                    }
                }
            } else if (player.getLocation().getY() > settedhight) {
                if (player.hasPermission("height.bypass")) ;
                {
                    if (!bypassing)
                        player.sendMessage(HeightLimiter.getPrefix() + ChatColor.GREEN + ChatColor.BOLD + "You are bypassing the setted Heightborder now");
                    bypassing = true;

                }

            }
            if (bypassing && player.getLocation().getY() < settedhight) {
                player.sendMessage(HeightLimiter.getPrefix() + ChatColor.GREEN + ChatColor.BOLD + "You are no longer bypassing the Setted Heightborder");
                bypassing = false;
            }
        }

    }

