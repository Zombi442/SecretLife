/*
SecretLife © 2023 by Zombi42 is licensed under CC BY-NC-SA 4.0.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/
https://github.com/Zombi442
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
    public GiveBook(Player player, ConfigManager configManager) {
        this.player = player;
        this.configManager = configManager;
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
        bookMeta.addPage(pageContent);


        writtenBook.setItemMeta(bookMeta);
        player.getInventory().addItem(writtenBook);

        if(configManager.getSettingsConfig().getBoolean("Animation")){
            player.playEffect(EntityEffect.TOTEM_RESURRECT);
        }


    }
}
