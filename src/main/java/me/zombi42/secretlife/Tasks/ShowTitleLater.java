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
