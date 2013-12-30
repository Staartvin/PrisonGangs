package me.staartvin.prisongang.commands;

import java.util.ArrayList;
import java.util.List;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AdminRemoveCommand implements CommandExecutor {

	private PrisonGang plugin;

	public AdminRemoveCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 1) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang admin remove <gangname>");
			return true;
		}
		
		String gangName = null;
		
		if (args.length > 2) {
			List<String> newArgs = new ArrayList<String>();

			for (int i = 0; i < args.length; i++) {
				if (i == 0 || i == 1)
					continue;
				else {
					newArgs.add(args[i]);
				}
			}

			gangName = plugin.getCommands().getFullString(newArgs);
		}
		
		Gang gang;

		gang = plugin.getGangHandler().getGang(gangName);
		
		if (gang == null) {
			sender.sendMessage(ChatColor.RED + "That gang doesn't exist!");
			return true;
		}

		if (!plugin.getCommands().hasPermission(sender, "prisongang.admin.remove"))
			return true;

		// Notice everyone
		plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Gang '" + gang.getGangName() + "' was disbanded!");
		
		// Remove gang
		plugin.getGangHandler().deleteGang(gang.getGangName());

		return true;
	}
}
