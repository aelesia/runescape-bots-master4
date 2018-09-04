package me.aelesia.runescape.exceptions;

public class IllegalArgumentException extends RunescapeBotException {
	
	private static final long serialVersionUID = -3614331661233600306L;

	public IllegalArgumentException(String errorMsg) {
		super("[ERROR] IllegalArgumentException: " + errorMsg);
	}

}
