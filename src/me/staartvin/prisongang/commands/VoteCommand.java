package me.staartvin.prisongang.commands;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.playerdata.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoteCommand implements CommandExecutor {

	private PrisonGang plugin;

	public VoteCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length > 1) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang vote");
			return true;
		}

		PlayerData player;
		
		Gang gang;

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can vote!");
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
		if (!gang.isVoteInProgress()) {
			sender.sendMessage(ChatColor.RED + "There is no election running now!");
			return true;
		}
		
		// Check if player has already voted
		if (gang.hasVoted(sender.getName())) {
			sender.sendMessage(ChatColor.RED + "You have already voted!");
			return true;
		}
		
		// Let player vote
		gang.vote(sender.getName());
		
		// Notify player
		sender.sendMessage(ChatColor.GREEN + "You have voted for this election!");
		
		// Broadcast message in gang
		gang.broadcastMessage(sender.getName() + " voted for a new leader!");
		
		return true;
	}
}
