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
import me.zombi42.secretlife.Tasks.SpawnParticleLater;
import me.zombi42.secretlife.Util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandSecretLife implements CommandExecutor {


    SecretLife secretLife;
    ConfigManager configManager;

    public CommandSecretLife(SecretLife secretLife, ConfigManager configManager) {
        this.secretLife = secretLife;
        this.configManager = configManager;

        TabCompleter tabCompleter = new TabCompleter() {
            @Nullable
            @Override
            public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
                List<String> list = new ArrayList<>();

                if (args.length == 1) {
                    list.add("save");
                    list.add("reload");
                    list.add("info");
                }

                return list;
            }
        };

        secretLife.getCommand("secretlife").setExecutor(this);
        secretLife.getCommand("secretlife").setTabCompleter(tabCompleter);


    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = null;
        Boolean isPlayer = false;
        if (sender instanceof Player) {
            player = (Player) sender;
            isPlayer = true;
        }


        if (args.length <= 0) {
            return true;
        }
        if (args.length == 1) {
            switch (args[0]) {
                case "save":
                    configManager.saveConfig();
                    player.sendMessage(ChatColor.GREEN + "Saved Config!");
                    break;
                case "reload":
                    if (isPlayer) {
                        player.sendMessage(ChatColor.RED + "Warning, this will overwrite any unsaved data on the server. Type [/secretlife reload confirm], to confirm");
                    } else {
                        Bukkit.getLogger().info("Warning, this will overwrite any unsaved data on the server. Type [/secretlife reload confirm], to confirm");
                    }
                    break;
                case "info":
                    if (isPlayer) {
                        player.sendMessage("SecretLife Plugin");
                        player.sendMessage(ChatColor.GREEN + "Version " + secretLife.version);
                        player.sendMessage(ChatColor.BLUE + "My Github: https://github.com/Zombi442");
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "Zombi42");
                    } else {
                        Bukkit.getLogger().info("SecretLife Plugin V" + secretLife.version + " by Zombi42");
                    }
                case "do":
                    Location location = player.getLocation();
                    location.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, location.getX(), location.getY(), location.getZ(), 6000, .25, 0.25, 0.25, 20);
                    for (int i = 40; i < 100; i = i + 10) {
                        new SpawnParticleLater(location).runTaskLater(secretLife, i);
                    }
            }
        } else if (args.length == 2) {
            if (args[0].equals("reload") & args[1].equals("confirm")) {
                configManager.loadConfig();
                if (isPlayer) {
                    player.sendMessage(ChatColor.GREEN + "Loaded Config!");
                } else {
                    Bukkit.getLogger().info("Loaded Config!");
                }
            }
        }
        return true;
    }


}

