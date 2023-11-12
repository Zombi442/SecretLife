/*
SecretLife © 2023 by Zombi42 is licensed under CC BY-NC-SA 4.0.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/
https://github.com/Zombi442
 */
package me.zombi42.secretlife;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {


    YamlConfiguration secretConfig;
    YamlConfiguration config;

    public ConfigManager(SecretLife secretLife) {
        createFile(secretLife);

    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public YamlConfiguration getSecretConfiguration() {
        return this.secretConfig;
    }


    private void createFile(SecretLife secretLife) {
        File secretFile = new File(secretLife.getDataFolder() + "/secrets.yml");
        if (!secretFile.exists()) {
            try {
                secretFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().info(ChatColor.RED + "Could not create Secrets.yml");
                return;
            }
        }

        File settingsFile = new File(secretLife.getDataFolder() + "/Settings.yml");
        if(!settingsFile.exists()){
            try {
                settingsFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().info(ChatColor.RED + "Could not create Settings.yml");
                return;
            }
        }

        config = YamlConfiguration.loadConfiguration(settingsFile);
        secretConfig = YamlConfiguration.loadConfiguration(secretFile);
    }
}
