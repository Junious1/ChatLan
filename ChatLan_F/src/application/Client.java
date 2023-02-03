package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javafx.scene.layout.VBox;

public class Client {
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private String usermane;
	private String ip_addresse;
	
	public String getIp_addresse() {
		return ip_addresse;
	}

	public void setIp_addresse(String ip_addresse) {
		this.ip_addresse = ip_addresse;
	}

	public String getUsermane() {
		return usermane;
	}

	public void setUsermane(String usermane) {
		this.usermane = usermane;
	}
	
	public Client(String usernames) { this.usermane = usernames;}

	public Client(Socket socket) {
		try {

			this.socket = socket;
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erreur lors de la creation du serveur");
			e.printStackTrace();
			CloseEverything(socket, bufferedReader, bufferedWriter);
		}
	}
	
	public void sendMessageToServer(String messageToServer) {
		try {
			bufferedWriter.write(messageToServer);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Erreur lors de l'envoie du message au client");
			CloseEverything(socket, bufferedReader, bufferedWriter);
		}
		
	}
	
	public void receiveMessageFromServer(VBox vbox) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (socket.isConnected()) {
					try {
						String messageFromServer = bufferedReader.readLine();
						ControllerC.addLabel(messageFromServer, vbox);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("Erreur lors de la reception du message du client");
						CloseEverything(socket, bufferedReader, bufferedWriter);
						break;
					}
					
				}
				
			}
		}).start();;
		
	}
	
	public void CloseEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		
		try {
			if(bufferedReader != null) {bufferedReader.close();}
			
			if(bufferedWriter != null) {bufferedWriter.close();}
			
			if(socket != null) {socket.close();}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
