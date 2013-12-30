package me.staartvin.prisongang.permissions;

public enum GangAbility {

	// TODO Add all abilities and descriptions
	
	KICK("Allow to kick members from the gang"),
	CHANGE_TITLE("Allow to change the title of other members"),
	CHANGE_DESC("Allow to change the description of the gang"),
	CHANGE_STATUS("Allow to change the status of this gang to other gangs (neutral, ally or enemy)"),
	SET_LEADER("Allow to set the leader of the gang"),
	ADD_ABILITY("Allow to give an ability to a player"),
	REMOVE_ABILITY("Allow to remove an ability from a player"),
	SET_PRIVATE("Allow to set the gang private"),
	INVITE_PLAYERS("Allow to invite players to the gang")
	;
	
	private String desc;
	
	GangAbility(String description) {
		desc = description;
	}
	
	/**
	 * Get the description of this certain ability
	 * @return description
	 */
	public String getDescription() {
		return desc;
	}
}
