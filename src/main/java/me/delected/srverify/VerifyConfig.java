package me.delected.srverify;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class VerifyConfig {
    private static File file;
    private static FileConfiguration customFile;

    public static void setup() throws IOException {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("SRVerify").getDataFolder(), "verify.yml");

        if (!file.exists()) {
            file.createNewFile();
        }
        customFile = YamlConfiguration.loadConfiguration(file);

    }

    public static FileConfiguration get() {
        return customFile;
    }

    public static void save() {
        try {
            customFile.save(file);
        } catch (IOException e) {
            System.out.println("Couldn't save verify.yml!");
        }
    }

    public static void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }

}

