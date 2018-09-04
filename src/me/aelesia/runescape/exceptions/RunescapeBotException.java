package me.aelesia.runescape.exceptions;

public class RunescapeBotException extends RuntimeException {

	private static final long serialVersionUID = -5806033052596190734L;

	public RunescapeBotException(String errorMsg) {
		super(errorMsg);
	}
}
