package me.staartvin.prisongang.commands;

import java.util.HashMap;

import me.staartvin.prisongang.PrisonGang;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatSwitchCommand implements CommandExecutor {

	private PrisonGang plugin;

	public ChatSwitchCommand(PrisonGang instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(final CommandSender sender, Command command,
			String label, String[] args) {
		
		if (!plugin.getCommands().hasPermission(sender, "prisongang.chat.switch"))
			return true;
		
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can change chat mode");
			return true;
		}
		
		String currentMode = null;
		HashMap<String, String> chatMode = plugin.getCommands().chatMode;
		
		// If there is already a key, use that key as latest one
		if (plugin.getCommands().chatMode.containsKey(sender.getName())) {
			currentMode = plugin.getCommands().chatMode.get(sender.getName());
		} else {
			// There is no key specified, so default to global chat
			currentMode = "global";
		}
		
		String newMode = null;

		// switch from global -> ally only -> gang only
		if (currentMode.equals("global")) {
			newMode = "ally-only";
		} else if (currentMode.equals("ally-only")) {
			newMode = "gang-only";
		} else if (currentMode.equals("gang-only")) {
			newMode = "global";
		}
		
		chatMode.put(sender.getName(), newMode);
		sender.sendMessage(ChatColor.GREEN + "Chat mode has been changed to " + ChatColor.GOLD + newMode);
		
		return true;
	}
}
