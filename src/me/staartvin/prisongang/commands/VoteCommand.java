package me.staartvin.prisongang.commands;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.playerdata.PlayerData;
import me.staartvin.prisongang.translation.Lang;

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
			sender.sendMessage(Lang.ONLY_PLAYER_ACTIVITY.getConfigValue(null));
			return true;
		}

		player = plugin.getPlayerDataHandler().getPlayerData(sender.getName(), false);

		if (!player.isInGang()) {
			sender.sendMessage(Lang.NOT_IN_A_GANG.getConfigValue(null));
			return true;
		}
		
		gang = plugin.getGangHandler().getGang(player.getGangName());
		
		if (gang == null) {
			sender.sendMessage(Lang.GANG_DOES_NOT_EXIST.getConfigValue(null));
			return true;
		}

		// Check if you can vote atm.
		if (!gang.isVoteInProgress()) {
			sender.sendMessage(Lang.NO_ELECTION_RUNNING.getConfigValue(null));
			return true;
		}
		
		// Check if player has already voted
		if (gang.hasVoted(sender.getName())) {
			sender.sendMessage(Lang.YOU_ALREADY_VOTED.getConfigValue(null));
			return true;
		}
		
		// Let player vote
		gang.vote(sender.getName());
		
		// Notify player
		sender.sendMessage(Lang.YOU_VOTED.getConfigValue(null));
		
		// Broadcast message in gang
		gang.broadcastMessage(Lang.PLAYER_VOTED_BROADCAST.getConfigValue(new String[] {sender.getName()}));
		
		return true;
	}
}
