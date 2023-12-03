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

import me.zombi42.secretlife.Enum.TeamEnum;
import me.zombi42.secretlife.SecretLife;
import me.zombi42.secretlife.Util.ConfigManager;
import me.zombi42.secretlife.Util.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandSetLives implements CommandExecutor {


    SecretLife secretLife;
    TeamManager teamManager;
    ConfigManager configManager;

    public CommandSetLives(SecretLife secretLife, TeamManager teamManager, ConfigManager configManager) {
        this.secretLife = secretLife;
        this.teamManager = teamManager;
        this.configManager = configManager;

        secretLife.getCommand("setlives").setExecutor(this);
        secretLife.getCommand("setlives").setTabCompleter(new TabCompleter() {
            @Nullable
            @Override
            public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
                List<String> list = new ArrayList<>();

                if (args.length == 1) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        list.add(player.getName());
                    }
                } else if (args.length == 2) {
                    list.add("1");
                    list.add("2");
                    list.add("3");
                }
                return list;
            }
        });
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if (args.length == 0) {
            return true;
        }


        Player player = (Player) sender;


        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(ChatColor.RED + "/setlives [PlayerName] [1-3]");
            return true;
        }


        int lifeValue;
        try {
            lifeValue = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "/setlives [PlayerName] [1-3]");
            return true;
        }

        configManager.setLives(target, lifeValue);
        switch (lifeValue) {
            case 3:
                teamManager.addPlayerToTeam(player, TeamEnum.Green);
                break;
            case 2:
                teamManager.addPlayerToTeam(player, TeamEnum.Yellow);
                break;
            case 1:
                teamManager.addPlayerToTeam(player, TeamEnum.Red);
                break;
            case 0:
                player.setGameMode(GameMode.SPECTATOR);
                teamManager.addPlayerToTeam(player, TeamEnum.None);
                break;
        }
        return true;
    }
}
