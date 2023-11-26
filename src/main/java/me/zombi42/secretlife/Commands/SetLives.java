package me.zombi42.secretlife.Commands;

import me.zombi42.secretlife.SecretLife;
import me.zombi42.secretlife.Util.ConfigManager;
import me.zombi42.secretlife.Util.TeamManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetLives implements CommandExecutor {


    SecretLife secretLife;
    TeamManager teamManager;

    ConfigManager configManager;

    public SetLives(SecretLife secretLife, TeamManager teamManager, ConfigManager configManager) {
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
