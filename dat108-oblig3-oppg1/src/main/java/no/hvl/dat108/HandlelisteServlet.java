package no.hvl.dat108;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HandlelisteServlet
 */
@WebServlet("/handleliste")
public class HandlelisteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Handleliste handleliste = new Handleliste();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if(!LoginUtil.erInnlogget(request)) { response.sendRedirect("/login"); return; }
		
		response.setContentType("text/html; charset=utf-8");

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>Min handleliste</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h3>Min handleliste</h3>");

        	out.println("<form method=\"post\">");
        	out.println("<input type=\"submit\" value=\"Legg til\"/>");
        	out.println("<input type=\"text\" name=\"product\"/><br/>");
        	out.println("<input type=\"hidden\" name=\"type\" value=\"add\"/>");
        	out.println("</form>");
        	
        	out.println("<form method=\"post\">");
        	out.println("<input type=\"hidden\" name=\"type\" value=\"remove\"/>");
        	Collection <Entry<Integer,String>> varer = handleliste.hentListe();
        	for(Entry<Integer,String> entry : varer) {
        		out.println("<button type=\"submit\" name=\"id\" value=\"" + entry.getKey()
        		+ "\">Slett</button>" + entry.getValue() + "<br/>");
        	}
        	out.println("</form>");
        	
        out.println("</body>");
        out.println("</html>");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!LoginUtil.erInnlogget(request)) { response.sendRedirect("/login"); return; }

		if(!isValidPostInput(request)) { response.sendError(400); return; }
		
		switch (request.getParameter("type")) {
		case "add":
			String product = request.getParameter("product");
			product = escapeHTML(product);
			if(!product.equals("")) handleliste.leggTil(product);
			break;
		case "remove":
			int id = Integer.parseInt(request.getParameter("id"));
			handleliste.fjern(id);			
			break;
		default: throw new RuntimeException("Type not add or remove: " + request.getParameter("type"));
		}
		
		response.sendRedirect("/handleliste");
	}
	
	/**
	 * Checks requests parameters
	 * "type" must not be null, but must be "add" or "remove".
	 * If "type" is "add", then "product" must be not-null (any string).
	 * If "type" is "remove", then "id" must not be null, and must be an integer.
	 * @param request
	 * @return true if valid by definition above, false otherwise.
	 */
	private boolean isValidPostInput(HttpServletRequest request) {
		String type = request.getParameter("type");
		if(type == null) return false;
		if(!type.equals("add") && !type.equals("remove")) return false;
		if(type.equals("add") && request.getParameter("product") == null) return false;
		if(type.equals("remove")) {
			String id = request.getParameter("id");
			if(id == null) return false;
			try { Integer.parseInt(id); } catch (NumberFormatException e) { return false; }
		}
		return true;
	}
	
//	public static String escapeHTML(String s) {
//	    StringBuilder out = new StringBuilder();
//	    for (int i = 0; i < s.length(); i++) {
//	        char c = s.charAt(i);
//	        if (c > 127 || c == '"' || c == '\'' || c == '<' || c == '>' || c == '&') {
//	            out.append("&#");
//	            out.append((int) c);
//	            out.append(';');
//	        } else {
//	            out.append(c);
//	        }
//	    }
//	    return out.toString();
//	}
	// TODO KJETIL: Test med Unicode Symbols
	public static String escapeHTML(String str) {
	    return str.codePoints().mapToObj(c -> c > 127 || "\"'<>&".indexOf(c) != -1 ?
	            "&#" + Integer.toHexString((int) c) + ";" : new String(Character.toChars(c)))
	       .collect(Collectors.joining());
	}
}
