/*
This file is part of SecretLife.
https://github.com/Zombi442

SecretLife is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

SecretLife is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with SecretLife.
If not, see <https://www.gnu.org/licenses/>.
 */
package me.zombi42.secretlife.Commands;

import me.zombi42.secretlife.SecretLife;
import me.zombi42.secretlife.Util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandGift implements CommandExecutor {


    SecretLife plugin;
    ConfigManager configManager;

    public CommandGift(SecretLife plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;

        plugin.getCommand("gift").setExecutor(this);

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = null;
        Boolean isPlayer = false;
        if (sender instanceof Player) {
            player = (Player) sender;
            isPlayer = true;
        }
        if(configManager.getGift(player) != null) {
            if (configManager.getGift(player)) {
                player.sendMessage(ChatColor.RED + "You've already given your heart this session.");
                return true;
            }
        }
        if(args.length == 0){
            player.sendMessage(ChatColor.RED + "Please input the name of the player you want to give a heart to.");
            return true;
        }
        if(Bukkit.getPlayer(args[0]) == null){
            player.sendMessage(ChatColor.RED + "Could not find a player named " + args[0]);
            return true;
        }
        Player giftedPlayer = Bukkit.getPlayer(args[0]);
        //fixme: Before release
//        if(player.equals(giftedPlayer)){
//            player.sendMessage(ChatColor.RED + "You cannot gift a heart to yourself.");
//            return true;
//        }


        AttributeInstance giftedPlayerMaxHealth = giftedPlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH);

        if(giftedPlayerMaxHealth.getBaseValue() >= 60){
            player.sendMessage(ChatColor.RED + "You cannot give a heart to a player with full health");
            return true;
        }
        if(giftedPlayerMaxHealth.getBaseValue() == 59){
            giftedPlayerMaxHealth.setBaseValue(giftedPlayerMaxHealth.getBaseValue() + 1);
            giftedPlayer.setHealth(giftedPlayer.getHealth() + 1);
        }else {
            giftedPlayerMaxHealth.setBaseValue(giftedPlayerMaxHealth.getBaseValue() + 2);
            giftedPlayer.setHealth(giftedPlayer.getHealth() + 2);
        }

        giftedPlayer.sendMessage(ChatColor.GREEN + player.getName() + " gifted you a heart!");
        configManager.setGift(player, true);
        return true;
    }
}
