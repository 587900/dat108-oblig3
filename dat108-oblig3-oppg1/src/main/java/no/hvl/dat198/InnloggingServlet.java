package no.hvl.dat198;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class InnloggingServlet
 */
@WebServlet("/login")
public class InnloggingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
        // Inn noe kode her i forbindelse med evt. feilmeldinger?
    	String feilmelding = "";
		String feilkode = request.getParameter("feilkode");
		if (feilkode != null && feilkode.equals("invalidusername")) {
			feilmelding = "Ugyldig...";
		}
        
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>Log inn</title>");
        out.println("</head>");
        out.println("<body>");
        	out.println("<form method=\"post\">");
        	out.println("Gi inn passord:<input type=\"password\" name=\"password\"/><br/>");
        	out.println("<input type=\"submit\" value=\"Logg inn\"/>");
        	out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }
		
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean success = LogInnUtil.loggInn(request, request.getParameter("password"));
		
		if(success) response.sendRedirect("/handleliste");
		else response.sendRedirect("/login?error=wrongpassword");
		
		doGet(request, response);
	}

}
