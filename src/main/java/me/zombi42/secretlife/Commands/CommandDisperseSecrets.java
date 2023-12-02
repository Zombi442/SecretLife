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

import me.zombi42.secretlife.Util.ConfigManager;
import me.zombi42.secretlife.SecretLife;
import me.zombi42.secretlife.Tasks.GiveBook;
import me.zombi42.secretlife.Tasks.ShowTitleLater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDisperseSecrets implements CommandExecutor {

    String name;
    SecretLife plugin;
    ConfigManager configManager;
    public CommandDisperseSecrets(SecretLife plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;

        name = "DisperseSecrets";
        plugin.getCommand(name).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        Boolean isPlayer = false;
        if (sender instanceof Player) {
            player = (Player) sender;
            isPlayer = true;
        }

        if(args.length == 0) {
            for(Player player1 : Bukkit.getOnlinePlayers()){
                player1.sendTitle( ChatColor.RED + "Your task is...","", 10, 100, 20);
                new ShowTitleLater(player1, "3", true).runTaskLater(plugin, 100);
                new ShowTitleLater(player1, "2", true).runTaskLater(plugin, 140);
                new ShowTitleLater(player1, "1", true).runTaskLater(plugin, 180);
                //TODO: fix this
//            new GiveBook(player1, configManager).runTaskLater(plugin,200);
                new GiveBook(player1, configManager, false).runTaskLater(plugin,0);

            }

            return true;
        }


        if(Bukkit.getPlayer(args[0]) == null){
            player.sendMessage(ChatColor.RED + "Could not find the player named " + args[0]);
            return true;
        }

        Player player1 = Bukkit.getPlayer(args[0]);
        new GiveBook(player1, configManager, true).run();
        return true;

    }

}
