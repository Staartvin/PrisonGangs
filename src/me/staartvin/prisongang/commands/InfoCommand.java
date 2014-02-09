package me.staartvin.prisongang.commands;

import java.util.ArrayList;
import java.util.List;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.playerdata.PlayerData;
import me.staartvin.prisongang.translation.Lang;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfoCommand implements CommandExecutor {

	private PrisonGang plugin;

	public InfoCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 1) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang info <gang name>");
			return true;
		}

		String gangName;
		PlayerData player;

		// Fetch targeted gangName
		if (args.length > 1) {
			List<String> newArgs = new ArrayList<String>();

			for (int i = 0; i < args.length; i++) {
				if (i == 0)
					continue;
				else {
					newArgs.add(args[i]);
				}
			}

			gangName = plugin.getCommands().getFullString(newArgs);
		} else {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Lang.PLEASE_PROVIDE_GANG.getConfigValue(null));
				return true;
			}

			player = plugin.getPlayerDataHandler().getPlayerData(
					sender.getName(), false);

			if (!player.isInGang()) {
				sender.sendMessage(Lang.PLEASE_PROVIDE_GANG.getConfigValue(null));
				return true;
			}

			gangName = player.getGangName();
		}

		
		if (!plugin.getCommands().hasPermission(sender, "prisongang.info"))
			return true;

		Gang gang = plugin.getGangHandler().getGang(gangName);
		
		if (gang == null) {
			sender.sendMessage(Lang.GANG_DOES_NOT_EXIST.getConfigValue(null));
			return true;
		}
		
		sender.sendMessage(ChatColor.GOLD + "-------[" + gang.getGangName() + "]-------");
		// Show info about gang
		sender.sendMessage(ChatColor.GOLD + "Started: " + ChatColor.GRAY + gang.getInfo("creationDate"));
		sender.sendMessage(ChatColor.GOLD + "Leader: " + ChatColor.GRAY + gang.getLeader());
		sender.sendMessage(ChatColor.GOLD + "Creator: " + ChatColor.GRAY + gang.getInfo("creator"));
		sender.sendMessage(ChatColor.GOLD + "Description: " + ChatColor.GRAY + gang.getInfo("description"));
		sender.sendMessage(ChatColor.GOLD + "Private: " + ChatColor.GRAY + gang.isPrivate());
		sender.sendMessage(ChatColor.GOLD + "Members: " + ChatColor.GRAY + gang.getMembers().size());
		sender.sendMessage(ChatColor.GOLD + "Allies: " + ChatColor.GRAY + gang.getAllies().size());
		sender.sendMessage(ChatColor.GOLD + "Enemies: " + ChatColor.GRAY + gang.getEnemies().size());
		
		return true;
	}
}
