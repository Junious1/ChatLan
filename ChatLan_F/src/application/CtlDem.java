package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CtlDem implements Initializable{
	@FXML
	private Button rejoindre;
	@FXML
	private Button demarrer;
	
	@FXML
	private Label username;
	@FXML
	private Label ip_addresse;
	@FXML
	private AnchorPane rootAnchor;
	
	String titre;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		try {
			FileReader reader = new FileReader("nom.txt");
			int count = reader.read();
			String nom = "";
			while (count != -1) {
				char c = ((char)count);
				 nom = nom+c;	
				 count = reader.read();
			}
			reader.close();
			username.setText(nom.toUpperCase());
			titre = nom.toUpperCase();
			
			InetAddress ip = InetAddress.getLocalHost();
			ip_addresse.setText(ip.getHostAddress());
			
			
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		rejoindre.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				try {
					
					if(arg0.getSource().equals(rejoindre)) {
						rootAnchor.getScene().getWindow().hide();
					}
					
					Parent root = FXMLLoader.load(getClass().getResource("adresseIp.fxml"));
					Scene scene = new Scene(root,700,492);
					Stage stage = new Stage();
					stage.setTitle("Connexion");
					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	
		
		demarrer.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					
					Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
					Scene scene = new Scene(root,770,400);
					Stage stage = new Stage();
					stage.setTitle(titre);
					stage.setScene(scene);
					stage.show();
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	

}
