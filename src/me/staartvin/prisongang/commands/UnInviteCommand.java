package me.staartvin.prisongang.commands;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.permissions.GangAbility;
import me.staartvin.prisongang.playerdata.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnInviteCommand implements CommandExecutor {

	private PrisonGang plugin;

	public UnInviteCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 2) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang uninvite <player>");
			return true;
		}

		PlayerData player;
		
		Gang gang;

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can uninvite players!");
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

		if (!plugin.getPermissionsManager().hasAbility(sender, GangAbility.UNINVITE_PLAYERS)) {
			sender.sendMessage(ChatColor.RED + "You are not authorised to uninvite others!");
			return true;
		}

		// Check whether the gang is private
		if (!gang.isPrivate()) {
			sender.sendMessage(ChatColor.RED + "This is not a private gang and therefore anybody can join!");
			return true;
		}
		
		Player target = plugin.getServer().getPlayer(args[1]);
		
		// Check if the player is online
		if (target == null) {
			sender.sendMessage(ChatColor.RED + args[1] + " is not online!");
			return true;
		}
		
		// Target is already invited
		if (!gang.isInvited(target.getName())) {
			sender.sendMessage(ChatColor.RED + "This player is not invited!");
			return true;
		}
		
		gang.unInvitePlayer(target.getName());
		
		// Notify player
		sender.sendMessage(ChatColor.GREEN + target.getName() + " has been uninvited from the gang!");
		
		// Notify invitee
		target.sendMessage(ChatColor.GREEN + "You have been uninvited from " + ChatColor.GOLD + gang.getGangName() + ChatColor.GREEN + " by " + ChatColor.GOLD + sender.getName());
		
		return true;
	}
}
