package web;

import services.UserServiceLocal;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class AdminFilter implements Filter  {
	@EJB
	private UserServiceLocal users;

	private ArrayList<String> admin_urls;

	public void init(FilterConfig config) throws ServletException {
		String urls = config.getInitParameter("admin-urls");
		StringTokenizer token = new StringTokenizer(urls, ",");

		admin_urls = new ArrayList<String>();

		while (token.hasMoreTokens()) {
			admin_urls.add(token.nextToken());
		}
	}

	public void  doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String url = req.getServletPath();

		System.out.println(url);
		System.out.println(req.getSession(false));
		if(admin_urls.contains(url)){
			HttpSession session = req.getSession(false);
			if(session == null || session.getAttribute("email") == null ||
					((String)session.getAttribute("email")).isEmpty() || !users.findByMail((String)session
					.getAttribute("email")).isPresent())
				resp.sendRedirect("");
		}

		chain.doFilter(request, response);
	}
	public void destroy( ){
	}
}