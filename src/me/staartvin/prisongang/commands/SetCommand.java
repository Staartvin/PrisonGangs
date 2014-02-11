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

public class SetCommand implements CommandExecutor {

	private PrisonGang plugin;

	public SetCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 3) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang set <arg> <value>");
			return true;
		}

		String argument = args[1];
		PlayerData player;
		Gang gang;

		String value = null;

		// Fetch targeted gangName
		if (args.length > 1) {
			List<String> newArgs = new ArrayList<String>();

			for (int i = 2; i < args.length; i++) {
				newArgs.add(args[i]);
			}

			value = plugin.getCommands().getFullString(newArgs);
		}

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

		/*if (!plugin.getCommands().hasPermission(sender, "prisongang.set"))
			return true;*/

		boolean isValidArg = false;
		
		for (String arg: Commands.allowedSetArgs) {
			if (arg.equalsIgnoreCase(argument)) {
				argument = arg;
				isValidArg = true;
			}
		}
		
		if (!isValidArg) {
			sender.sendMessage(ChatColor.RED + argument + " is not a valid argument.");
			sender.sendMessage(ChatColor.YELLOW + "Use /gang set list to get a list of valid arguments.");
			return true;
		}
		
		// Set the description
		if (argument.equals("desc")) {
			
			if (!plugin.getPermissionsManager().hasAbility(sender, GangAbility.CHANGE_DESC)) {
				sender.sendMessage(Lang.NOT_AUTHORISED.getConfigValue(null));
				return true;
			}
			
			gang.setInfo("description", value);
			
			sender.sendMessage(ChatColor.GREEN + "You've changed the description.");
			return true;
		} else if (argument.equals("leader")) {
			// Set the leader of the group
			
			if (!plugin.getPermissionsManager().hasAbility(sender, GangAbility.SET_LEADER)) {
				sender.sendMessage(Lang.NOT_AUTHORISED.getConfigValue(null));
				return true;
			}
			
			/*if (!gang.getLeader().equalsIgnoreCase(player.getPlayerName())) {
				sender.sendMessage(ChatColor.RED + "Only leaders can change who's boss.");
				return true;
			}*/
			
			Player target = plugin.getServer().getPlayer(value);
			
			if (target == null) {
				sender.sendMessage(Lang.PLAYER_NOT_ONLINE.getConfigValue(new String[] {value}));
				return true;
			}
			
			value = target.getName();
			
			if (gang.getLeader().equalsIgnoreCase(value)) {
				sender.sendMessage(ChatColor.RED + value + " already is the leader!");
				return true;
			}
			
			// Remove leaders title from old leader
			plugin.getPlayerDataHandler().getPlayerData(gang.getLeader(), true).setRankName(null);
			
			gang.setInfo("leader", value);
			
			plugin.getPlayerDataHandler().getPlayerData(value, false).setRankName(gang.getLeadersTitle());
			
			sender.sendMessage(ChatColor.GREEN + value + " is now the new leader!");
			
			plugin.getServer().broadcastMessage(ChatColor.GOLD + value + ChatColor.GREEN + " is now the new leader of " + ChatColor.GOLD + gang.getGangName());
			
			return true;
		} else if (argument.equals("private")) {
			// Set to private mode or not
			
			if (!plugin.getPermissionsManager().hasAbility(sender, GangAbility.SET_PRIVATE)) {
				sender.sendMessage(Lang.NOT_AUTHORISED.getConfigValue(null));
				return true;
			}
			
			boolean bool = Boolean.parseBoolean(value);
			
			// Set private value in the gang
			gang.setInfo("private", bool + "");
			
			sender.sendMessage(Lang.CHANGED_PRIVATE_MODE.getConfigValue(new String[] {bool + ""}));
		} else {
			sender.sendMessage(ChatColor.RED + "UNKNOWN ARGUMENT!");
		}

		return true;
	}
}
