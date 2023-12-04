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

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnParticleLater extends BukkitRunnable {


    World world;
    Location location;
    public SpawnParticleLater(Location location) {
        this.location = location;
        this.world = location.getWorld();
    }

    @Override
    public void run() {
        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.LIME, 3);
        world.spawnParticle(Particle.REDSTONE, location.getX(), location.getY() -1, location.getZ(), 40, .1, 0.1, 0.1, dustOptions);
    }
}
