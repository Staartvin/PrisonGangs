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

public class GangBroadcastCommand implements CommandExecutor {

	private PrisonGang plugin;

	public GangBroadcastCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 2) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang broadcast <message>");
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(Lang.ONLY_PLAYER_ACTIVITY.getConfigValue(null));
			return true;
		}

		String message = "";
		PlayerData player;

		// Fetch targeted gangName
		if (args.length > 1) {
			List<String> newArgs = new ArrayList<String>();

			for (int i = 0; i < args.length; i++) {
				if (i == 0)
					continue;
				else {
					newArgs.add(args[i]);
				}
			}

			message = plugin.getCommands().getFullString(newArgs);
		}

		player = plugin.getPlayerDataHandler().getPlayerData(sender.getName(),
				false);

		if (!player.isInGang()) {
			sender.sendMessage(Lang.NOT_IN_A_GANG.getConfigValue(null));
			return true;
		}

		Gang gang = plugin.getGangHandler().getGang(player.getGangName());

		if (gang == null) {
			sender.sendMessage(Lang.GANG_DOES_NOT_EXIST.getConfigValue(null));
			return true;
		}

		if (!plugin.getPermissionsManager().hasAbility(sender,
				GangAbility.BROADCAST)) {
			sender.sendMessage(Lang.NOT_AUTHORISED.getConfigValue(null));
			return true;
		}
		
		if (message == null || message.trim().isEmpty() || message.trim().equals("")) {
			sender.sendMessage(Lang.INVALID_BROADCAST_MESSAGE.getConfigValue(null));
			return true;
		}

		// Broadcast message
		gang.broadcastMessage(message);

		return true;
	}
}
