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

public class AbilityRemoveCommand implements CommandExecutor {

	private PrisonGang plugin;

	public AbilityRemoveCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 4) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang ability remove <player> <ability>");
			return true;
		}

		PlayerData player;	
		Gang gang;

		String target = args[2];

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can remove abilities!");
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
		
		if (!plugin.getPermissionsManager().hasAbility(sender, GangAbility.REMOVE_ABILITY)) {
			sender.sendMessage(ChatColor.RED + "You are not authorised to remove abilities!");
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
		
		if (gang.getLeader().equalsIgnoreCase(targetPlayer.getName())) {
			sender.sendMessage(ChatColor.RED + "You cannot remove abilities from the leader!");
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
		
		if (!plugin.getPermissionsManager().hasAbility(targetPlayer, ability)) {
			sender.sendMessage(ChatColor.RED + "That player doesn't have that ability!");
			return true;
		}
		
		targetData.addAbility(ability);
		
		
		// Notify both players
		sender.sendMessage(ChatColor.GOLD + targetPlayer.getName() + ChatColor.GREEN + " doesn't have the ability '" + ability.name() + "' anymore!");
		targetPlayer.sendMessage(ChatColor.GREEN + "You lost the ability '" + ability.name() + "'!");
		
		return true;
	}
}
