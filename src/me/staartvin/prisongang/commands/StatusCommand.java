package me.staartvin.prisongang.commands;

import java.util.ArrayList;
import java.util.List;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.permissions.GangAbility;
import me.staartvin.prisongang.playerdata.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StatusCommand implements CommandExecutor {

	private PrisonGang plugin;

	public StatusCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 2) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang ally/enemy/neutral <gang name>");
			return true;
		}

		String status = args[0];
		PlayerData player;	
		Gang gang;
		Gang targetGang = null;

		// Fetch title
		if (args.length > 1) {
			List<String> newArgs = new ArrayList<String>();

			for (int i = 1; i < args.length; i++) {
					newArgs.add(args[i]);
			}

			targetGang = plugin.getGangHandler().getGang(plugin.getCommands().getFullString(newArgs));
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can set the status of two gangs!");
			return true;
		}

		player = plugin.getPlayerDataHandler().getPlayerData(sender.getName(), false);

		if (targetGang == null) {
			sender.sendMessage(ChatColor.RED + "There is no gang with that name!");
			return true;
		}

		if (!player.isInGang()) {
			sender.sendMessage(ChatColor.RED + "You're not in a gang!");
			return true;
		}
		
		gang = plugin.getGangHandler().getGang(player.getGangName());
		
		/*if (!plugin.getCommands().hasPermission(sender, "prisongang.status")) {
			return true;
		}*/
		
		if (!plugin.getPermissionsManager().hasAbility(sender, GangAbility.CHANGE_STATUS)) {
			sender.sendMessage(ChatColor.RED + "You are not authorised to change the status!");
			return true;
		}
		
		if (gang == null) {
			sender.sendMessage(ChatColor.RED + "There is no such gang!");
			return true;
		}
		
		if (gang.getGangName().equals(targetGang.getGangName())) {
			sender.sendMessage(ChatColor.RED + "You cannot target your own gang!");
			return true;
		}

		if (status.equalsIgnoreCase("neutral")) {
			// Not enemies nor allies
			if (gang.isNeutral(targetGang.getGangName())) {
				sender.sendMessage(ChatColor.RED + "You and " + targetGang.getGangName() + " are already neutral.");
				return true;
			} else {
				// Remove ally and enemy from other gang
				targetGang.removeAlly(gang.getGangName());
				targetGang.removeEnemy(gang.getGangName());
				
				// Remove ally and enemy from home gang
				gang.removeAlly(targetGang.getGangName());
				gang.removeEnemy(targetGang.getGangName());
				
				sender.sendMessage(ChatColor.GREEN + "You and " + targetGang.getGangName() + " are now neutral.");
			}
		} else if (status.equalsIgnoreCase("ally")) {
			// Not enemies nor allies
			if (gang.isAlly(targetGang.getGangName())) {
				sender.sendMessage(ChatColor.RED + "You and " + targetGang.getGangName() + " are already allies.");
				return true;
			} else {
				// Add ally and remove enemy from other gang
				targetGang.addAlly(gang.getGangName());
				targetGang.removeEnemy(gang.getGangName());
				
				// Add ally and remove enemy from home gang
				gang.addAlly(targetGang.getGangName());
				gang.removeEnemy(targetGang.getGangName());
				
				sender.sendMessage(ChatColor.GREEN + "You and " + targetGang.getGangName() + " are now allies.");
			}
		} else if (status.equalsIgnoreCase("enemy")) {
			// Not enemies nor allies
			if (gang.isEnemy(targetGang.getGangName())) {
				sender.sendMessage(ChatColor.RED + "You and " + targetGang.getGangName() + " are already enemies.");
				return true;
			} else {
				// Remove ally and add enemy from other gang
				targetGang.removeAlly(gang.getGangName());
				targetGang.addEnemy(gang.getGangName());
				
				// Remove ally and add enemy from home gang
				gang.removeAlly(targetGang.getGangName());
				gang.addEnemy(targetGang.getGangName());
				
				sender.sendMessage(ChatColor.GREEN + "You and " + targetGang.getGangName() + " are now enemies.");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "You can only set gangs as neutral, ally or enemy.");	
		}
		return true;
	}
}
