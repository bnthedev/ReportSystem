package report.report.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import report.report.ConfigManager;

public class ReportCommand implements CommandExecutor {

    private final ConfigManager configManager;

    public ReportCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length < 2) {
                String fewArgumentsMessage = configManager.getMessage("fewArguments");
                player.sendMessage(fewArgumentsMessage);
                return false;
            }

            String reportedPlayer = args[0];
            String reason = String.join(" ", args).substring(args[0].length() + 1);

            String reportMessage = configManager.getMessage("reportMessage");
            reportMessage = reportMessage.replace("@player", player.getName());
            reportMessage = reportMessage.replace("@reportedplayer", reportedPlayer);
            reportMessage = reportMessage.replace("@reason", reason);

            String id = configManager.generateRandomId();

            configManager.addReport(id, player.getName(), reportedPlayer, reason);

            String lastReportId = configManager.getLastReportId();

            reportMessage = reportMessage.replace("@reportid", id);

            String reportSentMessage = configManager.getMessage("reportSent");
            reportSentMessage = reportSentMessage.replace("%reportId%", id);
            reportSentMessage = reportSentMessage.replace("%reportedPlayer%", reportedPlayer);
            reportSentMessage = reportSentMessage.replace("%reason%", reason);

            player.sendMessage(reportSentMessage);

            for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("reportsystem.reports")) {
                    onlinePlayer.sendMessage(reportMessage);
                }
            }


            return true;
        } else {
            sender.sendMessage("Tento příkaz může použít pouze hráč.");
            return false;
        }
    }


}

