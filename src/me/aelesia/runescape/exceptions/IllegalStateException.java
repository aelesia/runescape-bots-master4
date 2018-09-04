package me.aelesia.runescape.exceptions;

public class IllegalStateException extends RunescapeBotException {
	
	private static final long serialVersionUID = 3926427667740371370L;

	public IllegalStateException(String errorMsg) {
		super("[ERROR] IllegalStateException: " + errorMsg);
	}

}
