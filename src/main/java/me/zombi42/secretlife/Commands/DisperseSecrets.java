/*
SecretLife © 2023 by Zombi42 is licensed under CC BY-NC-SA 4.0.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/
https://github.com/Zombi442
 */
package me.zombi42.secretlife.Commands;

import me.zombi42.secretlife.ConfigManager;
import me.zombi42.secretlife.SecretLife;
import me.zombi42.secretlife.Tasks.GiveBook;
import me.zombi42.secretlife.Tasks.ShowTitleLater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class DisperseSecrets implements CommandExecutor {



    String name;
    SecretLife plugin;
    ConfigManager configManager;
    public DisperseSecrets(SecretLife plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;

        name = "DisperseSecrets";
        plugin.getCommand(name).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        for(Player player1 : Bukkit.getOnlinePlayers()){
           player1.sendTitle("", ChatColor.RED + "Dispersing your secrets shortly..", 10, 100, 20);
            new ShowTitleLater(player1, "3").runTaskLater(plugin, 100);
            new ShowTitleLater(player1, "2").runTaskLater(plugin, 140);
            new ShowTitleLater(player1, "1").runTaskLater(plugin, 180);
            new GiveBook(player1, configManager).runTaskLater(plugin,200);
//            new GiveBook(player1, configManager).runTaskLater(plugin,0);

        }

        return true;
    }

}
