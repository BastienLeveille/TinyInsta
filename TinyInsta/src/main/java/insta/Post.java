package insta;

import java.util.Date;

import com.google.appengine.api.datastore.Entity;

public class Post {

	private Long id;
	private Long idAuteur;
	private String AuteurDuPost;
	private String message;
	// private Type photo;
	private Date date;
	
	public Post(Entity e) {
		this.id = e.getKey().getId();
		this.AuteurDuPost = (String) e.getProperty("auteur");
		this.message = (String) e.getProperty("msg");
		this.date = (Date) e.getProperty("date");
		this.idAuteur = (Long) e.getProperty("idAuteur");
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdAuteur() {
		return idAuteur;
	}

	public void setIdAuteur(Long idAuteur) {
		this.idAuteur = idAuteur;
	}

	public String getAuteurDuPost() {
		return AuteurDuPost;
	}

	public void setAuteurDuPost(String auteurDuPost) {
		AuteurDuPost = auteurDuPost;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
