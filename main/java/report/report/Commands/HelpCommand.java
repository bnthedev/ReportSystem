package report.report.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import report.report.ConfigManager;

public class HelpCommand implements CommandExecutor {
    private final ConfigManager configManager;

    public HelpCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if(p.hasPermission("reportsystem.help")){
            p.sendMessage(configManager.getMessage("adminHelpCommand"));
        }else{
            p.sendMessage(configManager.getMessage("playerHelpCommand"));
        }
        return false;
    }
}
