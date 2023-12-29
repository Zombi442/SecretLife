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

import me.zombi42.secretlife.Util.ConfigManager;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class GiveBook extends BukkitRunnable {

    Player player;
    ConfigManager configManager;
    boolean animationBypass;

    public GiveBook(Player player, ConfigManager configManager, boolean animationBypass) {
        this.player = player;
        this.configManager = configManager;
        this.animationBypass = animationBypass;
    }

    @Override
    public void run() {

        ItemStack writtenBook = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) writtenBook.getItemMeta();

        String path = "Players." + player.getDisplayName();
        String pageContent = "Could not get player name from config. \n Report this error! This is not a task!";

        YamlConfiguration yamlConfiguration = configManager.getSecretConfiguration();
        if(yamlConfiguration.get(path) != null){
            pageContent = yamlConfiguration.get(path).toString();
        }


        bookMeta.setTitle(player.getDisplayName() + "'s Secret Task");
        bookMeta.setAuthor("Secret Keeper");
        while(pageContent.length() >= 256){
            bookMeta.addPage(pageContent.substring(0, 255));
            pageContent = pageContent.substring(255);
        }
        bookMeta.addPage(pageContent);

        writtenBook.setItemMeta(bookMeta);
        player.getInventory().addItem(writtenBook);

        if(configManager.getSettingsConfig().getBoolean("Animation") && !animationBypass){
            player.playEffect(EntityEffect.TOTEM_RESURRECT);
        }


    }
}
