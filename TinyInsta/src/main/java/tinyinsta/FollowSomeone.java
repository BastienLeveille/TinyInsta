package tinyinsta;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( name ="followSomeone", urlPatterns = "/followSomeone")
public class FollowSomeone extends HttpServlet {

	
	@Override
	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		  response.setContentType("text/html");
	      response.setCharacterEncoding("UTF-8");
	      
	      String follow, pseudo;
		  FollowersFollowees follower = new FollowersFollowees();
			
		  pseudo = request.getParameter("pseudo");
		  follow = request.getParameter("user");
		  follower.followUser(pseudo, follow);
	      request.getRequestDispatcher("/WEB-INF/Test.jsp").forward(request, response);
	   }
	
	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    		throws IOException, ServletException {	
		
	    String follow, pseudo;
	    FollowersFollowees follower = new FollowersFollowees();
		
	    pseudo = request.getParameter("pseudo");
	    follow = request.getParameter("user");
	    follower.followUser(follow, pseudo);
	}
}
