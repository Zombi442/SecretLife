package me.zombi42.secretlife.Util;

import me.zombi42.secretlife.SecretLife;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.data.type.Switch;
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
