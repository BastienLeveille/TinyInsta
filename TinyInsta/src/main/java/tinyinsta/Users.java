package tinyinsta;

import java.util.List;

import javax.servlet.http.HttpServlet;

import com.google.appengine.api.datastore.Entity;

public class Users extends HttpServlet{

	private Long id;
	private String pseudo;
	private String mail;
	private String mdp;
	private String prenom;
	private String nom;
	private List<Long> followers;//Liste des gens qui me suivent
	private List<Long> following;//Liste des gens que je suis

	public Users() {}

	public Users(Entity e) {
		this.id = (Long) e.getKey().getId();
		this.pseudo = (String) e.getProperty("pseudo");
		this.mail = (String) e.getProperty("mail");
		this.mdp = (String) e.getProperty("mdp");
		this.prenom = (String) e.getProperty("nom");
		this.nom = (String) e.getProperty("prenom");
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Long> getFollowers() {
		return followers;
	}

	public boolean addFollower(Long idFollower) {
		return this.followers.add(idFollower);
	}

	public void setFollowers(List<Long> followers) {
		this.followers = followers;
	}

	public List<Long> getFollowing() {
		return following;
	}

	public void setFollowing(List<Long> following) {
		this.following = following;
	}

	public boolean addFollowing(Long idFollowing) {
		return this.following.add(idFollowing);
	}

	public boolean containsFollower(Long idFollower) {
		return followers.contains(idFollower);
	}

	public boolean containsFollowing(Long idFollowing) {
		return following.contains(idFollowing);
	}
}
