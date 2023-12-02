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
package me.zombi42.secretlife.Listeners;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Damage implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player player = (Player) event.getEntity();

        if (player.getHealth() >= event.getFinalDamage()) {
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(player.getHealth() - event.getFinalDamage());
        }
    }
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        if (event.getRespawnReason() == PlayerRespawnEvent.RespawnReason.END_PORTAL) {
            return;
        }

        if (event.getPlayer() != null) {
            event.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(60);
        }
    }
}
