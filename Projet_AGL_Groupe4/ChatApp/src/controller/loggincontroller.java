package controller;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.ResourceBundle;

import  javax.xml.bind.JAXBContext ;
import javax.xml.bind.JAXBException;
import  javax.xml.bind.Unmarshaller ;

import application.Main;
import client.Client;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Server;
import model.connectiondata;
import view.roomchatview;

public class loggincontroller implements Initializable{

	private Stage escenario;
	private double xOffset = 0;
	private double yOffset = 0;
	
	@FXML
	private Label incorr;
	
	@FXML
	private AnchorPane afficher;
	
	
	@FXML
	private TextField txtname;
	
	@FXML
	private PasswordField mdp;

	@FXML
	private TextField txtserverip;

	@FXML
	private TextField txtfolderaddress;
	@FXML
	private Label ips;
	
	
	

	@FXML
	void close(ActionEvent event) {
		System.exit(0);

	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		try {
			InetAddress ip = InetAddress.getLocalHost();
			txtserverip.setPromptText(ip.getHostAddress());
			ips.setText(ip.getHostAddress());
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int numberOfSquares = 30;
        while (numberOfSquares > 0){
            generateAnimation();
            numberOfSquares--;
        }
		
	}
	
	public void generateAnimation(){
        Random rand = new Random();
        int sizeOfSqaure = rand.nextInt(50) + 1;
        int speedOfSqaure = rand.nextInt(10) + 5;
        int startXPoint = rand.nextInt(420);
        int startYPoint = rand.nextInt(350);
        int direction = rand.nextInt(5) + 1;
        Color color = Color.BLUE;

        KeyValue moveXAxis = null;
        KeyValue moveYAxis = null;
        Rectangle r1 = null;

        switch (direction){
            case 1 :
                // Mouvement gauche-droite
                r1 = new Rectangle(0,startYPoint,sizeOfSqaure,sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), 600 -  sizeOfSqaure);
                break;
            case 2 :
                // mouvement haut-bas
                r1 = new Rectangle(startXPoint,0,sizeOfSqaure,sizeOfSqaure);
                moveYAxis = new KeyValue(r1.yProperty(), 420 - sizeOfSqaure);
                break;
            case 3 :
                // mouvement gauche-droite, haut-bas
                r1 = new Rectangle(startXPoint,0,sizeOfSqaure,sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), 600 -  sizeOfSqaure);
                moveYAxis = new KeyValue(r1.yProperty(), 420 - sizeOfSqaure);
                break;
            case 4 :
                // mouvement bas-haut
                r1 = new Rectangle(startXPoint,420-sizeOfSqaure ,sizeOfSqaure,sizeOfSqaure);
                moveYAxis = new KeyValue(r1.xProperty(), 0);
                break;
            case 5 :
                // mouvement droite-gauche
                r1 = new Rectangle(420-sizeOfSqaure,startYPoint,sizeOfSqaure,sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), 0);
                break;
            case 6 :
                //mouvement droite-gauche, bas-haut
                r1 = new Rectangle(startXPoint,0,sizeOfSqaure,sizeOfSqaure);
                moveXAxis = new KeyValue(r1.xProperty(), 600 -  sizeOfSqaure);
                moveYAxis = new KeyValue(r1.yProperty(), 420 - sizeOfSqaure);
                break;

            default:
                System.out.println("default");
        }

        r1.setFill(Color.web("#00A2E8"));
        r1.setOpacity(0.1);

        KeyFrame keyFrame = new KeyFrame(Duration.millis(speedOfSqaure * 1000), moveXAxis, moveYAxis);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        afficher.getChildren().add(afficher.getChildren().size(),r1);
    }

	
	@FXML
	void insc(ActionEvent event) {
		try {
			escenario = (Stage) txtname.getScene().getWindow();
			escenario.hide();
			
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/view/inscrire.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("INSCRIPTION");
			stage.initStyle(StageStyle.UNDECORATED);
			stage.getIcons().add(new Image(Main.class.getResourceAsStream("/Images/iconeM.png")));
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void logginkey(KeyEvent event) throws IOException {
		if (event.getCode() == KeyCode.ENTER) {
			try {
				login(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@FXML
	void login(ActionEvent event) throws IOException, JAXBException {
		
		
		
		String mdpE = mdp.getText();
			
			File file = new File("resources\\client.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Client.class);
			Unmarshaller unmarshaller = jaxbContext . createUnmarshaller ();
			Client client = (Client) unmarshaller.unmarshal(file);
		
		String name = txtname.getText();
		String folderaddres = txtfolderaddress.getText();
		String ipserver = txtserverip.getText();
		
		if(ipserver.equalsIgnoreCase(InetAddress.getLocalHost().getHostAddress())) {
			new Thread(){
				public void run() {
					String[] args = null;
					Server.main(args);
				}
			}.start();
			System.out.println("Server ouvert");
		}
		
		if (!name.equals("") && !folderaddres.equals("") && !ipserver.equals("")) {
			
			if (name.equals(client.getNomU()) && mdpE.equals(client.getMdp())) {
				connectiondata.folderfiles = folderaddres;
				connectiondata.ipserver = ipserver;
				connectiondata.name = name;
				
				escenario = (Stage) txtname.getScene().getWindow();
				escenario.hide();
				
				new roomchatview();
				
			} else {
				incorr.setText("Nom d'utilisateur ou mot de passe incorrecte!!");
			}
				
		} else {
			incorr.setText("Veuillez remplir tout les champs!!");
		}
		
	}

	@FXML
	void selectfolder(ActionEvent event) {
		
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(new Stage());
		if (selectedDirectory != null) {
			
			txtfolderaddress.setText(selectedDirectory.getAbsolutePath()+"\\");
			connectiondata.folderfiles = selectedDirectory.getAbsolutePath()+"\\";
		}
	}
	
	@FXML
	void MouseDrag(MouseEvent event) {
		escenario = (Stage) txtname.getScene().getWindow();
		escenario.setX(event.getScreenX() - xOffset);
		escenario.setY(event.getScreenY() - yOffset);
		
	}

	
	@FXML
	void Mousepress(MouseEvent event) {
		Scene escene = txtname.getScene();
    	escene.setCursor(Cursor.MOVE);
		 xOffset = event.getSceneX();
         yOffset = event.getSceneY();
	}
	
	@FXML
    void Mousereleased(MouseEvent event) {
    	Scene escene = txtname.getScene();
    	escene.setCursor(Cursor.DEFAULT);
    	
    }



}
