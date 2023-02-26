package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Usersconnected;
import model.closehandle;
import model.connectiondata;
import model.message;
import view.visualcomponents;

public class roomchatcontroller implements Initializable, Runnable {

	private String ip, usuario, filename;
	private Object[] usersList;
	private HashMap<String, Usersconnected> usersconnected;
	private Usersconnected U;

	@FXML
	private VBox chatarea;

	@FXML
	private VBox Listeconnecter;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private TextArea txtmsj;

	@FXML
	void sendmsj(ActionEvent event) {
		
		

		String txt = txtmsj.getText();
		if (!txt.trim().equals("")) {
			sendingobject(txt, true, null);
			
			//Media hit = new Media(getClass().getClassLoader().getResource("sounds/notification.wav").toString());
	        Media hit = new Media(new File("src/sounds/envoi.wav").toURI().toString());
			MediaPlayer mediaPlayer = new MediaPlayer(hit);
	        
	        mediaPlayer.play();
		}
		txtmsj.setText("");
		txtmsj.requestFocus();

		chatarea.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));

	}

	private Stage escenario;
	private double xOffset = 0;
	private double yOffset = 0;

	@FXML
	void MouseDrag(MouseEvent event) {
		escenario = (Stage) chatarea.getScene().getWindow();
		escenario.getIcons().add(new Image(Main.class.getResourceAsStream("/Images/iconeM.png")));
		escenario.setX(event.getScreenX() - xOffset);
		escenario.setY(event.getScreenY() - yOffset);

	}

	@FXML
	void Mousepress(MouseEvent event) {
		Scene escene = chatarea.getScene();
		escene.setCursor(Cursor.MOVE);
		xOffset = event.getSceneX();
		yOffset = event.getSceneY();
	}

	@FXML
	void Mousereleased(MouseEvent event) {
		Scene escene = chatarea.getScene();
		escene.setCursor(Cursor.DEFAULT);

	}
	
	@FXML
	void infos(MouseEvent event) {
		try {
			Parent infos = FXMLLoader.load(getClass().getResource("/view/infos.fxml"));
			Scene scene = new Scene(infos, 735, 435);
			Stage stage = new Stage();
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@FXML
	void closeStage(MouseEvent event) {

		closehandle.handle();
	}

	@FXML
	void sendmsjkey(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			sendmsj(null);

		}
	}

	@FXML
	void closeMinimize(MouseEvent event) {
		escenario = (Stage) txtmsj.getScene().getWindow();
		escenario.setIconified(true);

	}

	@FXML
	void SendFile(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select file to sending.");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"),
				new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
				new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.aac"));
		File selectedFile = fileChooser.showOpenDialog(new Stage());

		if (selectedFile != null) {

			sendingobject(selectedFile.getName(), true, selectedFile);

			try {
				Socket socket = null;
				String host = connectiondata.ipserver;

				socket = new Socket(host, 4444);

				// Récupère la taille du fichier
				long length = selectedFile.length();
				byte[] bytes = new byte[(int) length];
				InputStream in = new FileInputStream(selectedFile);
				OutputStream out = socket.getOutputStream();

				int count;
				while ((count = in.read(bytes)) > 0) {
					out.write(bytes, 0, count);
				}
				out.close();
				in.close();
				socket.close();

			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Thread filetranferserver = new Thread(new transferFiles());
		filetranferserver.start();

		Thread messageserver = new Thread(this);
		messageserver.start();

		usuario = connectiondata.name;
		sendingobject(null, true, null);

	}

	private void sendingobject(String txt, boolean connected, File file) {
		try {
			Socket s = new Socket(connectiondata.ipserver, 9999);
			ObjectOutputStream oo = new ObjectOutputStream(s.getOutputStream());
			ip = InetAddress.getLocalHost().getHostAddress();
			oo.writeObject(new message(usuario, ip, txt, connected, file));
			oo.close();
			s.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			HBox box = visualcomponents.bubble("", "-----------------Aucun serveur connecter-----------------", "red",
					true);
			chatarea.getChildren().add(box);
		}

	}

	@Override
	public void run() {

		try {

			while (true) {

				ServerSocket ss = new ServerSocket(3000);
				Socket s = ss.accept();
				ObjectInputStream oi = new ObjectInputStream(s.getInputStream());
				message msj = (message) oi.readObject();

				usersList = msj.getusersList();
				usersconnected = msj.getUsersconnected();

				if (msj.isConected()) {
					// aligner le texte et définir le chat du propriétaire de la couleur
					if (msj.getNombre().equals(usuario) && msj.getIp().equals(ip)) {
						msj.setAlignPos(false);
						msj.setColor("#7FB5FF");
					}

					if (msj.getText() == null) {
						msj.setText("Connecter.");
					}

					// if (msj.getFile() != null && !msj.getIp().equals(ip)) {

					if (msj.getFile() != null) {

						filename = msj.getFile().getName();

					}

				} else
					msj.setText("Deconnecter.");

				// JavaFX fonctionne avec son propre thread
				Platform.runLater(() -> {

					HBox box = visualcomponents.bubble(msj.getNombre(), msj.getText(), msj.getColor(),
							msj.isAlignPos());
					chatarea.getChildren().add(box);

					Listeconnecter.getChildren().clear();
					for (Object o : usersList) {
						String iptemp = (String) o;
						U = usersconnected.get(iptemp);
						Listeconnecter.getChildren().add(new visualcomponents(U).userpane());
						

					}
				});

				ss.close();
				s.close();
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// classe interne pour l'accès aux valeurs parentes de la classe
	public class transferFiles implements Runnable {

		public void run() {

			ServerSocket serverSocket = null;

			while (true) {
				try {
					serverSocket = new ServerSocket(4455);
				} catch (IOException ex) {
					System.out.println("Impossible de configurer le serveur sur ce numéro de port. ");
				}
				Socket socket = null;
				InputStream in = null;
				OutputStream out = null;
				try {
					socket = serverSocket.accept();
					in = socket.getInputStream();
					out = new FileOutputStream(connectiondata.folderfiles + filename);
					byte[] bytes = new byte[16 * 1024];

					int count;

					while ((count = in.read(bytes)) > 0) {
						out.write(bytes, 0, count);
					}
					out.close();
					in.close();
					socket.close();
					serverSocket.close();
					filename = null;
				} catch (IOException ex) {
					System.out.println("Impossible d'accepter la connexion client. ");
				}

			}

		}

	}

}
