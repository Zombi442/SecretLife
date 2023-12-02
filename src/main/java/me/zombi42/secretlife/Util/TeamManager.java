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
package me.zombi42.secretlife.Util;

import me.zombi42.secretlife.Enum.TeamEnum;
import me.zombi42.secretlife.SecretLife;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class TeamManager {

    SecretLife secretLife;
    Scoreboard board;
    Team yellow;
    Team green;
    Team red;

    public TeamManager(SecretLife secretLife) {

        this.secretLife = secretLife;

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        board = manager.getMainScoreboard();
        if(board.getTeam("Green") == null){
            board.registerNewTeam("Green");
            board.getTeam("Green").setColor(ChatColor.GREEN);
        }
        if(board.getTeam("Yellow") == null){
            board.registerNewTeam("Yellow");
            board.getTeam("Yellow").setColor(ChatColor.YELLOW);
        }
        if(board.getTeam("Red") == null){
            board.registerNewTeam("Red");
            board.getTeam("Red").setColor(ChatColor.RED);
        }

        green = board.getTeam("Green");
        yellow = board.getTeam("Yellow");
        red = board.getTeam("Red");

    }

    public void addPlayerToTeam(Player player, TeamEnum teamEnum){
        switch (teamEnum){
            case Green:
                green.addEntry(player.getName());
                break;
            case Yellow:
                yellow.addEntry(player.getName());
                break;
            case Red:
                red.addEntry(player.getName());
                break;
            case None:
                green.removeEntry(player.getName());
                yellow.removeEntry(player.getName());
                red.removeEntry(player.getName());
        }
    }

}
