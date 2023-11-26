package me.zombi42.secretlife.Commands;

import me.zombi42.secretlife.SecretLife;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class secretLifeCommand implements CommandExecutor {


    SecretLife secretLife;

    public secretLifeCommand(SecretLife secretLife) {
        this.secretLife = secretLife;

        secretLife.getCommand("secretlife").setExecutor(this);
        secretLife.getCommand("secretlife").setTabCompleter(new TabCompleter() {
            @Nullable
            @Override
            public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
                List<String> list = new ArrayList<>();

                if (args.length == 1) {
                    list.add("reload");
                }

                return list;
            }
        });
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player;
        Boolean isPlayer = false;
        if(sender instanceof Player){
            player = (Player) sender;
            isPlayer = true;
        }



        if(args.length <= 0){
            return true;
        }




        return true;
    }
}

