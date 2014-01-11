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

public class AbilityAddCommand implements CommandExecutor {

	private PrisonGang plugin;

	public AbilityAddCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 4) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang ability add <player> <ability>");
			return true;
		}

		PlayerData player;	
		Gang gang;

		String target = args[2];

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can add abilities!");
			return true;
		}

		player = plugin.getPlayerDataHandler().getPlayerData(sender.getName(), false);

		Player targetPlayer = plugin.getServer().getPlayer(target);
		
		if (targetPlayer == null) {
			sender.sendMessage(ChatColor.RED + "That player is not online!");
			return true;
		}

		PlayerData targetData = plugin.getPlayerDataHandler().getPlayerData(targetPlayer.getName(), false);
		
		if (!player.isInGang()) {
			sender.sendMessage(ChatColor.RED + "You're not in a gang!");
			return true;
		}
		
		
		gang = plugin.getGangHandler().getGang(player.getGangName());
		
		/*if (!plugin.getCommands().hasPermission(sender, "prisongang.status")) {
			return true;
		}*/
		
		if (!plugin.getPermissionsManager().hasAbility(sender, GangAbility.ADD_ABILITY)) {
			sender.sendMessage(ChatColor.RED + "You are not authorised to add abilities!");
			return true;
		}
		
		if (gang == null) {
			sender.sendMessage(ChatColor.RED + "There is no such gang!");
			return true;
		}
		
		if (!plugin.getPlayerDataHandler().isGangPartner((Player) sender, targetPlayer)) {
			sender.sendMessage(ChatColor.RED + "That player is not in the same gang as you are.");
			return true;
		}

		GangAbility ability;
		
		try {
			ability = GangAbility.valueOf(args[3].toUpperCase().trim());	
		} catch (Exception e) {
			sender.sendMessage(ChatColor.RED + "Invalid ability.");
			sender.sendMessage(ChatColor.YELLOW + "Type '/gang ability list' for a list of abilities.");
			return true;
		}
		
		if (plugin.getPermissionsManager().hasAbility(targetPlayer, ability)) {
			sender.sendMessage(ChatColor.RED + "That player already has that ability!");
			return true;
		}
		
		targetData.addAbility(ability);
		
		
		// Notify both players
		sender.sendMessage(ChatColor.GOLD + targetPlayer.getName() + ChatColor.GREEN + " has now gotten the ability '" + ability.name() + "'!");
		targetPlayer.sendMessage(ChatColor.GREEN + "You got the ability '" + ability.name() + "'!");
		
		return true;
	}
}
