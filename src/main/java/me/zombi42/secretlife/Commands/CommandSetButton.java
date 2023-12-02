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
import me.zombi42.secretlife.Util.Button;
import me.zombi42.secretlife.Enum.ButtonType;
import me.zombi42.secretlife.Util.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandSetButton implements CommandExecutor {

    SecretLife secretLife;
    ConfigManager configManager;

    public CommandSetButton(SecretLife secretLife, ConfigManager configManager) {
        this.configManager = configManager;
        this.secretLife = secretLife;

        secretLife.getCommand("setbutton").setExecutor(this);

        secretLife.getCommand("setbutton").setTabCompleter(new TabCompleter() {
            @Nullable
            @Override
            public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
                List<String> list = new ArrayList<>();

                if (args.length == 1) {
                    list.add("success");
                    list.add("hard");
                    list.add("fail");
                }

                return list;
            }
        });

    }

    void sendUsage(Player player) {
        player.sendMessage(ChatColor.RED + "/setbutton [success/hard/fail]");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        Block block = player.getTargetBlock(null, 5);

        if (block.getType() != Material.STONE_BUTTON) {
            player.sendMessage(ChatColor.RED + "You must be looking at a stone button");
            return true;
        }

        if (args.length == 0) {
            sendUsage(player);
            return true;
        }

        boolean continueC = false;

        String argument0 = args[0].toLowerCase();

        switch (argument0) {
            case "success":
                continueC = true;
            case "hard":
                continueC = true;
            case "fail":
                continueC = true;
        }

        if (!continueC) {
            sendUsage(player);
            return true;
        }

        Location blockLocation = block.getLocation();

        argument0 = Character.toUpperCase(argument0.charAt(0)) + argument0.substring(1);
        ButtonType buttonType = ButtonType.valueOf(argument0);


        if (configManager.getButton(buttonType) != null) {
            player.sendMessage(ChatColor.RED + "This button is no longer registered to " + buttonType);
        }

        Button button = new Button(blockLocation, buttonType);

        configManager.setButton(button);

        player.sendMessage(ChatColor.GREEN + "Registered this button to " + button);

        return true;

    }
}
