package net.batschicraft.main.utils;

import net.batschicraft.main.HeightLimiter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private YamlConfiguration heightLimit;
    private String heightLimitValue = "HeightLimit";
    private String bypassMassege = "Height is reached Message";
    private File heightLimitFile;

    public YamlConfiguration getHeightLimiter() {
        return heightLimit;
    }

    public Config(HeightLimiter instance) {
        heightLimitFile = new File(instance.getDataFolder(), "config.yml");
        final String message = "Invalid or missing config, no heightLimit active";

        if (!heightLimitFile.exists()) {
            instance.getDataFolder().mkdir();

            try {
                heightLimitFile.createNewFile();
                heightLimit = new YamlConfiguration();
                heightLimit.set(heightLimitValue, 256);
                heightLimit.set(bypassMassege, "§4§lYou can not go any higher");
                heightLimit.save(heightLimitFile);
            } catch (IOException e) {
                instance.getLogger().warning(message);
            }
        }

        heightLimit = new YamlConfiguration();

        try {
            heightLimit.load(heightLimitFile);
            if (heightLimit.get(heightLimitValue) == null) {
                heightLimit.set(heightLimitValue, 256);
                heightLimit.save(heightLimitFile);
            }
            if (heightLimit.get(bypassMassege) == null) {
                heightLimit.set(bypassMassege, HeightLimiter.getPrefix() + "§4§lYou can not go any higher");
                heightLimit.save(heightLimitFile);
            }
        } catch (IOException | InvalidConfigurationException e) {
            instance.getLogger().warning(message);
        }
    }

    public String getHeightLimitValue() {
        return heightLimitValue;
    }

    public void setHeightLimitValue(String newLimit) {
        try {
            double limit = Double.parseDouble(newLimit);
            heightLimit = new YamlConfiguration();
            heightLimit.load(heightLimitFile);
            heightLimit.set(heightLimitValue, limit);
            heightLimit.save(heightLimitFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String getBypassMassege() {
        return bypassMassege;
    }
}
