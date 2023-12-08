package report.report.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import report.report.ConfigManager;

public class RemovePlayersCommand implements CommandExecutor {
    private final ConfigManager configManager;

    public RemovePlayersCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("reportsystem.resethelpers")) {

            configManager.resetPlayers();

            String m = configManager.getMessage("resetHelpersMessage");
            Player player = (Player) sender;
            player.sendMessage(m);
        }
        return true;
    }
}