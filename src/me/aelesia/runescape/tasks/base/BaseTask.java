package me.aelesia.runescape.tasks.base;

//public abstract class BaseTask {
	
public abstract class BaseTask {
	
	public void initialize() {};

	public void exit() {};
	/**
	* This method checks several conditions and will throw a RuntimeException
	* if it is impossible to recover from the scenario.
	*/
	public abstract void validate();
	
	public abstract String changeState();

	public abstract boolean execute();
}
