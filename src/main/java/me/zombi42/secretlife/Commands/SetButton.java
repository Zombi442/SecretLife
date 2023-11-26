/*
SecretLife © 2023 by Zombi42 is licensed under CC BY-NC-SA 4.0.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/
https://github.com/Zombi442
 */
package me.zombi42.secretlife.Commands;

import me.zombi42.secretlife.Util.Button;
import me.zombi42.secretlife.Util.ButtonType;
import me.zombi42.secretlife.Util.ConfigManager;
import me.zombi42.secretlife.SecretLife;
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

public class SetButton implements CommandExecutor {

    SecretLife secretLife;
    ConfigManager configManager;

    public SetButton(SecretLife secretLife, ConfigManager configManager) {
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

    void sendUsage(Player player){
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

        if(args.length == 0){
            sendUsage(player);
            return true;
        }

        boolean continueC = false;

        String argument0 = args[0].toLowerCase();

        switch (argument0){
            case "success":
                continueC = true;
            case "hard":
                continueC = true;
            case "fail":
                continueC = true;
        }

        if(!continueC){
            sendUsage(player);
            return true;
        }

        Location blockLocation = block.getLocation();

        argument0 = Character.toUpperCase(argument0.charAt(0)) + argument0.substring(1);
        ButtonType buttonType = ButtonType.valueOf(argument0);


        if(configManager.getButton(buttonType) != null){
                configManager.removeLocation(buttonType);
                player.sendMessage(ChatColor.RED + "This button is no longer registered to " + buttonType);
            }

        Button button = new Button(blockLocation, buttonType);

        configManager.saveLocation(button);
        configManager.setButton(button);

        player.sendMessage(ChatColor.GREEN + "Registered this button to " + button);

        return true;

    }
}
