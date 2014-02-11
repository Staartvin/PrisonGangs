package me.staartvin.prisongang.commands;

import java.util.ArrayList;
import java.util.List;

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

public class TitleCommand implements CommandExecutor {

	private PrisonGang plugin;

	public TitleCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 3) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang title <player> <title>");
			return true;
		}

		String title = null;
		PlayerData player;
		PlayerData targetPlayer;
		
		Gang gang;

		// Fetch title
		if (args.length > 1) {
			List<String> newArgs = new ArrayList<String>();

			for (int i = 2; i < args.length; i++) {
					newArgs.add(args[i]);
			}

			title = plugin.getCommands().getFullString(newArgs);
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(Lang.ONLY_PLAYER_ACTIVITY.getConfigValue(null));
			return true;
		}

		player = plugin.getPlayerDataHandler().getPlayerData(sender.getName(), false);
		
		if (plugin.getPlayerDataHandler().getPlayerData(args[1], true) == null) {
			sender.sendMessage(ChatColor.RED + "Invalid player given!");
			return true;
		}
		
		targetPlayer = plugin.getPlayerDataHandler().getPlayerData(args[1], true);

		if (!player.isInGang()) {
			sender.sendMessage(Lang.NOT_IN_A_GANG.getConfigValue(null));
			return true;
		}
		
		gang = plugin.getGangHandler().getGang(player.getGangName());
		
		/*if (!plugin.getCommands().hasPermission(sender, "prisongang.title")) {
			return true;
		}*/
		
		if (!plugin.getPermissionsManager().hasAbility(sender, GangAbility.CHANGE_TITLE)) {
			sender.sendMessage(Lang.NOT_AUTHORISED.getConfigValue(null));
			return true;
		}
		
		if (gang == null) {
			sender.sendMessage(Lang.GANG_DOES_NOT_EXIST.getConfigValue(null));
			return true;
		}

		if (!gang.getMembers().contains(targetPlayer.getPlayerName())) {
			sender.sendMessage(Lang.PLAYER_IS_NOT_IN_YOUR_GANG.getConfigValue(new String[] {targetPlayer.getPlayerName()}));
			return true;
		}
		
		// Title is leaders title and player is not leader
		if (title.equalsIgnoreCase(gang.getLeadersTitle()) && !gang.getLeader().equalsIgnoreCase(player.getPlayerName())) {
			sender.sendMessage(Lang.CANNOT_USE_LEADERS_TITLE.getConfigValue(null));
			return true;
		}
		
		if (gang.getLeader().equals(targetPlayer.getPlayerName())) {
			sender.sendMessage(Lang.CANNOT_CHANGE_TITLE_LEADER.getConfigValue(null));
			return true;
		}
		
		targetPlayer.setRankName(title);

		sender.sendMessage(Lang.CHANGED_TITLE_OF_PLAYER.getConfigValue(new String[] {targetPlayer.getPlayerName(), title}));
		
		return true;
	}
}
