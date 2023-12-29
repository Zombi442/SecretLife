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
package me.zombi42.secretlife.Listeners;

import me.zombi42.secretlife.Util.ConfigManager;
import me.zombi42.secretlife.Enum.TeamEnum;
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
        int i = configManager.getLives(player) - 1;
        configManager.setLives(player, i);
        teamManager.addPlayerToTeam(player, i, true, true);


    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!configManager.livesContainsKey(player)) {
            configManager.setLives(player, 3);
            teamManager.setPlayersTeam(player, TeamEnum.Green);
            return;
        }


        int lives = configManager.getLives(player);

        switch (lives) {
            case 3:
                teamManager.setPlayersTeam(player, TeamEnum.Green);
                break;
            case 2:
                teamManager.setPlayersTeam(player, TeamEnum.Yellow);
                break;
            case 1:
                teamManager.setPlayersTeam(player, TeamEnum.Red);
                break;
            case 0:
                player.setGameMode(GameMode.SPECTATOR);
                teamManager.setPlayersTeam(player, TeamEnum.None);
                break;
        }


    }
}
