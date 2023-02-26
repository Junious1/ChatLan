package client;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class Client {
	private String nom;
	private String prenom;
	private String nomU;
	private String mdp;
	
	
	public Client() {};
	public Client(String nom,String prenom, String nomU, String mdp) {
		this.nom = nom;
		this.prenom = prenom;
		this.nomU = nomU;
		this.mdp = mdp;	
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	public String getNomU() {
		return nomU;
	}
	public void setNomU(String nomU) {
		this.nomU = nomU;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
	
	
}
