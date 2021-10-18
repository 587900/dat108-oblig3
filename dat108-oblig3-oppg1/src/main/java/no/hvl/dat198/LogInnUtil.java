package no.hvl.dat198;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogInnUtil {

	private static String correctPassword = "dat108";
	
	// Returns true if login success, false otherwise
	public static boolean loggInn(HttpServletRequest request, String password) {
		loggUt(request);
		
		if(correctPassword.equals(password)) {
			HttpSession sesjon = request.getSession(true);
			sesjon.setAttribute("logged_in", true);
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
