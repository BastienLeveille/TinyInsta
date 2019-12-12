package tinyinsta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.repackaged.com.google.datastore.v1.client.Datastore;
import com.google.gson.Gson;

import tinyinsta.FollowersFollowees;

@WebServlet(name = "Like", urlPatterns = "/Like")
public class Like extends HttpServlet{
	private Gson gson = new Gson();

	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	     
	    Long idMessage = Long.parseLong(request.getParameter("idPost"));
	    Long compteur = (long) 0;
	    Entity likeEntity;
	    
	    System.out.println("on est dans le get de like");
	    System.out.println("l'id du message est " + idMessage);
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

	    Filter filter = new Query.FilterPredicate("idPost", FilterOperator.EQUAL, idMessage);
	    Query query = new Query("Like").setFilter(filter);
	    List<Entity> likeList = ds.prepare(query).asList(FetchOptions.Builder.withDefaults());	  
	    System.out.println("Recup de la query");
	    System.out.println("taille de la liste des entity like :"+likeList.size());
	    for (Entity like : likeList) {
	    	compteur +=(Long) like.getProperty("CompteurDeLike");
	    	System.out.println(compteur);

	    }
	    
	    String jsonPost = this.gson.toJson(compteur);
	    System.out.println(jsonPost);
		response.getWriter().print(jsonPost);
		
	     
	     
		
	   }


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {	
		
		String pseudo;
		Boolean likeornot;
		Long idMessage ;
		
		System.out.println("dans la fonctionPost de like");
		pseudo = request.getParameter("pseudo");
		idMessage =Long.parseLong(request.getParameter("idPost"));
		likeornot = Boolean.parseBoolean(request.getParameter("likeornot"));
		
		System.out.println(likeornot);
		if (likeornot) {
	        System.out.println("appele de la fonciton create like");
	        FollowersFollowees follow = new FollowersFollowees();
	        boolean create =follow.createlike(pseudo, idMessage);
	        
	        if (create) {
	            System.out.println("Like ajouté");
	        } else {
	        	System.out.println("Like non ajouté");
	        }
		} else {
			System.out.println("appele de la fonciton unlike");
	        FollowersFollowees follow = new FollowersFollowees();
	        boolean create =follow.Unlike(pseudo, idMessage);
	        
	        if (create) {
	            System.out.println("Like supprimé");
	        } else {
	        	System.out.println("Like non supprimé");
	        }
		}
		
	}

	
	
}
