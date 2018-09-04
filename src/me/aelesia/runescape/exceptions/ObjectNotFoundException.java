package me.aelesia.runescape.exceptions;

public class ObjectNotFoundException extends RunescapeBotException {
	
	private static final long serialVersionUID = -8772754781233183288L;

	public ObjectNotFoundException(String object) {
		super("[ERROR] ObjectNotFoundException: " + object);
	}
}
