package me.staartvin.prisongang.commands;

import java.util.List;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GangsListCommand implements CommandExecutor {

	private PrisonGang plugin;

	public GangsListCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 1) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang list");
			return true;
		}

		if (!plugin.getCommands().hasPermission(sender, "prisongang.list"))
			return true;
		
		List<Gang> gangs = plugin.getGangHandler().getAllGangs();
		
		String fullString = "";
		
		for (int i =0; i< gangs.size(); i++) {
			Gang gang = gangs.get(i);
			if (i == (gangs.size() - 1)) {
				fullString = fullString +  gang.getGangName();
			} else {
				fullString = fullString + gang.getGangName() + ", ";
			}
		}
		
		if (gangs.size() != 0) {
			sender.sendMessage(ChatColor.GOLD + "------[Gangs]------");
			sender.sendMessage(ChatColor.BLUE + fullString);	
		} else {
			sender.sendMessage(ChatColor.BLUE + "There are no gangs!");
		}
		
		return true;
	}
}
