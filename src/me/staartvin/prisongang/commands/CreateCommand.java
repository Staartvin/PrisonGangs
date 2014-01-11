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

public class CreateCommand implements CommandExecutor {

	private PrisonGang plugin;
	
	private String[] codes = {"&0", "&1", "&2", "&3", "&4", "&5", "&6", "&7", "&8", "&9", "&a", "&b", "&c", "&d", "&e", "&f", "&m", "&n", "&l", "&k", "&o"};

	public CreateCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 2) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang create <gang name>");
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can create gangs!");
			return true;
		}

		if (!plugin.getCommands().hasPermission(sender, "prisongang.create"))
			return true;

		List<String> newArgs = new ArrayList<String>();

		for (int i = 0; i < args.length; i++) {
			if (i == 0)
				continue;
			else {
				newArgs.add(args[i]);
			}
		}
		
		// Find playerdata
		PlayerData data = plugin.getPlayerDataHandler().getPlayerData(sender.getName());

		String gangName = plugin.getCommands().getFullString(newArgs);

		Gang gang = plugin.getGangHandler().getGang(gangName);

		if (gang != null) {
			sender.sendMessage(ChatColor.RED
					+ "There already is a gang with that name!");
			return true;
		}
		
		if (containsColours(gangName)) {
			sender.sendMessage(ChatColor.RED + "You cannot use formatting codes in the name!");
			return true;
		}

		gang = plugin.getGangHandler().getGangByLeader(sender.getName());

		if (gang != null) {
			sender.sendMessage(ChatColor.RED
					+ "You already have another gang called '"
					+ gang.getGangName() + "'!");
			return true;
		}
		
		if (data.isInGang()) {
			sender.sendMessage(ChatColor.RED + "You are already in a gang!");
			return true;
		}

		gang = plugin.getGangHandler().createNewGang(gangName, sender.getName());
		
		// Add the leader as member
		gang.addMember(sender.getName());
		gang.setInfo("leader", sender.getName());
		
		// Set gang and rank name
		data.setGangName(gang.getGangName());
		data.setRankName(gang.getLeadersTitle());

		sender.sendMessage(ChatColor.GREEN + "Your new gang '"
				+ ChatColor.YELLOW + gangName + ChatColor.GREEN
				+ "' has been created!");
		
		return true;
	}
	
	private boolean containsColours(String text) {
		text = text.toLowerCase();
		
		for (String code: codes) {
			if (text.contains(code)) {
				return true;
			}
		}
		
		return false;
	}
}
