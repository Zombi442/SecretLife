package me.zombi42.secretlife.Listeners;

import me.zombi42.secretlife.Util.ConfigManager;
import me.zombi42.secretlife.Util.TeamEnum;
import me.zombi42.secretlife.Util.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerDeath implements Listener {


    ConfigManager configManager;
    TeamManager teamManager;

    public PlayerDeath(ConfigManager configManager, TeamManager teamManager) {
        this.configManager = configManager;
        this.teamManager = teamManager;
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        configManager.setLives(player, configManager.getLives(player) - 1);

        if (configManager.getLives(player) == 2) {
            teamManager.addPlayerToTeam(player, TeamEnum.Yellow);
        } else if (configManager.getLives(player) == 1) {
            teamManager.addPlayerToTeam(player, TeamEnum.Red);
        } else if (configManager.getLives(player) == 0) {
            player.setGameMode(GameMode.SPECTATOR);
            teamManager.addPlayerToTeam(player, TeamEnum.None);
            player.getWorld().spawnEntity(player.getLocation(), EntityType.LIGHTNING);
            for(Player player1 : Bukkit.getOnlinePlayers()){
                player1.playSound(player1.getLocation(), Sound.ENTITY_GENERIC_EXPLODE,1f, 1.2f);
            }
        }


    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!configManager.livesContainsKey(player)) {
            Bukkit.getLogger().info(String.valueOf(configManager.livesContainsKey(player)));
            configManager.setLives(player, 3);
            teamManager.addPlayerToTeam(player, TeamEnum.Green);
            return;
        }

        switch (configManager.getLives(player)) {
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


    }
}