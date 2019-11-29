package insta;

import java.util.ArrayList;
import java.util.Collection;

import com.google.appengine.api.datastore.Entity;

public class User {
	
	private Long id;
	private String nom; 
	private String prenom;
	private String email;
	private String login;
	private Collection<Long> followers; // Liste des gens que je me suive
	private Collection<Long> followees; // liste des gens que je suis
	
	
	public User(Entity e) {
		this.id = e.getKey().getId();
		this.nom = (String) e.getProperty("nom");
		this.prenom = (String) e.getProperty("prenom");
		this.email = (String) e.getProperty("email");
		this.login = (String) e.getProperty("login");
		
		this.followees = new ArrayList<Long>();
		this.followers = new ArrayList<Long>();

	}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public Collection<Long> getFollowers() {
		return followers;
	}


	public void setFollowers(Collection<Long> followers) {
		this.followers = followers;
	}


	public Collection<Long> getFollowees() {
		return followees;
	}


	public void setFollowees(Collection<Long> followees) {
		this.followees = followees;
	}
	
	public boolean addFollowees(Long idFollowees) {
        return this.followees.add(idFollowees);
    }
	
    public boolean containsFollower(Long idFollowers) {
        return followers.contains(idFollowers);
    }
    
    public boolean containsFollowees(Long idFollowees) {
        return followees.contains(idFollowees);
    }

	
	
	

}
