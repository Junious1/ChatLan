package application;

import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ControllerC implements Initializable{
	@FXML
	private Button button_send;
	@FXML
	private TextField tf_message;
	@FXML
	private VBox vbox_message;
	@FXML
	private ScrollPane sp_main;
	
	private Client client;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			
			FileReader reader = new FileReader("ip.txt");
			int count = reader.read();
			String ip = "";
			while (count != -1) {
				char c = ((char)count);
				 ip = ip+c;	
				 count = reader.read();
			}
			reader.close();
			
			client = new Client(new Socket(ip, 1234));
			System.out.println("Connecter au serveur");
			
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		vbox_message.heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				sp_main.setVvalue((Double) newValue);
				
			}
			
		});
		
		client.receiveMessageFromServer(vbox_message);
		
		button_send.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String messageToSend = tf_message.getText();
				if(!messageToSend.isEmpty()) {
					HBox hBox = new HBox();
					hBox.setAlignment(Pos.CENTER_RIGHT);
					
					hBox.setPadding(new Insets(5, 5, 5, 10));
					Text text = new Text(messageToSend);
					TextFlow textFlow = new TextFlow(text);
					
					textFlow.setStyle("-fx-color: rgb(239,242,255); -fx-background-color: rgb(15,125,242); -fx-background-raduis:20px");
					
					textFlow.setPadding(new Insets(5, 10, 5, 10));
					text.setFill(Color.color(0.934, 0.945, 0.996));
					
					hBox.getChildren().add(textFlow);
					vbox_message.getChildren().add(hBox);
					
					client.sendMessageToServer(messageToSend);
					tf_message.clear();
					
				}
				
			}
		});
		
	}
	public static void addLabel(String messageFromServer, VBox vbox) {
		HBox hBox = new HBox();
		hBox.setAlignment(Pos.CENTER_LEFT);
		hBox.setPadding(new Insets(5, 5, 5, 10));
		
		Text text = new Text(messageFromServer);
		TextFlow textFlow = new TextFlow(text);
		textFlow.setStyle("-fx-background-color: rgb(233,233,235); -fx-background-raduis:20px");
		textFlow.setPadding(new Insets(5, 10, 5, 10));
		hBox.getChildren().add(textFlow);
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				vbox.getChildren().add(hBox);				
			}
		});
	}
	
	
}
