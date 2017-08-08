package uk.antiperson.stackspawner;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by nathat on 08/01/17.
 */
public class Commands implements CommandExecutor {

    private StackSpawner ss;
    private String tag = ChatColor.DARK_PURPLE + "[" + ChatColor.AQUA + "StackSpawner" + ChatColor.DARK_PURPLE + "] ";
    private String beforeBig = ChatColor.GRAY + "------" + tag.replace(" ", "") + ChatColor.GRAY + "------------------------------";
    public Commands(StackSpawner ss){
        this.ss = ss;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args){
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 0){
                if(p.hasPermission("StackSpawner.Commands") || p.hasPermission("StackSpawner.*")){
                    p.sendMessage(beforeBig);
                    p.sendMessage(ChatColor.GOLD + "/sts about " + ChatColor.YELLOW + "Shows information about this plugin.");
                }else{
                    sender.sendMessage(tag + ChatColor.RED + "Error: You don't have the permission to do that!");
                }
            }else if (args.length == 1){
                if(args[0].equalsIgnoreCase("about")){
                    if(p.hasPermission("StackSpawner.Commands") || p.hasPermission("StackSpawner.*")){
                        p.sendMessage(beforeBig);
                        p.sendMessage(ChatColor.GREEN + "StackSpawner v" + ss.getDescription().getVersion() + " by antiPerson.");
                        p.sendMessage(ChatColor.GREEN + "Find out more at " + ss.getDescription().getWebsite());
                        p.sendMessage(ChatColor.GREEN + "Has this plugin helped you? Please leave a review, it helps a lot!");
                    }else{
                        sender.sendMessage(tag + ChatColor.RED + "Error: You don't have the permission to do that!");
                    }

                }else{
                    sender.sendMessage(tag + ChatColor.RED + "Error: Invaild arguments!");
                }
            }else{
                sender.sendMessage(tag + ChatColor.RED + "Error: Invaild arguments!");
            }
        }else{
            sender.sendMessage(tag + ChatColor.RED + "Error: You must be a player!");
        }


        return false;
    }
}
