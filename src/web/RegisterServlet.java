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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	@EJB
	private UserServiceLocal users;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Optional<User> user = users.findByMail(request.getParameter("password"));
		if(!user.isPresent()) {
			User nUser = new User(
					0,
					request.getParameter("firstname"),
					request.getParameter("lastname"),
					request.getParameter("password"),
					request.getParameter("password")
			);
			int id = users.createUser(nUser);
			if (id != -1)
				response.sendRedirect("admin");
			else
				response.sendRedirect("");
		}else{
			response.sendRedirect("");
		}
		request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
	}
}
