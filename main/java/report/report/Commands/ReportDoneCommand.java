package report.report.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import report.report.ConfigManager;

public class ReportDoneCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public ReportDoneCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;
            if (!player.hasPermission("reportsystem.done")) {
                return true;
            }
            if (args.length != 1) {
                String rdu = configManager.getMessage("reportDoneUsage");
                player.sendMessage(rdu);
                return false;
            }

            String reportId = args[0];

            configManager.removeReport(reportId);
            String reportDoneMessage = configManager.getMessage("reportDoneMessage");
            reportDoneMessage = reportDoneMessage.replace("%reportId%", reportId);

            player.sendMessage(reportDoneMessage);
            configManager.addPlayerPoints(player.getName(), 1);
            return true;
        } else {
            sender.sendMessage("Tento příkaz může použít pouze hráč.");
            return false;
        }
    }
}
