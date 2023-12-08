package report.report.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import report.report.ConfigManager;

public class FindCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public FindCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("reportsystem.find")) {
                return true;
            }

            if (args.length != 1) {
                String fru = configManager.getMessage("findReportUsage");
                player.sendMessage(fru);
                return true;
            }

            String reportId = args[0];

            String reportInfoMessage = configManager.getReportInfoMessage(reportId);


            player.sendMessage(reportInfoMessage);
            return true;
        } else {
            sender.sendMessage("Tento příkaz může použít pouze hráč.");
            return false;
        }
    }
}
