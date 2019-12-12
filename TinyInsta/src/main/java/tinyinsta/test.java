package tinyinsta;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "test", urlPatterns = "/test")
public class test extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		request.getRequestDispatcher("/WEB-INF/Test.jsp").forward(request, response);
	}

}
