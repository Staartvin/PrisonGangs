package me.staartvin.prisongang.commands;

import java.util.List;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.playerdata.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShowMembersCommand implements CommandExecutor {

	private PrisonGang plugin;

	public ShowMembersCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 1) {
			sender.sendMessage(ChatColor.YELLOW + "Usage: /gang members");
			return true;
		}

		PlayerData player;
		Gang gang;
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED
					+ "Only players can see the members list!");
			return true;
		}

		player = plugin.getPlayerDataHandler().getPlayerData(sender.getName());

		if (!player.isInGang()) {
			sender.sendMessage(ChatColor.RED + "You're not in a gang!");
			return true;
		}

		gang = plugin.getGangHandler().getGang(player.getGangName());

		if (gang == null) {
			sender.sendMessage(ChatColor.RED + "There is no such gang!");
			return true;
		}

		List<String> members = gang.getMembers();

		StringBuilder fullString = new StringBuilder("");

		for (int i = 0; i < members.size(); i++) {
			String member = members.get(i);
			if (i == (members.size() - 1)) {
				fullString.append(member);
			} else {
				fullString.append(member + ", ");
			}
		}
		
		sender.sendMessage(ChatColor.GOLD + "------[Members]------");
		sender.sendMessage(ChatColor.BLUE + fullString.toString());	
		
		return true;
	}
}
