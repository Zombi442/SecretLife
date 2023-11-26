package me.zombi42.secretlife.Tasks;

import me.zombi42.secretlife.Util.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveConfigTask extends BukkitRunnable {


    ConfigManager configManager;

    public SaveConfigTask(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public void run() {
        configManager.saveConfig();

    }
}
