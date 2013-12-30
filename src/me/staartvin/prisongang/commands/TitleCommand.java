package me.staartvin.prisongang.commands;

import java.util.ArrayList;
import java.util.List;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.permissions.GangAbility;
import me.staartvin.prisongang.playerdata.PlayerData;

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
			sender.sendMessage(ChatColor.RED + "Only players can title other players!");
			return true;
		}

		player = plugin.getPlayerDataHandler().getPlayerData(sender.getName());
		
		if (plugin.getPlayerDataHandler().getPlayerData(args[1]) == null) {
			sender.sendMessage(ChatColor.RED + "Invalid player given!");
			return true;
		}
		
		targetPlayer = plugin.getPlayerDataHandler().getPlayerData(args[1]);

		if (!player.isInGang()) {
			sender.sendMessage(ChatColor.RED + "You're not in a gang!");
			return true;
		}
		
		gang = plugin.getGangHandler().getGang(player.getGangName());
		
		/*if (!plugin.getCommands().hasPermission(sender, "prisongang.title")) {
			return true;
		}*/
		
		if (!plugin.getPermissionsManager().hasAbility(sender, GangAbility.CHANGE_TITLE)) {
			sender.sendMessage(ChatColor.RED + "You are not authorised to change titles of members!");
			return true;
		}
		
		if (gang == null) {
			sender.sendMessage(ChatColor.RED + "There is no such gang!");
			return true;
		}

		if (!gang.getMembers().contains(targetPlayer.getPlayerName())) {
			sender.sendMessage(ChatColor.RED + "That player is not in your gang!");
			return true;
		}
		
		// Title is leaders title and player is not leader
		if (title.equalsIgnoreCase(gang.getLeadersTitle()) && !gang.getLeader().equalsIgnoreCase(player.getPlayerName())) {
			sender.sendMessage(ChatColor.RED + "You cannot use the leader's title as title!");
			return true;
		}
		
		if (gang.getLeader().equals(targetPlayer.getPlayerName())) {
			sender.sendMessage(ChatColor.RED + "You cannot change the title of the leader!");
			return true;
		}
		
		targetPlayer.setRankName(title);

		sender.sendMessage(ChatColor.GREEN + "You successfully set the title of " + targetPlayer.getPlayerName() + " to '" + ChatColor.GOLD + title + ChatColor.GREEN + "'.");
		

		return true;
	}
}
