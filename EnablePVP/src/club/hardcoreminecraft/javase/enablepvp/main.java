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
      
      if (args.length == 1 && args[0].equalsIgnoreCase("enable")) {
          if (sender.hasPermission("JavaSE.pvp.enable")) {
        	  
        	  int delay = getConfig().getInt("enableDelay") * 20;
	             final Player player = (Player) sender;
	             
	     	    String pm = getConfig().getString("preEnableMessage");
	    	    if (pm != null && !pm.isEmpty())
	    	      player.sendMessage(pm.replaceAll("&", "§").replaceAll("%player%", player.getName()));
	             
     	  Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			    @Override
			    public void run() {


	              pvpEnable(player);
			       
			    }


			},delay);
            return true;
          } 
          sender.sendMessage(ChatColor.DARK_RED + "JavaSE has personally forbidden you from using this command.");
        } 
      
      if (args.length == 1 && args[0].equalsIgnoreCase("disable")) {
          if (sender.hasPermission("JavaSE.pvp.disable")) {
        	  int delay = getConfig().getInt("disableDelay") * 20;

	             final Player player = (Player) sender;
	             
		     	    String pm = getConfig().getString("preDisableMessage");
		    	    if (pm != null && !pm.isEmpty())
		    	      player.sendMessage(pm.replaceAll("&", "§").replaceAll("%player%", player.getName()));
	             
        	  Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
  			    @Override
  			    public void run() {


  	              pvpDisable(player);
  			       
  			    }


  			},delay);
            return true;
          } 
          sender.sendMessage(ChatColor.DARK_RED + "JavaSE has personally forbidden you from using this command.");
        } 
      
      sender.sendMessage(ChatColor.RED + "Use " + ChatColor.GOLD + "/pvp enable " + ChatColor.RED + "to enable pvp. "
    		  + "Use " + ChatColor.GOLD + "/pvp disable " + ChatColor.RED + "to disable pvp.");
     

    } 
    return false;
  }
  
  public void pvpEnable(Player player) {
	  if(!player.isOnline()) return;
	  
    String pm = getConfig().getString("playerOnEnableMessage");
    if (pm != null && !pm.isEmpty())
      player.sendMessage(pm.replaceAll("&", "§").replaceAll("%player%", player.getName()));
    String bm = getConfig().getString("broadcastOnEnableMessage");
    if (bm != null && !bm.isEmpty())
      player.sendMessage(bm.replaceAll("&", "§").replaceAll("%player%", player.getName())); 
    List<String> list = getConfig().getStringList("commandsOnEnable");
    for (String string : list)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), string.replaceAll("%player%", player.getName())); 
    if (getConfig().getBoolean("kickOnEnable")) {
      String kickmsg = getConfig().getString("kickOnEnableMessage");
      player.kickPlayer(kickmsg.replaceAll("&", "§"));
    } 
  }
  
  public void pvpDisable(Player player) {
	  if(!player.isOnline()) return;
	  
	    String pm = getConfig().getString("playerOnDisableMessage");
	    if (pm != null && !pm.isEmpty())
	      player.sendMessage(pm.replaceAll("&", "§").replaceAll("%player%", player.getName()));
	    String bm = getConfig().getString("broadcastOnDisableMessage");
	    if (bm != null && !bm.isEmpty())
	      player.sendMessage(bm.replaceAll("&", "§").replaceAll("%player%", player.getName())); 
	    List<String> list = getConfig().getStringList("commandsOnDisable");
	    for (String string : list)
	      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), string.replaceAll("%player%", player.getName())); 
	    if (getConfig().getBoolean("kickOnDisable")) {
	      String kickmsg = getConfig().getString("kickOnDisableMessage");
	      player.kickPlayer(kickmsg.replaceAll("&", "§"));
	    } 
	  
  }
  
  
}
