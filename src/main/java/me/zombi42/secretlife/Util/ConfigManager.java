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
    YamlConfiguration playerConfig;
    File secretFile;
    File settingsFile;
    File playerConfigFile;
    Location itemDropLocation;
    Button successButton = null;
    Button failButton = null;
    Button hardButton = null;
    Map<String, Integer> lives = new HashMap<>();
    Map<String, Boolean> gifts = new HashMap<>();
    boolean enabled;

    public ConfigManager(SecretLife secretLife) {
        secretFile = new File(secretLife.getDataFolder() + "/secrets.yml");
        settingsFile = new File(secretLife.getDataFolder() + "/Settings.yml");
        playerConfigFile = new File(secretLife.getDataFolder() + "/Players.yml");
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
        if (!playerConfigFile.exists()) {
            secretLife.saveResource("Players.yml", false);
        }

        loadConfig();
    }

    public void saveConfig() {

        saveLocation(this.itemDropLocation, "itemDropLocation");
        saveButton(failButton);
        saveButton(successButton);
        saveButton(hardButton);
        saveLives();
        saveGifts();

    }

    void saveLocation(Location location, String locationName) {

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

    void saveLocation(Button button) {

        if (button == null) {
            return;
        }

        saveLocation(button.getLocation(), button.getButtonType().toString());
    }

    void saveLives() {
        for (String playerName : lives.keySet()) {
            playerConfig.set(playerName + ".lives", lives.get(playerName));
        }

        try {
            playerConfig.save(playerConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    void saveButton(Button button) {
        saveLocation(button);
    }

    void saveGifts() {
        for (String playername : gifts.keySet()) {
            playerConfig.set(playername + ".gited", gifts.get(playername));
        }

        try {
            playerConfig.save(playerConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadConfig() {
        settingsConfig = YamlConfiguration.loadConfiguration(settingsFile);
        secretConfig = YamlConfiguration.loadConfiguration(secretFile);
        playerConfig = YamlConfiguration.loadConfiguration(playerConfigFile);

        if (playerConfig.getConfigurationSection("") != null) {
            Map<String, Object> map = playerConfig.getConfigurationSection("").getValues(false);
            for (String string : map.keySet()) {
                try {
                    Bukkit.getLogger().info(string);
                    Bukkit.getLogger().info(String.valueOf(map.get(string + ".lives")));
                    this.lives.put(string, Integer.parseInt(String.valueOf(playerConfig.get(string + ".lives"))));
                } catch (NumberFormatException e) {
                    Bukkit.getLogger().warning("Found non integer values in Players.yml");
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
        return this.settingsConfig;
    }

    public YamlConfiguration getSecretConfiguration() {
        return this.secretConfig;
    }

    public YamlConfiguration getPlayerConfig() {
        return this.playerConfig;
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
        Location location = getLocation(buttonType.toString());

        if (location == null) {
            return null;
        }

        return new Button(location, buttonType);
    }

    public void removeLocation(String locationName) {

        settingsConfig.set("Locations." + locationName, null);

        try {
            settingsConfig.save(settingsFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void removeLocation(ButtonType buttonType) {
        removeLocation(buttonType.toString());
    }

    public Location getItemDropLocation() {
        return itemDropLocation;
    }

    public void setItemDropLocation(Location itemDropLocation) {
        this.itemDropLocation = itemDropLocation;
        saveLocation(this.itemDropLocation, "itemDropLocation");
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
        saveButton(button);
    }

    public Integer getLives(Player player) {
        return lives.get(player.getName());
    }

    public void setLives(Player player, int lives) {
        this.lives.put(player.getName(), lives);
        saveLives();
    }

    public boolean livesContainsKey(Player player) {
        return lives.containsKey(player.getName());
    }

    public Boolean getGift(Player player) {
        return gifts.get(player.getName());
    }

    public void setGift(Player player, boolean gifted) {
        gifts.put(player.getName(), gifted);
        saveGifts();
    }

}
