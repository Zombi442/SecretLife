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
package me.zombi42.secretlife;

import me.zombi42.secretlife.Commands.*;
import me.zombi42.secretlife.Listeners.ButtonPress;
import me.zombi42.secretlife.Listeners.Damage;
import me.zombi42.secretlife.Listeners.PlayerDeath;
import me.zombi42.secretlife.Util.ConfigManager;
import me.zombi42.secretlife.Util.DropManager;
import me.zombi42.secretlife.Util.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class SecretLife extends JavaPlugin {
    ConfigManager configManager;
    DropManager dropManager;
    TeamManager teamManager;
    public String version = "0.8";

    @Override
    public void onEnable() {

        configManager = new ConfigManager(this);
        dropManager = new DropManager();
        teamManager = new TeamManager(this);

        new CommandDisperseSecrets(this, configManager);
        new CommandSetButton(this, configManager);
        new CommandSetItemdrop(this, configManager);
        new CommandSetLives(this, teamManager ,configManager);
        new CommandSecretLife(this, configManager);
        new CommandGift(this, configManager);

        Bukkit.getServer().getPluginManager().registerEvents(new Damage(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ButtonPress(configManager, dropManager, this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeath(configManager, teamManager), this);

    }

    @Override
    public void onDisable() {
        configManager.saveConfig();
    }
}
