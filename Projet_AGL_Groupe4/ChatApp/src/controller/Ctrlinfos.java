package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Ctrlinfos{
	@FXML
	private Button retour;
	
	@FXML
	void retour() {
		Stage stage = new Stage();
		stage = (Stage) retour.getScene().getWindow();
		stage.hide();
		
	}

}
