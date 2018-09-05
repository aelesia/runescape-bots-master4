package me.aelesia.runescape.script;

import java.util.HashMap;
import java.util.Map;

import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.framework.LoopingBot;
import com.runemate.game.api.script.framework.logger.BotLogger;

import me.aelesia.runescape.exceptions.RunescapeBotException;
import me.aelesia.runescape.tasks.base.BaseTask;
import me.aelesia.runescape.utils.game.PlayerUtils;
import me.aelesia.runescape.exceptions.IllegalStateException;


public abstract class StateBot extends LoopingBot {
	
	protected Map<String, BaseTask> taskMap = new HashMap<String, BaseTask>();
	
	protected String state = null;
	private String previousState = null;
	private int iterations = 0;
	Rest restManager = new Rest();
//	private static boolean stateChanged = false;
	
    @Override
    public void onStart(String... args){
    	System.out.println("***** Bot Starting *****");
    	RestManager.createNew(Players.getLocal().getName());
    	this.initialize();
    	this.registerTasks();
    	this.state = startingState();
    	this.setLoopDelay(50, 100);
    	
    	BotLogger logger = new BotLogger(this);
    }
 
    @Override
    public void onStop(){
    	System.out.println("***** Bot Stopped *****");
    }
    
    @Override
    public void onLoop() {
    	iterations++;

    	try {
    		if (!taskMap.containsKey(state)) {
    			throw new IllegalStateException("No state found: " + this.state);
    		}
    		
	    	if (iterations==1) {
	    		taskMap.get(state).initialize();
	    	}
    	
	    	taskMap.get(state).validate();
	    	
	    	String changeState = taskMap.get(state).changeState(); 
	    	if (changeState == null) {
	    		taskMap.get(state).execute();
	    	} else if ("REVERT".equals(changeState)) { 
	    		taskMap.get(state).exit();
	    		revertState();
	    	} else {
	    		taskMap.get(state).exit();
	    		setState(changeState);
	    	}
	    	
	    	if (iterations==0 && state.equals(startingState())) {
	    		RestManager.get(PlayerUtils.name()).away();
	    	}
	    	
    	} catch (RunescapeBotException e) {
    		e.printStackTrace();
    		this.stop(e.getMessage());
		}
    }
	
	private void setState(String newState) {
		System.out.println("[STATE] " + state + "->" + newState);
		previousState = state;
		state = newState;
		iterations = 0;
	}
	
	private void revertState() {
		setState(previousState);
		iterations = 2;
	}

    protected abstract void registerTasks();

    protected abstract String startingState();
    
    protected abstract void initialize();
}
