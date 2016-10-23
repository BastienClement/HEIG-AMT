package web;

import models.User;
import services.UserServiceLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	@EJB
	private UserServiceLocal users;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Optional<User> user = users.findByMail(email).filter(u -> u.getPassword().equals(password));
		if(user.isPresent()) {
			request.setAttribute("test", "test");
			response.sendRedirect("admin");
		}
		else
			response.sendRedirect("");
		request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}
}
