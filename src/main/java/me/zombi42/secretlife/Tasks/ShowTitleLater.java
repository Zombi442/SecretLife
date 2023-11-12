/*
SecretLife © 2023 by Zombi42 is licensed under CC BY-NC-SA 4.0.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/
https://github.com/Zombi442
 */
package me.zombi42.secretlife.Tasks;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ShowTitleLater extends BukkitRunnable {


    String messageToDisplay;
    Player player;

    public ShowTitleLater(Player player, String messageToDisplay) {
        this.messageToDisplay = messageToDisplay;
        this.player = player;

    }

    @Override
    public void run() {

            player.sendTitle("", ChatColor.RED + messageToDisplay, 20, 40, 2);
    }
}
