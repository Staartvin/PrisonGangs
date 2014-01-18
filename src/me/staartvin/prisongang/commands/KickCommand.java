package me.staartvin.prisongang.commands;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.permissions.GangAbility;
import me.staartvin.prisongang.playerdata.PlayerData;
import me.staartvin.prisongang.translation.Lang;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand implements CommandExecutor {

	private PrisonGang plugin;

	public KickCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 2) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang kick <player>");
			return true;
		}

		PlayerData player;
		
		String target = args[1];
		
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

		/*if (!plugin.getCommands().hasPermission(sender, "prisongang.kick"))
			return true;*/
		
		if (!plugin.getPermissionsManager().hasAbility(sender, GangAbility.KICK)) {
			sender.sendMessage(Lang.NOT_AUTHORISED.getConfigValue(null));
			return true;
		}

		if (target.equalsIgnoreCase(gang.getLeader())) {
			sender.sendMessage(Lang.CANNOT_KICK_LEADER.getConfigValue(null));
			return true;
		}
		
		if (sender.getName().equalsIgnoreCase(target)) {
			sender.sendMessage(Lang.CANNOT_KICK_YOURSELF.getConfigValue(null));
			return true;
		}
		
		OfflinePlayer offPlayer = plugin.getServer().getOfflinePlayer(target);
		
		// Targeted player is online
		if (offPlayer.getPlayer() != null) {
			target = offPlayer.getPlayer().getName();
		} else {
			target = offPlayer.getName();
		}
		
		PlayerData oPlayer = plugin.getPlayerDataHandler().getPlayerData(target, true);
		
		if (oPlayer == null) {
			sender.sendMessage(Lang.PLAYER_DOES_NOT_EXIST.getConfigValue(new String[] {target}));
			return true;
		}
		
		if (!oPlayer.isInGang() || !oPlayer.getGangName().equalsIgnoreCase(gang.getGangName())) {
			sender.sendMessage(Lang.PLAYER_IS_NOT_IN_YOUR_GANG.getConfigValue(new String[] {oPlayer.getGangName()}));
			return true;
		}
		
		// Remove member
		gang.removeMember(target);
		
		// Remove player data
		oPlayer.setGangName(null);
		
		// Reset abilities
		oPlayer.resetAbilities();
		
		// Reset rank name
		oPlayer.setRankName(null);
		
		// Send sender a message
		sender.sendMessage(Lang.KICKED_PLAYER_OUT_OF_GANG.getConfigValue(new String[] {oPlayer.getGangName(), gang.getGangName()}));
		
		// Notify gang members
		gang.broadcastMessage(Lang.BROADCAST_KICK_MEMBER.getConfigValue(new String[] {oPlayer.getPlayerName()}));
		
		// Try to send kicked player a message
		if (offPlayer.getPlayer() != null) {
			offPlayer.getPlayer().sendMessage(Lang.YOU_GOT_KICKED.getConfigValue(new String[] {gang.getGangName(), sender.getName()}));
		}

		return true;
	}
}
