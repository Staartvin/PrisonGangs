package me.staartvin.prisongang.commands;

import me.staartvin.prisongang.PrisonGang;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandExecutor {

	@SuppressWarnings("unused")
	private PrisonGang plugin;

	public HelpCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length == 1) {
			showHelpPages(sender, 1);
		} else {
			int page = 1;
			try {
				page = Integer.parseInt(args[1]);
			} catch (final Exception e) {
				sender.sendMessage(ChatColor.RED + "Invalid page number!");
				return true;
			}
			showHelpPages(sender, page);
		}

		return true;
	}

	private void showHelpPages(CommandSender sender, int page) {
		final int maxPages = 4;
		if (page == 2) {
			sender.sendMessage(ChatColor.GREEN + "-- PrisonGang Commands --");
			sender.sendMessage(ChatColor.AQUA + "/gang set <arg> <value> "
					+ ChatColor.GRAY + "- Set an option to <value>");
			sender.sendMessage(ChatColor.AQUA + "/gang set list "
					+ ChatColor.GRAY + "- Shows a list of options to set");
			sender.sendMessage(ChatColor.AQUA + "/gang title <player> <title> "
					+ ChatColor.GRAY + "- Set the title of a player");
			sender.sendMessage(ChatColor.AQUA + "/gang enemy <gang> "
					+ ChatColor.GRAY + "- Make a gang an enemy");
			sender.sendMessage(ChatColor.AQUA + "/gang ally <gang> "
					+ ChatColor.GRAY + "- Make a gang an ally");
			sender.sendMessage(ChatColor.AQUA + "/gang neutral <gang> "
					+ ChatColor.GRAY + "- Make a gang neutral");
			sender.sendMessage(ChatColor.AQUA + "/gang chat " + ChatColor.GRAY
					+ "- Switch between chat modes");
			sender.sendMessage(ChatColor.AQUA + "/gang ability add <player> <ability> "
					+ ChatColor.GRAY + "- Give a player an ability");
			sender.sendMessage(ChatColor.BLUE + "Page 2 of " + maxPages);
		} else if (page == 3) {
			sender.sendMessage(ChatColor.GREEN + "-- PrisonGang Commands --");
			sender.sendMessage(ChatColor.AQUA + "/gang ability remove <player> <ability> "
					+ ChatColor.GRAY + "- Remove an ability from a player");
			sender.sendMessage(ChatColor.AQUA + "/gang members " + ChatColor.GRAY
					+ "- Shows all gang's members");
			sender.sendMessage(ChatColor.AQUA + "/gang allies " + ChatColor.GRAY
					+ "- Shows all gang's enemies");
			sender.sendMessage(ChatColor.AQUA + "/gang enemies " + ChatColor.GRAY
					+ "- Shows all gang's allies");
			sender.sendMessage(ChatColor.AQUA + "/gang invite <player> " + ChatColor.GRAY
					+ "- Invite a player to the gang");
			sender.sendMessage(ChatColor.BLUE + "Page 3 of " + maxPages);
		} else if (page == 4) {
			sender.sendMessage(ChatColor.GREEN + "-- PrisonGang Commands --");
			sender.sendMessage(ChatColor.AQUA + "/gang admin remove <gang> "
					+ ChatColor.GRAY + "- Remove a gang completely.");
			sender.sendMessage(ChatColor.AQUA + "/gang admin save "
					+ ChatColor.GRAY + "- Request a manual save of all files.");
			sender.sendMessage(ChatColor.BLUE + "Page 4 of " + maxPages);
		} else {
			sender.sendMessage(ChatColor.GREEN + "-- PrisonGang Commands --");
			sender.sendMessage(ChatColor.AQUA + "/gang help (page) "
					+ ChatColor.GRAY + "- Show a list of commands");
			sender.sendMessage(ChatColor.AQUA + "/gang info (gang) "
					+ ChatColor.GRAY + "- View info about a gang");
			sender.sendMessage(ChatColor.AQUA + "/gang create <gang name> "
					+ ChatColor.GRAY + "- Create a new gang");
			sender.sendMessage(ChatColor.AQUA + "/gang disband "
					+ ChatColor.GRAY + "- Disband the gang");
			sender.sendMessage(ChatColor.AQUA + "/gang join <gang> "
					+ ChatColor.GRAY + "- Join a gang");
			sender.sendMessage(ChatColor.AQUA + "/gang leave " + ChatColor.GRAY
					+ "- Leave your current gang");
			sender.sendMessage(ChatColor.AQUA + "/gang kick <player> "
					+ ChatColor.GRAY + "- Kick a player from the gang");
			sender.sendMessage(ChatColor.AQUA + "/gang list " + ChatColor.GRAY
					+ "- Get a list of all current gangs");
			sender.sendMessage(ChatColor.BLUE + "Page 1 of " + maxPages);
		}
	}
}
