/*
SecretLife © 2023 by Zombi42 is licensed under CC BY-NC-SA 4.0.
To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/
https://github.com/Zombi442
 */
package me.zombi42.secretlife.Util;

import me.zombi42.secretlife.SecretLife;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ConfigManager {


    Button successButton = null;
    Button failButton = null;
    Button hardButton = null;
    SecretLife secretLife;
    YamlConfiguration secretConfig;
    YamlConfiguration settingsConfig;
    File secretFile;
    File settingsFile;
    Location itemDropLocation;

    public ConfigManager(SecretLife secretLife) {
        secretFile = new File(secretLife.getDataFolder() + "/secrets.yml");
        settingsFile = new File(secretLife.getDataFolder() + "/Settings.yml");
        this.secretLife = secretLife;

        initialize(secretLife);

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

        settingsConfig.set("Locations." + button.getButtonType().toString() + ".x", button.getX());
        settingsConfig.set("Locations." + button.getButtonType().toString() + ".y", button.getY());
        settingsConfig.set("Locations." + button.getButtonType().toString() + ".z", button.getZ());
        settingsConfig.set("Locations." + button.getButtonType().toString() + ".world", button.getWorld().getUID().toString());

        try {
            settingsConfig.save(settingsFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Location getLocation(String locationName) {

        if (settingsConfig.get("Locations." + locationName) == null) {
            return null;
        }

        Double X = settingsConfig.getDouble("Locations." + locationName + ".x");
        Double Y = settingsConfig.getDouble("Locations." + locationName + ".y");
        Double Z = settingsConfig.getDouble("Locations." + locationName + ".z");
        String worldUUIDString = String.valueOf(settingsConfig.get("Locations." + locationName + ".world"));
        UUID worldUUID = UUID.fromString(worldUUIDString);
        World world = Bukkit.getWorld(worldUUID);

        return new Location(world, X, Y, Z);
    }

    public Button getLocation(ButtonType buttonType) {
        if (settingsConfig.get("Locations." + buttonType.toString()) == null) {
            return null;
        }

        Double X = settingsConfig.getDouble("Locations." + buttonType + ".x");
        Double Y = settingsConfig.getDouble("Locations." + buttonType + ".y");
        Double Z = settingsConfig.getDouble("Locations." + buttonType + ".z");
        String worldUUIDString = String.valueOf(settingsConfig.get("Locations." + buttonType + ".world"));
        UUID worldUUID = UUID.fromString(worldUUIDString);
        World world = Bukkit.getWorld(worldUUID);

        return new Button(world, X, Y, Z, buttonType);

    }

    public void removeLocation(ButtonType buttonType) {

        settingsConfig.set("Locations." + buttonType.toString(), null);

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



    private void initialize(SecretLife secretLife) {
        if (!secretFile.exists()) {
            secretLife.saveResource("Secrets.yml", false);
        }
        if (!settingsFile.exists()) {
            secretLife.saveResource("Settings.yml", false);
        }

        settingsConfig = YamlConfiguration.loadConfiguration(settingsFile);
        secretConfig = YamlConfiguration.loadConfiguration(secretFile);


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

    public Location getItemDropLocation() {
        return itemDropLocation;
    }

    public void setItemDropLocation(Location itemDropLocation) {
        saveLocation(itemDropLocation, "itemDropLocation");
        this.itemDropLocation = itemDropLocation;
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


    public void setButton(Button button){
        switch (button.getButtonType()) {
            case Fail:
                failButton = button;
            case Hard:
                hardButton = button;
            case Success:
                successButton = button;
        }
    }


}
