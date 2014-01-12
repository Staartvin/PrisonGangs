package me.staartvin.prisongang.commands;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.playerdata.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CallVoteCommand implements CommandExecutor {

	private PrisonGang plugin;

	public CallVoteCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length > 1) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang callvote");
			return true;
		}

		PlayerData player;
		
		Gang gang;

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can call a vote!");
			return true;
		}

		player = plugin.getPlayerDataHandler().getPlayerData(sender.getName(), false);

		if (!player.isInGang()) {
			sender.sendMessage(ChatColor.RED + "You're not in a gang!");
			return true;
		}
		
		gang = plugin.getGangHandler().getGang(player.getGangName());
		
		if (gang == null) {
			sender.sendMessage(ChatColor.RED + "There is no such gang!");
			return true;
		}

		// Check if you can vote atm.
		if (gang.isVoteInProgress()) {
			sender.sendMessage(ChatColor.RED + "There is already an election running now!");
			return true;
		}
		
		gang.startElection();
		
		// Notify player
		//sender.sendMessage(ChatColor.GREEN + "You have voted for this election!");
		
		// Broadcast message in gang
		gang.broadcastMessage(sender.getName() + " started an election for a new leader!");
		
		return true;
	}
}
