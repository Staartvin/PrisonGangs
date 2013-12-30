package me.staartvin.prisongang.commands;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.playerdata.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisbandCommand implements CommandExecutor {

	private PrisonGang plugin;

	public DisbandCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 1) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang disband");
			return true;
		}

		PlayerData player;
		
		Gang gang;


		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You are not in a gang!");
			return true;
		}

		player = plugin.getPlayerDataHandler().getPlayerData(sender.getName());

		if (!player.isInGang()) {
			sender.sendMessage(ChatColor.RED + "You're not in a gang!");
			return true;
		}
		
		gang = plugin.getGangHandler().getGang(player.getGangName());
		
		if (gang == null) {
			sender.sendMessage(ChatColor.RED + "Your gang doesn't exist?!");
			return true;
		}

		/*if (!plugin.getCommands().hasPermission(sender, "prisongang.disband"))
			return true;*/

		if (!sender.getName().equalsIgnoreCase(gang.getLeader())) {
			sender.sendMessage(ChatColor.RED + "Only a leader can disband a gang.");
			return true;
		}
		
		// Remove player data
		player.setGangName(null);
		
		// Remove rank
		player.setRankName(null);
		
		// Reset abilities
		player.resetAbilities();

		// Notice everyone
		plugin.getServer().broadcastMessage(ChatColor.YELLOW + "Gang '" + gang.getGangName() + "' was disbanded!");

		// Remove gang
		plugin.getGangHandler().deleteGang(gang.getGangName());

		return true;
	}
}
