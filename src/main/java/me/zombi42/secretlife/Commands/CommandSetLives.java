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
import me.zombi42.secretlife.Util.TeamManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandSetLives implements CommandExecutor {


    SecretLife secretLife;
    TeamManager teamManager;
    ConfigManager configManager;

    public CommandSetLives(SecretLife secretLife, TeamManager teamManager, ConfigManager configManager) {
        this.secretLife = secretLife;
        this.teamManager = teamManager;
        this.configManager = configManager;

        secretLife.getCommand("setlives").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if (args.length == 0) {
            return true;
        }


        Player player = (Player) sender;

        configManager.setLives(player, Integer.parseInt(args[0]));

        return true;
    }
}
