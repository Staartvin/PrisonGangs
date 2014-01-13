package me.staartvin.prisongang.translation;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Every enumeration value has its path and default value.
 * To get the path, do {@link #getPath()}.
 * To get the default value, do {@link #getDefault()}.
 * 
 * For the defined value in the lang.yml config, use
 * {@link #getConfigValue(String[])}.
 * A string array is expected for the input. This can be null
 * 
 * @author Staartvin and gomeow
 * 
 */
public enum Lang {
	PLAYER_NOT_ONLINE("player-not-online", "&4{0} is not online!"),
	ONLY_PLAYER_ACTIVITY("only-player-activity", "&4Only players can do this!"),
	NOT_IN_A_GANG("not-in-a-gang", "&4You're not in a gang!"),
	NOT_AUTHORISED("not-authorised", "&4You're not authorised to do this."),
	GANG_DOES_NOT_EXIST("gang-does-not-exist", "&4That gang does not exist."),
	PLAYER_IS_NOT_IN_SAME_GANG("player-is-not-in-same-gang", "&4{0} is not in the same gang as you."),
	PLAYER_ALREADY_HAS_ABILITY("player-already-has-ability", "&4{0} already has that ability."),
	GAVE_ABILITY_TO_PLAYER("gave-ability-to-player", "&aYou've given &6{0}&a '{1}'."),
	GOT_ABILITY_FROM_PLAYER("got-ability-from-player", "&aYou've gotten {0} from &6{1}.")
	;

	private String path, def;
	private static FileConfiguration LANG;

	/**
	 * Lang enum constructor.
	 * 
	 * @param path The string path.
	 * @param start The default string.
	 */
	Lang(final String path, final String start) {
		this.path = path;
		this.def = start;
	}

	/**
	 * Set the {@code FileConfiguration} to use.
	 * 
	 * @param config The config to set.
	 */
	public static void setFile(final FileConfiguration config) {
		LANG = config;
	}

	/**
	 * Get the value in the config with certain arguments
	 * 
	 * @param args arguments that need to be given. (Can be null)
	 * @return value in config or otherwise default value
	 */
	public String getConfigValue(final String[] args) {
		String value = ChatColor.translateAlternateColorCodes('&',
				LANG.getString(this.path, this.def));

		if (args == null)
			return value;
		else {
			if (args.length == 0)
				return value;

			for (int i = 0; i < args.length; i++) {
				value = value.replace("{" + i + "}", args[i]);
			}
		}

		return value;
	}

	/**
	 * Get the default value of the path.
	 * 
	 * @return The default value of the path.
	 */
	public String getDefault() {
		return this.def;
	}

	/**
	 * Get the path to the string.
	 * 
	 * @return The path to the string.
	 */
	public String getPath() {
		return this.path;
	}
}
