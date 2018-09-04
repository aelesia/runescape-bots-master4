package me.aelesia.runescape.exceptions;

public class OutOfBoundsException extends RunescapeBotException {
	
	private static final long serialVersionUID = 3926427667740371370L;

	public OutOfBoundsException(String errorMsg) {
		super("[ERROR] OutOfBoundsException: " + errorMsg);
	}

}
