package report.report;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import report.report.Commands.*;

public class Main extends JavaPlugin{

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);

        getCommand("report").setExecutor(new ReportCommand(configManager));
        getCommand("report-reload").setExecutor(new ReloadCommand(configManager));
        getCommand("report-find").setExecutor(new FindCommand(configManager));
        getCommand("report-done").setExecutor(new ReportDoneCommand(configManager));
        getCommand("report-help").setExecutor(new HelpCommand(configManager));
        getCommand("report-gui").setExecutor(new ReportGuiCommand(configManager));
        getCommand("report-helperlist").setExecutor(new ListPlayersCommand(configManager));
        getCommand("report-resethelperlist").setExecutor(new RemovePlayersCommand(configManager));

    }

    @Override
    public void onDisable() {
        configManager.saveConfig();

    }

}