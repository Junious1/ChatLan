package application;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

public class CtrlAdd implements Initializable{
	@FXML
	private TextField ip_entre;
	@FXML
	private Button btn_sendIp;
	@FXML
	private AnchorPane rootAnchor;
	String titre;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		btn_sendIp.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					String ip = ip_entre.getText();
					FileWriter fstream = new FileWriter("ip.txt");
					fstream.append(ip);
					fstream.close();
					
					FileReader reader = new FileReader("nom.txt");
					int count = reader.read();
					String nom = "";
					while (count != -1) {
						char c = ((char)count);
						 nom = nom+c;	
						 count = reader.read();
					}
					reader.close();
					titre = nom.toUpperCase();
					
					if(arg0.getSource().equals(btn_sendIp)) {
						rootAnchor.getScene().getWindow().hide();
					}
					
					Parent root = FXMLLoader.load(getClass().getResource("clientfx.fxml"));
					Stage stage = new Stage();
					Scene scene = new Scene(root,770,400);
					stage.setTitle(titre);
					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					
					
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
	}

}
