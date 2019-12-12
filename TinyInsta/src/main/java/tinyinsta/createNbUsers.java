package tinyinsta;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;

@WebServlet(name="nbUsers", urlPatterns = "/nbUsers")
public class createNbUsers extends HttpServlet {


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		String nbfollower;
		String followed;
		FollowersFollowees follow = new FollowersFollowees();

		nbfollower = request.getParameter("nbUsers");
		System.out.println(nbfollower);
		followed = request.getParameter("pseudo");

		follow.createNbFollowers(Integer.valueOf(nbfollower), followed);

		response.getWriter().print("");
	}

}
