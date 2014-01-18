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

public class StatusCommand implements CommandExecutor {

	private PrisonGang plugin;

	public StatusCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 2) {
			sender.sendMessage(ChatColor.YELLOW
					+ "Usage: /gang ally/enemy/neutral <gang name>");
			return true;
		}

		String status = args[0];
		PlayerData player;
		Gang gang;
		Gang targetGang = null;

		// Fetch title
		if (args.length > 1) {
			List<String> newArgs = new ArrayList<String>();

			for (int i = 1; i < args.length; i++) {
				newArgs.add(args[i]);
			}

			targetGang = plugin.getGangHandler().getGang(
					plugin.getCommands().getFullString(newArgs));
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage(Lang.ONLY_PLAYER_ACTIVITY.getConfigValue(null));
			return true;
		}

		player = plugin.getPlayerDataHandler().getPlayerData(sender.getName(),
				false);

		if (targetGang == null) {
			sender.sendMessage(Lang.GANG_DOES_NOT_EXIST.getConfigValue(null));
			return true;
		}

		if (!player.isInGang()) {
			sender.sendMessage(Lang.NOT_IN_A_GANG.getConfigValue(null));
			return true;
		}

		gang = plugin.getGangHandler().getGang(player.getGangName());

		/*if (!plugin.getCommands().hasPermission(sender, "prisongang.status")) {
			return true;
		}*/

		if (!plugin.getPermissionsManager().hasAbility(sender,
				GangAbility.CHANGE_STATUS)) {
			sender.sendMessage(Lang.NOT_AUTHORISED.getConfigValue(null));
			return true;
		}

		if (gang == null) {
			sender.sendMessage(Lang.GANG_DOES_NOT_EXIST.getConfigValue(null));
			return true;
		}

		if (gang.getGangName().equals(targetGang.getGangName())) {
			sender.sendMessage(Lang.CANNOT_TARGET_OWN_GANG.getConfigValue(null));
			return true;
		}

		if (status.equalsIgnoreCase("neutral")) {
			// Not enemies nor allies
			if (gang.isNeutral(targetGang.getGangName())) {
				sender.sendMessage(Lang.ALREADY_STATUS
						.getConfigValue(new String[] {
								targetGang.getGangName(), "neutral" }));
				return true;
			} else {

				// Remove possible ally request.
				targetGang.removeAllyRequest(gang.getGangName());

				// Remove possible ally request from this gang
				gang.removeAllyRequest(targetGang.getGangName());

				// Remove ally and enemy from other gang
				targetGang.removeAlly(gang.getGangName());
				targetGang.removeEnemy(gang.getGangName());

				// Remove ally and enemy from home gang
				gang.removeAlly(targetGang.getGangName());
				gang.removeEnemy(targetGang.getGangName());

				sender.sendMessage(Lang.CHANGED_STATUS
						.getConfigValue(new String[] {
								targetGang.getGangName(), "neutral" }));
			}
		} else if (status.equalsIgnoreCase("ally")) {
			// Not enemies nor allies
			if (gang.isAlly(targetGang.getGangName())) {
				sender.sendMessage(Lang.ALREADY_STATUS
						.getConfigValue(new String[] {
								targetGang.getGangName(), "allies" }));
				return true;
			} else {

				// Check if target gang has already sent an ally request
				if (!gang.hasAllyRequest(targetGang.getGangName())) {

					// First check if a request was sent already
					if (targetGang.hasAllyRequest(gang.getGangName())) {
						// This gang already has sent a request
						sender.sendMessage(Lang.GANG_ALREADY_SENT_ALLY_REQUEST
								.getConfigValue(new String[] { targetGang
										.getGangName() }));
						return true;
					} else {
						// This gang has not sent a request to the target gang yet -> send them a request.
						targetGang.addAllyRequest(gang.getGangName());

						sender.sendMessage(Lang.SENT_GANG_ALLY_REQUEST
								.getConfigValue(new String[] { targetGang
										.getGangName() }));
						
						// Notify gang
						targetGang.broadcastMessage(Lang.BROADCAST_NEW_ALLY_REQUEST.getConfigValue(new String[] {gang.getGangName()} ));
						return true;
					}
				} else {
					// This gang got an ally request from the target gang -> now become allies

					// Add ally and remove enemy from other gang
					targetGang.addAlly(gang.getGangName());
					targetGang.removeEnemy(gang.getGangName());

					// Add ally and remove enemy from home gang
					gang.addAlly(targetGang.getGangName());
					gang.removeEnemy(targetGang.getGangName());

					sender.sendMessage(Lang.CHANGED_STATUS
							.getConfigValue(new String[] {
									targetGang.getGangName(), "allies" }));
				}
			}
		} else if (status.equalsIgnoreCase("enemy")) {
			// Not enemies nor allies
			if (gang.isEnemy(targetGang.getGangName())) {
				sender.sendMessage(Lang.ALREADY_STATUS
						.getConfigValue(new String[] {
								targetGang.getGangName(), "l" }));
				return true;
			} else {
				// Remove possible ally request.
				targetGang.removeAllyRequest(gang.getGangName());

				// Remove possible ally request from this gang
				gang.removeAllyRequest(targetGang.getGangName());

				// Remove ally and add enemy from other gang
				targetGang.removeAlly(gang.getGangName());
				targetGang.addEnemy(gang.getGangName());

				// Remove ally and add enemy from home gang
				gang.removeAlly(targetGang.getGangName());
				gang.addEnemy(targetGang.getGangName());

				sender.sendMessage(Lang.CHANGED_STATUS
						.getConfigValue(new String[] {
								targetGang.getGangName(), "enemies" }));
			}
		} else {
			sender.sendMessage(ChatColor.RED
					+ "You can only set gangs as neutral, ally or enemy.");
		}
		
		// Send message for other gangs
		
		// Change ally to allies
		gang.broadcastMessage(Lang.BROADCAST_NEW_STATUS.getConfigValue(new String[] {targetGang.getGangName(), status.replace("y", "ies")}));
		
		// Change enemy to enemies
		targetGang.broadcastMessage(Lang.BROADCAST_NEW_STATUS.getConfigValue(new String[] {gang.getGangName(), status.replace("y", "ies")}));
		
		return true;
	}
}
