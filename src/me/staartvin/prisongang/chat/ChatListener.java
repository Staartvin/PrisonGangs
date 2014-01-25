package me.staartvin.prisongang.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.staartvin.prisongang.PrisonGang;
import me.staartvin.prisongang.gang.Gang;
import me.staartvin.prisongang.playerdata.PlayerData;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

	private PrisonGang plugin;

	public ChatListener(PrisonGang instance) {
		plugin = instance;
	}

	private String gang_chat = "{PRISONGANG_GANG}"; //%gang%
	private String title_chat = "{PRISONGANG_TITLE}"; //%title%
	private String chatmode = "{PRISONGANG_CHATMODE}"; //%chatmode%

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		// This will replace the {PRISONGANG_*] tags with the corresponding tags

		if (plugin.getMainConfig().doDebug()) {
			plugin.getLogger().info(
					"Gang chat prefix: " + plugin.gang_chat_prefix);
			plugin.getLogger().info(
					"Title chat prefix: " + plugin.title_chat_prefix);
			plugin.getLogger().info(
					"Chatmode prefix: " + plugin.chatmode_prefix);
		}

		Player player = event.getPlayer();
		PlayerData data = plugin.getPlayerDataHandler().getPlayerData(
				player.getName(), true);

		Set<Player> recip = event.getRecipients();

		String format = event.getFormat();

		// Keep track of players we have to remove.
		List<Player> removeables = new ArrayList<Player>();

		// Always global when not in a gang
		if (!data.isInGang()) {
			// Remove the prisongang tag
			format = format.replace(gang_chat, "").replace(title_chat, "")
					.replace(chatmode, "");

			// Set new format without the extra spaces
			event.setFormat(removeExtraSpaces(format));

			// Fix colours
			event.setFormat(ChatColor.translateAlternateColorCodes('&',
					event.getFormat()));

			return;
		}

		Gang gang = plugin.getGangHandler().getGang(data.getGangName());

		// For some reason the gang is null
		if (gang == null) {
			throw new NullPointerException("Gang is null of player "
					+ player.getName());
		}

		String chatMode = plugin.getCommands().chatMode.get(player.getName());

		if (chatMode == null) {
			chatMode = "global";
		}

		String chatModeName = null;

		if (chatMode.equals("global")) {
			chatModeName = "";
		} else if (chatMode.equals("ally-only")) {
			chatModeName = "Ally-Only";
		} else if (chatMode.equalsIgnoreCase("gang-only")) {
			chatModeName = "Gang-Only";
		}

		String rankName = (data.getRankName() != null) ? data.getRankName()
				: "";

		String gPrefix = plugin.gang_chat_prefix.replace("%gang%",
				gang.getGangName());
		String tPrefix = plugin.title_chat_prefix.replace("%title%", rankName);

		// Change format
		format = format.replace(gang_chat, gPrefix)
				.replace(title_chat, tPrefix).replace(chatmode, chatModeName);

		// Remove double spaces
		format = format.replaceAll("\\s+", " ");

		event.setFormat(format.trim());

		// Fix colours
		event.setFormat(ChatColor.translateAlternateColorCodes('&',
				event.getFormat()));

		// Everyone ought to see the message, act as vanilla minecraft
		if (chatMode.equals("global"))
			return;

		// Used to show what chat mode you're in
		String chatPrefix = "";

		if (chatMode.equals("ally-only")) {
			// Ally and gang should see message

			chatPrefix = "Ally Only";

			for (Player pRecip : recip) {
				if (!plugin.getPlayerDataHandler().isAlly(player, pRecip)
						&& !plugin.getPlayerDataHandler().isGangPartner(player,
								pRecip)) {
					// Player is not an ally and not a gang partner, thus may not receive the message

					// Do not remove the player that is talking
					if (pRecip.getName().equals(player.getName())) {
						continue;
					}

					removeables.add(pRecip);
				}
			}
		} else if (chatMode.equals("gang-only")) {
			// Gang should see message

			chatPrefix = "Gang Only";

			for (Player pRecip : recip) {
				if (!plugin.getPlayerDataHandler()
						.isGangPartner(player, pRecip)) {
					// Player is not a gang partner, thus may not receive the message

					// Do not remove the player that is talking
					if (pRecip.getName().equals(player.getName())) {
						continue;
					}

					removeables.add(pRecip);
				}
			}
		}

		// Remove all players that cannot get this message.
		recip.removeAll(removeables);

		String cPrefix = plugin.chatmode_prefix.replace("%chatmode%",
				chatPrefix);

		// Change so chatprefix is added to the format
		event.setFormat(cPrefix + " " + event.getFormat());

		// Fix colours
		event.setFormat(ChatColor.translateAlternateColorCodes('&',
				event.getFormat()));
	}

	private String removeExtraSpaces(String oldString) {
		// Remove double spaces
		oldString = oldString.replaceAll("\\s+", " ");

		StringBuilder newFormat = new StringBuilder(oldString);

		// Find extra space after '<'
		int charpoint = oldString.indexOf("%1$s") - 1;

		if (charpoint >= 0) {
			// Delete char so space is removed
			newFormat.deleteCharAt(charpoint);
		}

		return newFormat.toString().trim();
	}
}
