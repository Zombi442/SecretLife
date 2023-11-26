/*
SecretLife © 2023 by Zombi42 is licensed under CC BY-NC-SA 4.0.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/
https://github.com/Zombi442
 */
package me.zombi42.secretlife.Commands;

import me.zombi42.secretlife.Util.ConfigManager;
import me.zombi42.secretlife.SecretLife;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetItemDrop implements CommandExecutor {


    SecretLife secretLife;
    ConfigManager configManager;


    public SetItemDrop(SecretLife secretLife, ConfigManager configManager) {
        this.secretLife = secretLife;
        this.configManager = configManager;


        secretLife.getCommand("setitemdrop").setExecutor(this);

    }


    void sendUsage(Player player){
        player.sendMessage(ChatColor.RED + "/SetItemDrop");
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        Location playerLocation = player.getLocation();
        Double x = floor(playerLocation.getX());
        Double y = floor(playerLocation.getY());
        Double z = floor(playerLocation.getZ());
        playerLocation = new Location(playerLocation.getWorld(), x,y,z);


        configManager.setItemDropLocation(playerLocation);


        player.sendMessage("Set Item Drop Location to: [" +
                playerLocation.getX() + "," + playerLocation.getY() + "," + playerLocation.getZ()+ "]"
        );

        return true;

    }

    Double floor(Double _double){
        if(_double <= 0){
            return Math.ceil(_double) + .5 ;
        }
        return Math.floor(_double) + .5;
    }

}
