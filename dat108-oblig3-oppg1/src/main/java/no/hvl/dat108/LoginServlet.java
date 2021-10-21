package no.hvl.dat108;

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
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
       
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>Logg inn</title>");
        out.println("</head>");
        out.println("<body>");
        	out.println("<form method=\"post\">");
        	if("wrongpassword".equals(request.getParameter("error"))) {
        		out.println("Passordet du skrev inn var feil. Prøv igjen.<br/>");
        	} else { out.println("Gi inn passord:<br/>"); }
        	out.println("<input type=\"password\" name=\"password\"/><br/>");
        	out.println("<input type=\"submit\" value=\"Logg inn\"/>");
        	out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }
		
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean success = LoginUtil.loggInn(request, request.getParameter("password"));
		
		if(success) response.sendRedirect("/handleliste");
		else response.sendRedirect("/login?error=wrongpassword");
	}

}
