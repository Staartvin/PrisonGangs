package me.staartvin.prisongang.commands;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.playerdata.PlayerData;
import me.staartvin.prisongang.translation.Lang;

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

		/*if (!plugin.getCommands().hasPermission(sender, "prisongang.disband"))
			return true;*/

		if (!sender.getName().equalsIgnoreCase(gang.getLeader())) {
			sender.sendMessage(Lang.ONLY_LEADER_CAN_DISBAND.getConfigValue(null));
			return true;
		}
		
		// Remove player data
		player.setGangName(null);
		
		// Remove rank
		player.setRankName(null);
		
		// Reset abilities
		player.resetAbilities();

		// Notice everyone
		plugin.getServer().broadcastMessage(Lang.GANG_DISBANDED.getConfigValue(new String[] {gang.getGangName()}));

		// Remove gang
		plugin.getGangHandler().deleteGang(gang.getGangName());

		return true;
	}
}
