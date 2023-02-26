package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Server;


public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			AnchorPane root = FXMLLoader.load(getClass().getResource("/view/loggins.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setTitle("Sign in");
			//primaryStage.getIcons().add(new Image("/Images/iconeM.png")); 
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/Images/iconeM.png")));
			primaryStage.show();
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
//		
//		new Thread(){
//			public void run() {
//				Server.main(args);
//			}
//		}.start();
//		
		launch(args);
}
}


 
 
