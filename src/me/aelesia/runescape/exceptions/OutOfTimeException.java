package me.aelesia.runescape.exceptions;

public class OutOfTimeException extends RunescapeBotException {
	
	private static final long serialVersionUID = 3926427667740371370L;

	public OutOfTimeException(String errorMsg) {
		super("[ERROR] OutOfTimeException: " + errorMsg);
	}

}
