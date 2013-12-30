package me.staartvin.prisongang.commands;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.playerdata.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand implements CommandExecutor {

	private PrisonGang plugin;

	public LeaveCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 1) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang leave");
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

		if (!plugin.getCommands().hasPermission(sender, "prisongang.leave"))
			return true;

		if (sender.getName().equalsIgnoreCase(gang.getLeader())) {
			sender.sendMessage(ChatColor.RED + "You cannot leave your gang! To delete your gang, do /gang disband.");
			return true;
		}
		
		// Remove member
		gang.removeMember(sender.getName());
		
		// Remove player data
		player.setGangName(null);
		
		// Reset abilities
		player.resetAbilities();
		
		sender.sendMessage(ChatColor.GREEN + "You successfully left '" + gang.getGangName() + "'!");
		

		return true;
	}
}
