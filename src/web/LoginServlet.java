package web;

import services.UserServiceLocal;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	@EJB
	private UserServiceLocal users;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		request.setAttribute("email", email);
		if(email == null || email.isEmpty())
			request.setAttribute("message", "Email can't be null");
		else if(password == null || password.isEmpty())
			request.setAttribute("message", "Password can't be null");
		else if(!users.findByMail(email).filter(u -> u.getPassword().equals(password)).isPresent())
			request.setAttribute("message", "Email or password is incorrect");
		else {
			HttpSession session = request.getSession(true);
			session.setAttribute("email", email);
			response.sendRedirect("admin");
		}
		request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
	}
}
