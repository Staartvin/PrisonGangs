package me.staartvin.prisongang.commands;

import me.staartvin.prisongang.PrisonGang;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetListCommand implements CommandExecutor {

	@SuppressWarnings("unused")
	private PrisonGang plugin;

	public SetListCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {	
		
		if (args.length < 1) {
			sender.sendMessage(ChatColor.YELLOW + "Usage: /gang set list");
			return true;
		}

		/*if (!plugin.getCommands().hasPermission(sender, "prisongang.list"))
			return true;*/

		String fullString = "";

		for (int i = 0; i < Commands.allowedSetArgs.length; i++) {
			String arg = Commands.allowedSetArgs[i];
			if (i == (Commands.allowedSetArgs.length - 1)) {
				fullString = fullString + arg;
			} else {
				fullString = fullString + arg + ", ";
			}
		}

		sender.sendMessage(ChatColor.GOLD
				+ "You can change the following: ");
		sender.sendMessage(ChatColor.GREEN + fullString);

		return true;
	}
}
