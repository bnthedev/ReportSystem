package report.report;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ConfigManager {

    private final JavaPlugin plugin;
    public FileConfiguration config;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    private void loadConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig() {
        try {
            config.save(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void removeReport(String id) {
        String path = "reports." + id;
        config.set(path, null);
        saveConfig();
    }
    public String getMessage(String key) {
        return ChatColor.translateAlternateColorCodes('&', config.getString(key, ""));
    }
    public void reloadConfig() {
        loadConfig();
    }
    public void addReport(String id, String reporter, String reportedPlayer, String reason) {
        String path = "reports." + id;

        config.set(path + ".reporter", reporter);
        config.set(path + ".reportedPlayer", reportedPlayer);
        config.set(path + ".reason", reason);
        config.set(path + ".timestamp", System.currentTimeMillis());

        saveConfig();
    }

    public String generateRandomId() {
        Random random = new Random();
        return String.valueOf(random.nextInt(9999));
    }

    public List<String> getReports() {
        return config.getStringList("reports");
    }
    public String getLastReportId() {
        ConfigurationSection reportsSection = config.getConfigurationSection("reports");
        if (reportsSection != null) {
            Set<String> reportIds = reportsSection.getKeys(false);
            if (!reportIds.isEmpty()) {
                int maxId = reportIds.stream()
                        .mapToInt(Integer::parseInt)
                        .max()
                        .orElse(0);
                return String.valueOf(maxId);
            }
        }
        return "";
    }

    public String getReportInfoMessage(String reportId) {
        String path = "reports." + reportId;

        if (config.contains(path)) {
            String reporter = config.getString(path + ".reporter");
            String reportedPlayer = config.getString(path + ".reportedPlayer");
            String reason = config.getString(path + ".reason");
            long timestamp = config.getLong(path + ".timestamp");

            return ChatColor.translateAlternateColorCodes('&',
                    config.getString("findResult")
                            .replace("%reportId%", reportId)
                            .replace("%reporter%", reporter)
                            .replace("%reportedPlayer%", reportedPlayer)
                            .replace("%reason%", reason)
                            .replace("%timestamp%", String.valueOf(timestamp))
            );
        }

        return ChatColor.translateAlternateColorCodes('&',
                config.getString("findResultNotFound")
                        .replace("%reportId%", reportId)
        );
    }
    public void addPlayerPoints(String playerName, int points) {
        String path = "players." + playerName + ".points";
        int currentPoints = config.getInt(path, 0);
        config.set(path, currentPoints + points);
        saveConfig();
    }
    public void resetPlayers() {
        ConfigurationSection playersSection = config.getConfigurationSection("players");

        if (playersSection != null) {
            Set<String> playerNames = playersSection.getKeys(false);
            for (String playerName : playerNames) {
                playersSection.set(playerName, null);
            }

            saveConfig();

            System.out.println("Seznam hráčů byl resetován.");
        } else {
            System.out.println("Chyba: Sekce 'players' neexistuje v konfiguraci.");
        }
    }
    public List<String> getAllPlayers() {
        ConfigurationSection playersSection = config.getConfigurationSection("players");

        if (playersSection != null) {
            Set<String> playerNames = playersSection.getKeys(false);
            List<String> players = new ArrayList<>(playerNames);

            System.out.println("Seznam hráčů z konfigurace: " + players);

            return players;
        } else {
            System.out.println("Chyba: Sekce 'players' neexistuje v konfiguraci.");
            return Collections.emptyList();
        }
    }

    public String getPlayersMessage() {
        ConfigurationSection playersSection = config.getConfigurationSection("players");

        if (playersSection != null) {
            List<String> players = new ArrayList<>();

            for (String playerName : playersSection.getKeys(false)) {
                int points = playersSection.getInt(playerName + ".points", 0);
                players.add(playerName + " (" + points + " points)");
            }

            String rawMessage = config.getString("listPlayersMessage", "&aList of helpers: &f@players");

            rawMessage = rawMessage.replace("@players", String.join(", ", players));

            return ChatColor.translateAlternateColorCodes('&', rawMessage);
        } else {
            System.out.println("Chyba: Sekce 'players' neexistuje v konfiguraci.");
            return "";
        }
    }

}