package me.staartvin.prisongang.commands;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.permissions.GangAbility;
import me.staartvin.prisongang.playerdata.PlayerData;

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
			sender.sendMessage(ChatColor.RED + "You are not in a gang!");
			return true;
		}

		player = plugin.getPlayerDataHandler().getPlayerData(sender.getName(), false);

		if (!player.isInGang()) {
			sender.sendMessage(ChatColor.RED + "You're not in a gang!");
			return true;
		}
		
		gang = plugin.getGangHandler().getGang(player.getGangName());
		
		if (gang == null) {
			sender.sendMessage(ChatColor.RED + "Your gang doesn't exist?!");
			return true;
		}

		/*if (!plugin.getCommands().hasPermission(sender, "prisongang.kick"))
			return true;*/
		
		if (!plugin.getPermissionsManager().hasAbility(sender, GangAbility.KICK)) {
			sender.sendMessage(ChatColor.RED + "You are not authorised to kick anybody!");
			return true;
		}

		if (target.equalsIgnoreCase(gang.getLeader())) {
			sender.sendMessage(ChatColor.RED + "You cannot kick the leader.");
			return true;
		}
		
		if (sender.getName().equalsIgnoreCase(target)) {
			sender.sendMessage(ChatColor.RED + "You cannot kick yourself, try /gang leave instead.");
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
			sender.sendMessage(ChatColor.RED + "Player '" + target + "' does not exist.");
			return true;
		}
		
		if (!oPlayer.isInGang() || !oPlayer.getGangName().equalsIgnoreCase(gang.getGangName())) {
			sender.sendMessage(ChatColor.RED + target + " is not in your gang!");
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
		sender.sendMessage(ChatColor.GREEN + "You kicked '" + target + "' out of " + gang.getGangName() + "!");
		
		// Try to send kicked player a message
		if (offPlayer.getPlayer() != null) {
			offPlayer.getPlayer().sendMessage(ChatColor.RED + "You have been kicked from " + gang.getGangName());
		}

		return true;
	}
}
