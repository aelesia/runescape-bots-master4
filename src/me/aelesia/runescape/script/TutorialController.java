package me.aelesia.runescape.script;

import java.net.URL;
import java.util.ResourceBundle;

import me.aelesia.runescape.utils.general.RandomUtils;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class TutorialController implements Initializable {

	@FXML
	private Button startBtn;
	@FXML
	private TextField minTimeTxt;
	@FXML
	private TextField maxTimeTxt;
	
	Config config;
//	Stage stage;
	
	public TutorialController(Config config) {
		this.config = config;
//		this.stage = stage;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		startBtn.setOnAction(start());
	}
	
	private EventHandler<ActionEvent> start() {
		return event -> {
			int minTime = Integer.parseInt(minTimeTxt.getText());
			int maxTime = Integer.parseInt(maxTimeTxt.getText());
			config.bottingDuration = RandomUtils.randomInt(minTime*60, maxTime*60);
			config.ready = true;
			startBtn.setDisable(true);
		};
	}

}
