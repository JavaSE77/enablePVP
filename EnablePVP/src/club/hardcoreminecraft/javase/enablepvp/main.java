package club.hardcoreminecraft.javase.enablepvp;

import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
  private static main plugin;
  
  public void onEnable() {
    plugin = (main)getPlugin(main.class);
    PluginManager pluginManager = getServer().getPluginManager();
    getConfig().options().copyDefaults(true);
    saveDefaultConfig();
    getCommand("pvp").setExecutor((CommandExecutor)this);
    getLogger().info("Enable PVP, created by JavaSE");
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
    if (cmdLabel.equalsIgnoreCase("pvp")) {
      if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
        if (sender.hasPermission("JavaSE.pvp.reload")) {
          reloadConfig();
          sender.sendMessage(ChatColor.LIGHT_PURPLE + "reloaded");
          return true;
        } 
        sender.sendMessage(ChatColor.DARK_RED + "JavaSE has personally forbidden you from using this command.");
      } 
      if (!(sender instanceof Player)) {
        sender.sendMessage(ChatColor.DARK_RED + "Sorry! This command can only be done by players");
        return false;
      } 
      Player player = (Player)sender;
      pvpEnable(player);
    } 
    return false;
  }
  
  public void pvpEnable(Player player) {
    String pm = getConfig().getString("playerMessage");
    if (pm != null && !pm.isEmpty())
      player.sendMessage(pm.replaceAll("&", "§").replaceAll("%player%", player.getName()));
    String bm = getConfig().getString("broadcastMessage");
    if (bm != null && !bm.isEmpty())
      player.sendMessage(bm.replaceAll("&", "§").replaceAll("%player%", player.getName())); 
    List<String> list = getConfig().getStringList("commandOnUse");
    for (String string : list)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), string.replaceAll("%player%", player.getName())); 
    if (getConfig().getBoolean("kickOnEnable")) {
      String kickmsg = getConfig().getString("kickMessage");
      player.kickPlayer(kickmsg.replaceAll("&", "§"));
    } 
  }
}
