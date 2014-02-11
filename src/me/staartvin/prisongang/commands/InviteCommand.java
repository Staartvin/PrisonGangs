package me.staartvin.prisongang.commands;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.permissions.GangAbility;
import me.staartvin.prisongang.playerdata.PlayerData;
import me.staartvin.prisongang.translation.Lang;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InviteCommand implements CommandExecutor {

	private PrisonGang plugin;

	public InviteCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 2) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang invite <player>");
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

		if (!plugin.getPermissionsManager().hasAbility(sender, GangAbility.INVITE_PLAYERS)) {
			sender.sendMessage(Lang.NOT_AUTHORISED.getConfigValue(null));
			return true;
		}

		// Check whether the gang is private
		if (!gang.isPrivate()) {
			sender.sendMessage(Lang.NOT_A_PRIVATE_GANG_ANYONE_CAN_JOIN.getConfigValue(null));
			return true;
		}
		
		Player target = plugin.getServer().getPlayer(args[1]);
		
		// Check if the player is online
		if (target == null) {
			sender.sendMessage(Lang.PLAYER_NOT_ONLINE.getConfigValue(new String[] {args[1]}));
			return true;
		}
		
		// Target is already invited
		if (gang.isInvited(target.getName())) {
			sender.sendMessage(Lang.PLAYER_ALREADY_INVITED.getConfigValue(new String[] {target.getName()}));
			return true;
		}
		
		if (plugin.getPlayerDataHandler().isGangPartner((Player) sender, target)) {
			sender.sendMessage(Lang.PLAYER_ALREADY_IN_GANG.getConfigValue(new String[] {target.getName()}));
			return true;
		}
		
		gang.invitePlayer(target.getName());
		
		// Notify player
		sender.sendMessage(Lang.INVITED_PLAYER_TO_GANG.getConfigValue(new String[] {target.getName()}));
		
		// Notify invitee
		target.sendMessage(Lang.YOU_ARE_INVITED.getConfigValue(new String[] {sender.getName(), gang.getGangName()}));
		
		return true;
	}
}
