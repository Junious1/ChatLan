package application;

import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import application.Main;

public class CtrlConnexion implements Initializable{
	
	@FXML
	private TextField enter_username;
	@FXML
	private Button btn_username;
	@FXML
	private AnchorPane rootAnchor;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		btn_username.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {		
					
					String nom = enter_username.getText();
					FileWriter fstream = new FileWriter("nom.txt");
					fstream.append(nom);
					fstream.close();
					
					if(arg0.getSource().equals(btn_username)) {
						rootAnchor.getScene().getWindow().hide();
					}
					
					enter_username.clear();
					Parent root = FXMLLoader.load(getClass().getResource("demarrer.fxml"));
					Scene scene = new Scene(root,700,492);
					Stage stage = new Stage();
					stage.setTitle("Demarrrer");
					stage.setScene(scene);
					stage.show();
					//stage.setResizable(false);
					
						
					
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
		
		
	}
	
	
	

}
