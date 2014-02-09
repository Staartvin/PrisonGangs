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
			sender.sendMessage(Lang.ONLY_PLAYER_ACTIVITY.getConfigValue(null));
			return true;
		}

		player = plugin.getPlayerDataHandler().getPlayerData(sender.getName(), false);

		Player targetPlayer = plugin.getServer().getPlayer(target);
		
		if (targetPlayer == null) {
			sender.sendMessage(Lang.PLAYER_NOT_ONLINE.getConfigValue(new String[] {target} ));
			return true;
		}

		PlayerData targetData = plugin.getPlayerDataHandler().getPlayerData(targetPlayer.getName(), false);
		
		if (!player.isInGang()) {
			sender.sendMessage(Lang.NOT_IN_A_GANG.getConfigValue(null));
			return true;
		}
		
		
		gang = plugin.getGangHandler().getGang(player.getGangName());
		
		/*if (!plugin.getCommands().hasPermission(sender, "prisongang.status")) {
			return true;
		}*/
		
		if (!plugin.getPermissionsManager().hasAbility(sender, GangAbility.ADD_ABILITY)) {
			sender.sendMessage(Lang.NOT_AUTHORISED.getConfigValue(null));
			return true;
		}
		
		if (gang == null) {
			sender.sendMessage(Lang.GANG_DOES_NOT_EXIST.getConfigValue(null));
			return true;
		}
		
		if (!plugin.getPlayerDataHandler().isGangPartner((Player) sender, targetPlayer)) {
			sender.sendMessage(Lang.PLAYER_IS_NOT_IN_SAME_GANG.getConfigValue(new String[] {targetPlayer.getName()}));
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
			sender.sendMessage(Lang.PLAYER_ALREADY_HAS_ABILITY.getConfigValue(new String[] {targetPlayer.getName()}));
			return true;
		}
		
		targetData.addAbility(ability);
		
		
		// Notify both players
		sender.sendMessage(Lang.GAVE_ABILITY_TO_PLAYER.getConfigValue(new String[] {targetPlayer.getName(), ability.name().toLowerCase()}));
		targetPlayer.sendMessage(Lang.GOT_ABILITY_FROM_PLAYER.getConfigValue(new String[] {ability.name().toLowerCase(), sender.getName()}));
		
		return true;
	}
}
