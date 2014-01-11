package me.staartvin.prisongang.commands;

import java.util.List;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.playerdata.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShowAlliesCommand implements CommandExecutor {

	private PrisonGang plugin;

	public ShowAlliesCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length < 1) {
			sender.sendMessage(ChatColor.YELLOW + "Usage: /gang allies");
			return true;
		}

		PlayerData player;
		Gang gang;
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED
					+ "Only players can see the allies list!");
			return true;
		}

		player = plugin.getPlayerDataHandler().getPlayerData(sender.getName(), false);

		if (!player.isInGang()) {
			sender.sendMessage(ChatColor.RED + "You're not in a gang!");
			return true;
		}

		gang = plugin.getGangHandler().getGang(player.getGangName());

		if (gang == null) {
			sender.sendMessage(ChatColor.RED + "There is no such gang!");
			return true;
		}

		List<String> allies = gang.getAllies();

		StringBuilder fullString = new StringBuilder("");

		for (int i = 0; i < allies.size(); i++) {
			String ally = allies.get(i);
			if (i == (allies.size() - 1)) {
				fullString.append(ally);
			} else {
				fullString.append(ally + ", ");
			}
		}
		
		if (allies.size() > 0) {
			sender.sendMessage(ChatColor.GOLD + "------[Allies]------");
			sender.sendMessage(ChatColor.BLUE + fullString.toString());	
		} else {
			sender.sendMessage(ChatColor.BLUE + "You have no allies!");
		}	
		
		return true;
	}
}
