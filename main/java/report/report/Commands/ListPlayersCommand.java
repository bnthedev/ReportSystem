package report.report.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import report.report.ConfigManager;


public class ListPlayersCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public ListPlayersCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player player = (Player) sender;
            if (!player.hasPermission("reportsystem.helperlist")) {
                return true;
            }
            String playersMessage = configManager.getPlayersMessage();

            player.sendMessage(playersMessage);
            return true;
        } else {
            sender.sendMessage("Tento příkaz může použít pouze hráč.");
            return false;
        }
    }
}


