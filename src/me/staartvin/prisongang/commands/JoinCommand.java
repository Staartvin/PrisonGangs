package me.staartvin.prisongang.commands;

import java.util.ArrayList;
import java.util.List;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.playerdata.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand implements CommandExecutor {

	private PrisonGang plugin;

	public JoinCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 2) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang join <gang name>");
			return true;
		}

		String gangName = null;
		PlayerData player;
		
		Gang gang;

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
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can join a gang!");
			return true;
		}

		player = plugin.getPlayerDataHandler().getPlayerData(sender.getName());

		if (player.isInGang()) {
			sender.sendMessage(ChatColor.RED + "You're already in a gang!");
			return true;
		}
		
		gang = plugin.getGangHandler().getGang(gangName);
		
		if (gang == null) {
			sender.sendMessage(ChatColor.RED + "There is no such gang!");
			return true;
		}

		if (!plugin.getCommands().hasPermission(sender, "prisongang.join"))
			return true;

		// Check whether the gang is private and if so if the player is invited
		if (gang.isPrivate()) {
			if (!gang.isInvited(sender.getName())) {
				sender.sendMessage(ChatColor.RED + "This is a private gang.");
				sender.sendMessage(ChatColor.YELLOW + "You need to be invited first!");
				return true;	
			}
			
			// Remove from the invited list because the player now joined the gang
			gang.unInvitePlayer(sender.getName());
		}
		
		// Add player to gang
		gang.addMember(sender.getName());
		
		player.setGangName(gang.getGangName());
		
		sender.sendMessage(ChatColor.GREEN + "You successfully joined '" + gang.getGangName() + "'!");
		
		return true;
	}
}
