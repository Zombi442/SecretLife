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

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class DropItemLater extends BukkitRunnable {

    World world;
    ItemStack itemStack;
    Location location;
    Player player;

    public DropItemLater(ItemStack itemStack, Location location, Player player) {
        this.world = location.getWorld();
        this.itemStack = itemStack;
        this.location = location;
        this.player = player;
    }

    @Override
    public void run() {
        location = location.clone();
        location.setY(location.getY() - 2);
        world.dropItemNaturally(location, itemStack);
        world.playSound(location, Sound.ENTITY_CHICKEN_EGG, 1, 2);
    }
}
