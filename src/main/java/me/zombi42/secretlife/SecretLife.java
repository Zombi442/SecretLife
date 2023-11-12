/*

SecretLife © 2023 by Zombi42 is licensed under CC BY-NC-SA 4.0.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/
https://github.com/Zombi442

I do not know how licenses work. Help me.
 */


package me.zombi42.secretlife;

import me.zombi42.secretlife.Commands.DisperseSecrets;
import org.bukkit.plugin.java.JavaPlugin;


public final class SecretLife extends JavaPlugin {

    @Override
    public void onEnable() {


        saveResource("Secrets.yml", false);
        saveResource("Settings.yml", false);


        ConfigManager configManager = new ConfigManager(this);
        new DisperseSecrets(this,configManager);
    }

    @Override
    public void onDisable() {

    }
}
