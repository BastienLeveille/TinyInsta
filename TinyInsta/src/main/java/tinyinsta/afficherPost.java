package tinyinsta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import tinyinsta.FollowersFollowees;
import tinyinsta.Users;

@WebServlet(name="afficherPost", urlPatterns = "/afficherPost")
public class afficherPost extends HttpServlet{

	private Gson gson = new Gson();
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		 response.setContentType("text/html");
	      response.setCharacterEncoding("UTF-8");
	      
	      	String filDActu, pseudo;
			FollowersFollowees follow = new FollowersFollowees();
			Entity user;
			Query query;
			Collection<Filter> filters;
			Filter filter;
			Entity userEntit;
			List<Entity>  postEntity;
			Users userid;
			DatastoreService ds ;
			
			
			//System.out.println("coucou, on cree la fil d'actu");

			pseudo = request.getParameter("pseudo");
			
			//init de la fil d'actu a une chaine vide
			filDActu = "";
			 
			ds= DatastoreServiceFactory.getDatastoreService();
	        filter = new Query.FilterPredicate("pseudo", Query.FilterOperator.EQUAL, pseudo);
	        query = new Query("User").setFilter(filter);
	        user = ds.prepare(query).asSingleEntity();
	        userid = follow.getUser(pseudo);
	        
	        //System.out.println("--> afficherPost get "+user.getProperty("pseudo"));
	        
	        
			//System.out.println("on test pour trouver le pseudo dans l'un des list ");
			
	        query = new Query("UserFollowers");
	        postEntity = ds.prepare(query).asList(FetchOptions.Builder.withDefaults());
	        ArrayList<String> follows;
	        System.out.println("taille de postEntity "+ postEntity.size());
	        ArrayList<String> listAAfficher = new ArrayList<String>();
	        //listAAfficher.add(user.getKey());

	        for (Entity entity : postEntity) {
	        	follows = (ArrayList<String> )entity.getProperty("followers");
	        	//parcours de la list des receivers à la recherche du nom de l'user
	        	
	        	//System.out.println(entity.getProperty("followers"));
	        	
	        	if (follows.contains(userid.getPseudo())) {
	        		listAAfficher.add(follows.get(0));
	        		System.out.println("on est la " + listAAfficher.get(0));
	        		System.out.println("ajout d'un Post dans listAAficher");
	        	}
	        	
	        	
	        	//faire une methode qui permet de comparer un objet avec le pseudo de l'uilisateur
	        }
	        
	        	String nameAuthor = "";
	    		filters = new ArrayList<Filter>();
	    		System.out.println("avant for followees de taille "+ listAAfficher.size() + "  " + listAAfficher);
	        	for (String followees : listAAfficher) {
	        		nameAuthor = followees;
	        		System.out.println(" on est ici" + nameAuthor);
	        		filters.add(new Query.FilterPredicate("nameAuthor", FilterOperator.EQUAL, followees));
	        	}
	        	
	        	if (filters.size() > 1 ) {
	        		System.out.println("if");
	    			filter = new Query.CompositeFilter(CompositeFilterOperator.OR, filters);
	    		} else { // cas ou il n'y a qu'un utilisateur dans la base de donnée
	    			System.out.println("else");
	    			filter = new Query.FilterPredicate("nameAuthor", FilterOperator.EQUAL, nameAuthor);
	    		}
	    		query = new Query("Post").setFilter(filter);
	    		postEntity = ds.prepare(query).asList(FetchOptions.Builder.withDefaults());
	    		
	    		System.out.println("list des post " + postEntity.size());
	        	//query = new Query("User");
	    		//List<Entity> post = ds.prepare(query).asList(FetchOptions.Builder.withDefaults());
	    		/*for (Entity ent : post) {
	    			/*System.out.println("post key "+ ent.getKey().getId());
	    			if(listAAfficher.contains(ent.getKey().getId())) {
	    				userid = follow.getUser((String) ent.getProperty("pseudo"));
	    				filDActu += affichageDesPosts( userid, ds);
	    				System.out.println("cle egale pour pseudo "+ ent.getProperty("pseudo"));
	    		}*
    			nameAuthor = (String) ent.getProperty("pseudo");
    			System.out.println(nameAuthor);

	    		filters.add(new Query.FilterPredicate("nameAuthor", FilterOperator.EQUAL,nameAuthor));
	    		
	        }
	    		System.out.println(filters.size());
	    		if (filters.size() > 1 ) {
	    			filter = new Query.CompositeFilter(CompositeFilterOperator.OR, filters);
	    		} else { // cas ou il n'y a qu'un utilisateur dans la base de donnée
	    			System.out.println("else");
	    			filter = new Query.FilterPredicate("nameAuthor", FilterOperator.EQUAL, nameAuthor);
	    		}
	    		query = new Query("Post").setFilter(filter);
	    		postEntity = ds.prepare(query).asList(FetchOptions.Builder.withDefaults());
	    		*/
	    		System.out.println("list des post " + postEntity.size());
	    		
	    	
	        
	        
			//System.out.println(filDActu);
			String jsonPost = this.gson.toJson(postEntity);
			response.getWriter().print(jsonPost);
	      //request.getRequestDispatcher("/WEB-INF/createUserTest.jsp").forward(request, response);
	   }


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {	
	}

}
