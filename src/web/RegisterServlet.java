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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	@EJB
	private UserServiceLocal users;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		request.setAttribute("firstname", firstName);
		request.setAttribute("lastname", lastName);
		request.setAttribute("email", email);

		if(firstName == null || firstName.isEmpty())
			request.setAttribute("message", "First name can't be null");
		else if(lastName == null || lastName.isEmpty())
			request.setAttribute("message", "Last name can't be null");
		else if(email == null || email.isEmpty())
			request.setAttribute("message", "Email can't be null");
		else if(users.findByMail(email).isPresent())
			request.setAttribute("message", "Email is already used");
		else if(!email.contains("@"))
			request.setAttribute("message", "Wrong email format");
		else if(password == null || password.length() < 4)
			request.setAttribute("message", "Password must have at least 4 characters");
		else{
			User nUser = new User(
					0,
					firstName,
					lastName,
					email,
					password
			);
			int id = users.createUser(nUser);
			if (id == -1)
				request.setAttribute("message", "Creation failed");
			else
				response.sendRedirect("");
		}
		request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
	}
}
