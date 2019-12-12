package tinyinsta;

import java.util.List;
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
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;

@WebServlet(name = "login", urlPatterns = "/login")
public class login extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		request.getRequestDispatcher("/WEB-INF/connexion.jsp").forward(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {	

		String pseudo, mdp;
		DatastoreService ds;
		Collection<Filter> filters;
		Filter filter;
		Query query;
		Entity user;

		pseudo = request.getParameter("pseudo");
		mdp = request.getParameter("mdp");

		ds = DatastoreServiceFactory.getDatastoreService();
		filters = new ArrayList <Filter>();
		filters.add(new Query.FilterPredicate("pseudo", Query.FilterOperator.EQUAL, pseudo));
		filters.add(new Query.FilterPredicate("mdp", Query.FilterOperator.EQUAL, mdp));
		filter = new Query.CompositeFilter(CompositeFilterOperator.AND, filters);
		query = new Query("User").setFilter(filter);
		System.out.println("query ok");
		user = ds.prepare(query).asSingleEntity();
		System.out.println(user.getProperty("pseudo")+ " " + user.getProperty("mdp"));

		response.getWriter().write("pomm");
	}
}