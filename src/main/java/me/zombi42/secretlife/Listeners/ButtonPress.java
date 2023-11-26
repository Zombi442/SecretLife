/*
SecretLife © 2023 by Zombi42 is licensed under CC BY-NC-SA 4.0.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/
https://github.com/Zombi442
 */
package me.zombi42.secretlife.Listeners;

import me.zombi42.secretlife.Util.Button;
import me.zombi42.secretlife.Util.ButtonType;
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

        if (event.getClickedBlock().getType() != Material.STONE_BUTTON) {
            return;
        }

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
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
