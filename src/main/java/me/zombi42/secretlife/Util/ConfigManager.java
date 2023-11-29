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
package me.zombi42.secretlife.Util;

import me.zombi42.secretlife.Enum.ButtonType;
import me.zombi42.secretlife.SecretLife;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class ConfigManager {

    SecretLife secretLife;
    YamlConfiguration secretConfig;
    YamlConfiguration settingsConfig;
    YamlConfiguration livesConfig;
    File secretFile;
    File settingsFile;
    File livesFile;
    Location itemDropLocation;
    Button successButton = null;
    Button failButton = null;
    Button hardButton = null;
    Map<String, Integer> lives = new HashMap<>();

    public ConfigManager(SecretLife secretLife) {
        secretFile = new File(secretLife.getDataFolder() + "/secrets.yml");
        settingsFile = new File(secretLife.getDataFolder() + "/Settings.yml");
        livesFile = new File(secretLife.getDataFolder() + "/Lives.yml");
        this.secretLife = secretLife;

        initialize(secretLife);

    }

    private void initialize(SecretLife secretLife) {
        this.secretLife = secretLife;
        if (!secretFile.exists()) {
            secretLife.saveResource("Secrets.yml", false);
        }
        if (!settingsFile.exists()) {
            secretLife.saveResource("Settings.yml", false);
        }
        if (!livesFile.exists()) {
            secretLife.saveResource("Lives.yml", false);
        }

        settingsConfig = YamlConfiguration.loadConfiguration(settingsFile);
        secretConfig = YamlConfiguration.loadConfiguration(secretFile);
        livesConfig = YamlConfiguration.loadConfiguration(livesFile);

        loadConfig();
    }

    public void saveConfig() {

        saveLocation(this.itemDropLocation, "itemDropLocation");
        saveButton(failButton);
        saveButton(successButton);
        saveButton(hardButton);
        saveLives();


        Bukkit.getLogger().info("Saved Config!");
    }

    public void loadConfig() {
        Map<String, Object> map = livesConfig.getConfigurationSection("Lives.").getValues(false);

        for (String string : map.keySet()) {
            if (Bukkit.getPlayer(string) != null) {
                try {

                    this.lives.put(string, (Integer) map.get(string));
                } catch (ClassCastException e) {
                    Bukkit.getLogger().warning("Found non integer values in Lives.yml");
                }
            }
        }

        Button success = getLocation(ButtonType.Success);
        Button hard = getLocation(ButtonType.Hard);
        Button fail = getLocation(ButtonType.Fail);
        Location items = getLocation("itemDropLocation");

        if (success != null) {
            successButton = success;
        }
        if (hard != null) {
            hardButton = hard;
        }
        if (fail != null) {
            failButton = fail;
        }
        if (items != null) {
            this.itemDropLocation = items;
        }
    }

    public YamlConfiguration getSettingsConfig() {
        return settingsConfig;
    }

    public YamlConfiguration getSecretConfiguration() {
        return this.secretConfig;
    }

    public void saveLocation(Location location, String locationName) {

        settingsConfig.set("Locations." + locationName + ".x", location.getX());
        settingsConfig.set("Locations." + locationName + ".y", location.getY());
        settingsConfig.set("Locations." + locationName + ".z", location.getZ());
        settingsConfig.set("Locations." + locationName + ".world", location.getWorld().getUID().toString());

        try {
            settingsConfig.save(settingsFile);
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    public void saveLocation(Button button) {

        if (button == null) {
            return;
        }

        saveLocation(button.getLocation(), button.getButtonType().toString());
    }

    public Location getLocation(String locationName) {

        if (settingsConfig.get("Locations." + locationName) == null) {
            return null;
        }

        double X = settingsConfig.getDouble("Locations." + locationName + ".x");
        double Y = settingsConfig.getDouble("Locations." + locationName + ".y");
        double Z = settingsConfig.getDouble("Locations." + locationName + ".z");
        String worldUUIDString = String.valueOf(settingsConfig.get("Locations." + locationName + ".world"));
        UUID worldUUID = UUID.fromString(worldUUIDString);
        World world = Bukkit.getWorld(worldUUID);

        return new Location(world, X, Y, Z);
    }

    public Button getLocation(ButtonType buttonType) {
        return getLocation(buttonType);
    }

    public void removeLocation(ButtonType buttonType) {

        settingsConfig.set("Locations." + buttonType, null);

        try {
            settingsConfig.save(settingsFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeLocation(String locationName) {

        settingsConfig.set("Locations." + locationName, null);

        try {
            settingsConfig.save(settingsFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Location getItemDropLocation() {
        return itemDropLocation;
    }

    public void setItemDropLocation(Location itemDropLocation) {
        this.itemDropLocation = itemDropLocation;
    }

    void saveButton(Button button) {
        saveLocation(button);
    }

    public Button getButton(ButtonType buttonType) {
        switch (buttonType) {
            case Fail:
                return failButton;
            case Hard:
                return hardButton;
            case Success:
                return successButton;
            default:
                return null;
        }
    }

    public Button getButton(String buttonType) {
        return getButton(ButtonType.valueOf(buttonType));
    }


    public void setButton(Button button) {
        switch (button.getButtonType()) {
            case Fail:
                failButton = button;
            case Hard:
                hardButton = button;
            case Success:
                successButton = button;
        }
    }

    void saveLives() {
        for (String string : lives.keySet()) {
            livesConfig.set("Lives." + string, lives.get(string));
        }

        try {
            livesConfig.save(livesFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public int getLives(Player player) {
        return lives.get(player.getName());
    }

    public void setLives(Player player, int lives) {
        this.lives.put(player.getName(), lives);
    }

    public boolean livesContainsKey(Player player) {
        return lives.get(player.getName()) != null;
    }

}
