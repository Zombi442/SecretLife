/*

SecretLife © 2023 by Zombi42 is licensed under CC BY-NC-SA 4.0.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/
https://github.com/Zombi442

I do not know how licenses work. Help me.
 */


package me.zombi42.secretlife;

import me.zombi42.secretlife.Commands.DisperseSecrets;
import me.zombi42.secretlife.Commands.SetButton;
import me.zombi42.secretlife.Commands.SetItemDrop;
import me.zombi42.secretlife.Listeners.ButtonPress;
import me.zombi42.secretlife.Listeners.Damage;
import me.zombi42.secretlife.Util.ButtonType;
import me.zombi42.secretlife.Util.ConfigManager;
import me.zombi42.secretlife.Util.DropManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;


public final class SecretLife extends JavaPlugin {


    @Override
    public void onEnable() {



        DropManager dropManager = new DropManager();
        ConfigManager configManager = new ConfigManager(this);

        new DisperseSecrets(this,configManager);
        new SetButton(this, configManager);
        new SetItemDrop(this, configManager);

        Bukkit.getServer().getPluginManager().registerEvents(new Damage(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ButtonPress(configManager, dropManager), this);


    }

}
