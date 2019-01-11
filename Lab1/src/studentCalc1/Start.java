package studentCalc1;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Start
 */
@WebServlet({"/Start", "/Startup/*"})
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Start() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println("Hello, Got a Get Request");
		
		response.setContentType("text/plain");
		Writer resOut = response.getWriter();
		
		String clientIP = request.getRemoteAddr();
		int clientPort = request.getRemotePort();
		String clientQueryString = request.getQueryString();
		String clientProtocol = request.getProtocol();
		String clientMethod = request.getMethod();
		String URI = request.getRequestURI();
		String context = request.getContextPath();
		String servletPath = this.getServletContext().getContextPath();
		String foo = request.getParameter("foo");
		String queryPrincipal = request.getParameter("principal");
		String queryPeriod = request.getParameter("period");
		String queryInterest = request.getParameter("interest");
		String queryString = request.getQueryString();
		String invalidPath = "/Startup/YorkBank";
		double principal = Double.parseDouble(getServletContext().getInitParameter("principal"));
		double period = Double.parseDouble(getServletContext().getInitParameter("period"));
		double interest = Double.parseDouble(getServletContext().getInitParameter("interest"));
		double monthlyPayment = 0.0;
		
	  //if (clientIP == 0:0:0:0:0:0:0:1)
	  //{
	  //	block()
	  //}
		if (queryPrincipal != null){
			principal = Double.parseDouble(queryPrincipal);
		}
		if (queryPeriod != null){
			period = Double.parseDouble(queryPeriod);
		}
		if (queryInterest != null){
			interest = Double.parseDouble(queryInterest);
		}

		monthlyPayment = (((interest/100) / 12) * principal) / 
					     (1 - (Math.pow(1 + ((interest/100) / 12), -period)));
		monthlyPayment = round(monthlyPayment,1);
		
		resOut.write("Hello, World!\n");
		resOut.write("Client IP: " + clientIP + "\n");
		resOut.write("Client Port: " + clientPort + "\n");
		resOut.write("This IP has been flagged!" + "\n");
		resOut.write("Client Protocol: " + clientProtocol + "\n");
		resOut.write("Client Method: " + clientMethod + "\n");
		resOut.write("Query String: " + queryString + "\n");
		resOut.write("Query Param foo=" + foo + "\n");
		resOut.write("Request URI: " + URI + "\n");
	  //resOut.write("Context: " + context + "\n");
		resOut.write("Request Servlet Path: " + servletPath + "\n");
		resOut.write("---- Monthly payments ----" + "\n");
		resOut.write("Based on Principal=" + principal + " Period=" + period + " Interest=" + interest);
		resOut.write("\n" + "Monthly payments: " + monthlyPayment);
	
		if (URI.contains(invalidPath))
		{
			response.sendRedirect(this.getServletContext().getContextPath() + "/Start");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private static double round (double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (double) Math.round(value * scale) / scale;
	}
}
