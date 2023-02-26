package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import  javax.xml.bind.JAXBContext ; 
import  javax.xml.bind.Marshaller ;

import application.Main;
import client.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.closehandle;

public class CtrlInscrire implements Initializable{
	
	private Stage stage;
	@FXML
	private TextField nom;
	@FXML
	private TextField prenom;
	@FXML
	private TextField nomU;
	@FXML
	private PasswordField mdp;
	@FXML
	private Button btn_inscrire;
	@FXML
	private Label terminer;
	@FXML
	private Hyperlink SeCo;
	@FXML
	private Label erreur;
	
	@FXML
	void closeStage(MouseEvent event) {

		stage = (Stage) SeCo.getScene().getWindow();
		stage.hide();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		SeCo.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					stage = (Stage) SeCo.getScene().getWindow();
					stage.hide();
					AnchorPane root = FXMLLoader.load(getClass().getResource("/view/loggins.fxml"));
					Scene scene = new Scene(root);
					stage = new Stage();
					stage.setScene(scene);
					stage.getIcons().add(new Image(Main.class.getResourceAsStream("/Images/iconeM.png")));
					stage.initStyle(StageStyle.UNDECORATED);
					stage.setResizable(false);
					stage.setTitle("Sign in");
					//primaryStage.getIcons().add(new Image("/Images/iconeM.png"));
//					stage.getIcons().add(new Image(Main.class.getResourceAsStream("/Images/iconeM.png")));
					stage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		btn_inscrire.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					
					if(nom.getText().equals("") && prenom.getText().equals("") && nomU.getText().equals("") && mdp.getText().equals("")) {
						erreur.setText("VEUILLEZ REMPLIR TOUS LES CHAMPS!!");
						
						
						
					}else {
						Client client = new Client(nom.getText(), prenom.getText(), nomU.getText(), mdp.getText());
						
						JAXBContext jaxbContext = JAXBContext.newInstance(Client.class);
						Marshaller marshaller = jaxbContext.createMarshaller();
						
				       File file = new File("resources\\client.xml");
						
						marshaller.marshal(client, file);
//						Parent root = FXMLLoader.load(getClass().getResource("ok.fxml"));
//						Stage stage = new Stage();
//						Scene scene = new Scene(root,257,135);
//						stage.setTitle("Connexion");
//						stage.setScene(scene);
//						stage.setResizable(false);
////						Image ico = new Image();
////						stage1.getIcons().add(new Image("../Images/iconeM.png")); 
//						stage.show();
//						
//						Parent root2 = FXMLLoader.load(getClass().getResource("connex.fxml"));
//						Stage stage2 = new Stage();
//						Scene scene2 = new Scene(root2,700,492);
//						stage2.setTitle("Connexion");
//						stage2.setScene(scene2);
//						stage2.setResizable(false);
////						Image ico = new Image();
////						stage1.getIcons().add(new Image("../Images/iconeM.png")); 
//						stage2.show();

						
						
						nom.clear();
						prenom.clear();
						nomU.clear();
						mdp.clear();
						
						terminer.setText("INSCRIPTION TERMINEE!!");	
					}
					
					
					
				} catch (javax.xml.bind.JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
	}

}
