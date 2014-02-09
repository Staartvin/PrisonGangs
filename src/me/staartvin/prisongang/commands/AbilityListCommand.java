package me.staartvin.prisongang.commands;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.permissions.GangAbility;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AbilityListCommand implements CommandExecutor {

	@SuppressWarnings("unused")
	private PrisonGang plugin;

	public AbilityListCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 1) {
			sender.sendMessage(ChatColor.YELLOW + "Usage: /gang ability list");
			return true;
		}

		/*if (!plugin.getCommands().hasPermission(sender, "prisongang.list"))
			return true;*/

		String fullString = "";

		for (int i = 0; i < GangAbility.values().length; i++) {
			GangAbility abi = GangAbility.values()[i];
			if (i == (GangAbility.values().length - 1)) {
				fullString = fullString + abi.name().toLowerCase();
			} else {
				fullString = fullString + abi.name().toLowerCase() + ", ";
			}
		}

		sender.sendMessage(ChatColor.GOLD + "------[Abilities]------");
		sender.sendMessage(ChatColor.BLUE + fullString);

		return true;
	}
}
