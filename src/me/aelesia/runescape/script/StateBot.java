package me.aelesia.runescape.script;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

import com.runemate.game.api.client.embeddable.EmbeddableUI;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.Resources;
import com.runemate.game.api.script.framework.LoopingBot;
import me.aelesia.runescape.exceptions.RunescapeBotException;
import me.aelesia.runescape.tasks.base.BaseTask;
import me.aelesia.runescape.utils.game.LocationUtils;
import me.aelesia.runescape.utils.game.Logger;
import me.aelesia.runescape.utils.game.PlayerUtils;
import me.aelesia.runescape.utils.general.ThreadUtils;
import me.aelesia.runescape.actions.InventoryActions;
import me.aelesia.runescape.actions.LocationActions;
import me.aelesia.runescape.exceptions.IllegalStateException;
import me.aelesia.runescape.exceptions.OutOfTimeException;


public abstract class StateBot extends LoopingBot implements EmbeddableUI {
	
	protected Map<String, BaseTask> taskMap = new HashMap<String, BaseTask>();
	
	private String state = null;
	private String previousState = null;
	private LocalDateTime stateStartTime;
	private int iterations = 0;
	Rest rest;
	protected Config config = new Config();
//	private static boolean stateChanged = false;
	
	int failCounter;
	
	public StateBot() {
    	setEmbeddableUI(this);
	}
	
    @Override
    public void onStart(String... args){
    	Logger.info("***** Bot setup *****");
    	while (!config.ready) {
    		ThreadUtils.sleepFor(100);
    	}
    	Logger.info("***** Bot Starting *****");
    	Logger.config(this.config.toString());
    	RestManager.createNew(Players.getLocal().getName(), config.bottingDuration);
    	this.initialize();
    	this.registerTasks();
    	setState(startingState());
    	this.setLoopDelay(50, 100);
    }
 
    @Override
    public void onStop(){
    	Logger.info("***** Bot Stopped *****");
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
	    		failCounter = 0;
	    	}
    	
	    	taskMap.get(state).validate();
	    	
	    	String changeState = taskMap.get(state).changeState(); 
	    	
	    	this.stateTimeout(10);
	    	
	    	if (changeState == null) {
	    		if (taskMap.get(state).execute()) {
	    			failCounter = 0;
	    		} else {
	    			failCounter++;
	    			Logger.info("Fail count: " + failCounter);
	    			if (failCounter>=15) {
		    			Logger.info("Execution failed more than 15 times. Reseting state");
		    			setState(startingState());
		    		}
	    		}	    		
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
	    	
	    	RestManager.get(PlayerUtils.name()).exit();
	    	
    	} catch (RunescapeBotException e) {
    		e.printStackTrace();
    		this.stop(e.getMessage());
		}
    }
    
    private void reset() {
    	InventoryActions.close();
    	LocationActions.walkHere(LocationUtils.getEmptySpaceBesideMe());
    }
	
	private void setState(String newState) {
		Logger.stateChange(state, state + "->" + newState);
		previousState = state;
		state = newState;
		iterations = 0;
		stateStartTime = LocalDateTime.now();
	}
	
	private void revertState() {
		setState(previousState);
		iterations = 2;
	}
	
	public void stateTimeout(int mins) {
    	if (stateStartTime.plusMinutes(mins).compareTo(LocalDateTime.now()) <= 0) {
    		throw new OutOfTimeException("State running for more than " + mins + " mins");
    	}
    	
	}

	@Override
	public ObjectProperty<? extends Node> botInterfaceProperty() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(new TutorialController(config));
//			Node node = loader.load(this.getClass().getResourceAsStream("me/aelesia/runescape/script/Tutorial.fxml"));
            Node node = loader.load(Resources.getAsStream("me/aelesia/runescape/script/Tutorial.fxml"));
            return new SimpleObjectProperty<>(node);
        } catch (IOException e) {
        	throw new RuntimeException(e);
        }            
	}
	
    protected abstract void registerTasks();

    protected abstract String startingState();
    
    protected abstract void initialize();
}
