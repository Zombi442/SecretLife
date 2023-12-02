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
package me.zombi42.secretlife.Tasks;


import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ShowTitleLater extends BukkitRunnable {


    String messageToDisplay;
    Player player;
    boolean tick;

    public ShowTitleLater(Player player, String messageToDisplay, boolean sound) {
        this.messageToDisplay = messageToDisplay;
        this.player = player;
        this.tick = sound;

    }

    @Override
    public void run() {
            player.sendTitle( ChatColor.RED + messageToDisplay, "", 20, 40, 2);
            if(tick){
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 100, 1);
            }
    }
}
