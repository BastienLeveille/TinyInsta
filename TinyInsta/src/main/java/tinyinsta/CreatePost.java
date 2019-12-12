package tinyinsta;

import java.io.IOException;
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
import com.google.appengine.api.datastore.Query.Filter;

@WebServlet(name = "createPost", urlPatterns = "/createPost")
public class CreatePost extends HttpServlet {


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {	
		String post, pseudo;
		FollowersFollowees follow = new FollowersFollowees();

		System.out.println("Post creation");
		post = request.getParameter("post");
		pseudo = request.getParameter("pseudo");
		System.out.println("post "+ post + " by "+ pseudo);

		follow.createPost(pseudo, post);
	}

}