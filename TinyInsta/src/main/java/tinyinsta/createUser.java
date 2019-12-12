package tinyinsta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.repackaged.com.google.datastore.v1.client.Datastore;

@WebServlet(name = "createUser", urlPatterns = "/createUser")


public class createUser extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		request.getRequestDispatcher("/WEB-INF/createUserTest.jsp").forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {	
		String pseudo, mdp, mail, nom, prenom;
		Entity user;
		Query query;
		Collection<Filter> filters;
		Filter filter;
		Entity userFollowers;
		FollowersFollowees follow;

		pseudo = request.getParameter("pseudo");
		mdp = request.getParameter("mdp");
		mail = request.getParameter("mail");
		nom = request.getParameter("nom");
		prenom = request.getParameter("prenom");

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		filters = new ArrayList <Filter>();
		filters.add(new Query.FilterPredicate("pseudo", Query.FilterOperator.EQUAL, pseudo));
		filters.add(new Query.FilterPredicate("mail", Query.FilterOperator.EQUAL, mail));
		filter = new Query.CompositeFilter(CompositeFilterOperator.OR, filters);
		query = new Query("User").setFilter(filter);
		user = ds.prepare(query).asSingleEntity();

		if (user != null){
			throw new IllegalStateException("User already exist");
		} else {
			user = new Entity("User");
			user.setIndexedProperty("pseudo", pseudo);
			user.setProperty("mail", mail);
			user.setProperty("mdp", mdp);
			user.setProperty("nom", nom);
			user.setProperty("prenom", prenom);

			ds.put(user);
			userFollowers = new Entity("UserFollowers",user.getKey());
			userFollowers.setProperty("followers", new ArrayList<Long>());

			ds.put(userFollowers);
			follow = new FollowersFollowees();
			follow.addFollowerUser(pseudo);

		}    
		response.getWriter().write("pomm");
	}
}
