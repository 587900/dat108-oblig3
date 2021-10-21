package no.hvl.dat108;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginUtil {

	private static String correctPassword;
	private static int maxInactiveInterval;
	
	static {
		try {
			Context ctx = new InitialContext();
			Context env = (Context) ctx.lookup("java:comp/env");
			correctPassword = (String) env.lookup("plaintext-password");
			maxInactiveInterval = (int) env.lookup("max-inactive-interval");
		} catch (NamingException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	// Returns true if login success, false otherwise
	public static boolean loggInn(HttpServletRequest request, String password) {
		loggUt(request);
		
		if(correctPassword.equals(password)) {
			HttpSession sesjon = request.getSession(true);
			sesjon.setAttribute("logged_in", true);
			// TODO: Dette skal inn i web,xml som init-parameter
			sesjon.setMaxInactiveInterval(maxInactiveInterval);
			return true;
		}
		return false;
	}

	public static void loggUt(HttpServletRequest request) {
		HttpSession sesjon = request.getSession(false);
		if (sesjon != null) sesjon.invalidate();
	}
	
	public static boolean erInnlogget(HttpServletRequest request) {
		HttpSession sesjon = request.getSession(false);
		return sesjon != null && sesjon.getAttribute("logged_in") != null;
	}

}
