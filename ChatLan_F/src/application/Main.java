package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	public Stage stage1;	
	
	public Stage getStage() {
		return stage1;
	}

	public void setStage(Stage stage) {
		this.stage1 = stage;
	}
 
	Scene scene;
	
	@Override
	public void start(Stage stage1) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("connex.fxml"));
			scene = new Scene(root,700,492);
			stage1.setTitle("Connexion");
			stage1.setScene(scene);
			stage1.show();	
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
