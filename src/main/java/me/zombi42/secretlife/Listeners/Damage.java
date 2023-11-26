/*
SecretLife © 2023 by Zombi42 is licensed under CC BY-NC-SA 4.0.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/
https://github.com/Zombi442
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
