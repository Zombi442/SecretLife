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

import me.zombi42.secretlife.Enum.ButtonType;
import me.zombi42.secretlife.SecretLife;
import me.zombi42.secretlife.Tasks.DropItemLater;
import me.zombi42.secretlife.Tasks.SpawnParticleLater;
import me.zombi42.secretlife.Util.Button;
import me.zombi42.secretlife.Util.ConfigManager;
import me.zombi42.secretlife.Util.DropManager;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.data.type.Switch;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ButtonPress implements Listener {
    ConfigManager configManager;

    DropManager dropManager;
    SecretLife secretLife;

    public ButtonPress(ConfigManager configManager, DropManager dropManager, SecretLife secretLife) {
        this.configManager = configManager;
        this.dropManager = dropManager;
        this.secretLife = secretLife;
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {


        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }


        if (event.getClickedBlock().getType() != Material.STONE_BUTTON) {
            return;
        }

        if (event.getClickedBlock().getBlockData() instanceof Switch) {
            Switch aSwitch = (Switch) event.getClickedBlock().getBlockData();
            if (aSwitch.isPowered()) {
                return;
            }
        }


        Location location = event.getClickedBlock().getLocation();


        Button button = null;


        for (ButtonType buttonType : ButtonType.values()) {
            Button button1 = configManager.getButton(buttonType);
            if (button1 != null) {
                if (button1.getLocation().equals(location)) {
                    button = configManager.getButton(buttonType);
                    break;
                }
            }
        }

        if (button == null) {
            return;
        }


        if (button.getButtonType() == ButtonType.Success) {

            Player player = event.getPlayer();
            World world = player.getWorld();

            Double howManyHeartsToGivePlayer;

            howManyHeartsToGivePlayer = 60 - player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            if (howManyHeartsToGivePlayer >= 20) {

                howManyHeartsToGivePlayer = 20.0;
                AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                attribute.setBaseValue(attribute.getBaseValue() + howManyHeartsToGivePlayer);
                player.setHealth(attribute.getBaseValue());
                player.sendTitle(ChatColor.GREEN + "+ " + Math.round(howManyHeartsToGivePlayer / 2) + "  Hearts!", "", 20, 40, 2);
                return;
            }


            Double extraHearts = 0.0;
            extraHearts = 20 - howManyHeartsToGivePlayer;
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(60);
            player.setHealth(60);
            if (Math.round(howManyHeartsToGivePlayer / 2) != 0) {
                player.sendTitle(ChatColor.GREEN + "+ " + Math.round(howManyHeartsToGivePlayer / 2) + "  Hearts!", "", 20, 40, 2);
            }

            Location itemDropLocation = configManager.getItemDropLocation();
            itemDropLocation.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, itemDropLocation.getX(), itemDropLocation.getY(), itemDropLocation.getZ(), 6000, .25, 0.25, 0.25, 20);
            for (int i = 40; i < 100; i = i + 10) {
                new SpawnParticleLater(itemDropLocation).runTaskLater(secretLife, i);
            }
            int timeElapsed = 40;
            for (ItemStack itemStack : dropManager.getItems(extraHearts)) {
                new DropItemLater(itemStack, itemDropLocation, player).runTaskLater(secretLife, timeElapsed);
                timeElapsed = timeElapsed + 5;
            }


        }
    }
}
