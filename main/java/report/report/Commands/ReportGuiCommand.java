package report.report.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import report.report.ConfigManager;

import java.util.ArrayList;
import java.util.List;

public class ReportGuiCommand implements CommandExecutor, Listener {

    private final ConfigManager configManager;

    public ReportGuiCommand(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length != 1) {
                player.sendMessage("Použití: /report-gui <id>");
                return false;
            }

            String reportId = args[0];

            String path = "reports." + reportId;
            String reporter = configManager.getMessage(path + ".reporter");
            String reportedPlayer = configManager.getMessage(path + ".reportedPlayer");
            String reason = configManager.getMessage(path + ".reason");

            if (reporter == null || reportedPlayer == null || reason == null) {
                player.sendMessage("Report with ID " + reportId + " doesn't exist.");
                return false;
            }

            openReportGUI(player, reporter, reportedPlayer, reason, reportId);
            return true;
        } else {
            sender.sendMessage("Tento příkaz může použít pouze hráč.");
            return false;
        }
    }

    private void openReportGUI(Player player, String reportId, String reporter, String reportedPlayer, String reason) {
        Inventory gui = Bukkit.createInventory(null, 9, reportId);



        ItemStack infoItem = new ItemStack(Material.BOOK);
        ItemMeta infoMeta = infoItem.getItemMeta();
        infoMeta.setDisplayName("Informations about Report");


        ItemStack remItem = new ItemStack(Material.BARRIER);
        ItemMeta remMeta = remItem.getItemMeta();
        infoMeta.setDisplayName("Remove report");
        List<String> lore = configManager.config.getStringList("reportGuiLore");
        lore.replaceAll(line -> ChatColor.translateAlternateColorCodes('&', replaceVariables(line, reportId, reporter, reportedPlayer, reason)));

        infoMeta.setLore(lore);
        infoItem.setItemMeta(infoMeta);
        gui.setItem(4, infoItem);
        gui.setItem(8, remItem);

        player.openInventory(gui);
    }

    private String replaceVariables(String line, String reportId, String reporter, String reportedPlayer, String reason) {
        line = line.replace("@reportid", reportId);
        line = line.replace("@reporter", reporter);
        line = line.replace("@reportedPlayer", reportedPlayer);
        line = line.replace("@reason", reason);
        return line;
    }
    public void onInventoryClick(InventoryClickEvent event){
            ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem.getItemMeta().getDisplayName().equals("Remove report")){
            String reportId = event.getView().getTitle();
            configManager.removeReport(reportId);
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            player.sendMessage("test");



        }
    }


}