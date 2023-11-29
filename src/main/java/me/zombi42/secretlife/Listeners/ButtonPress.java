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

import me.zombi42.secretlife.Util.Button;
import me.zombi42.secretlife.Enum.ButtonType;
import me.zombi42.secretlife.Util.ConfigManager;
import me.zombi42.secretlife.Util.DropManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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


    public ButtonPress(ConfigManager configManager, DropManager dropManager) {
        this.configManager = configManager;
        this.dropManager = dropManager;
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

        for(ButtonType buttonType : ButtonType.values()){
            if(configManager.getButton(buttonType).getLocation().equals(location)){
                button = configManager.getButton(buttonType);
                break;
            }
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
                return;
            }


            Double extraHearts = 0.0;
            extraHearts = 20 - howManyHeartsToGivePlayer;
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(60);
            player.setHealth(60);


            for (ItemStack itemStack : dropManager.drop(extraHearts)) {
                world.dropItemNaturally(configManager.getItemDropLocation(), itemStack);

            }

        }
    }
}
