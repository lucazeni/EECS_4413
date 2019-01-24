package studentCalc1;

import java.io.IOException;
import java.io.Writer;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
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
	String startPage="/UI.jspx";
	String resultPage="/Result.jspx";
	private static final String GRACE_PERIOD_INTEREST = "gracePeriodInterest";
	private static final String MONTHLY_PAYMENTS = "monthlyPayments";
	private static final String PRINCIPAL = "principal";
	private static final String INTEREST = "interest";
	private static final String PERIOD = "period";
	private static final String GRACE_CHECKED = "graceChecked";
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

		response.setContentType("text/plain");
		Writer resOut = response.getWriter();
		String queryPrincipal = request.getParameter("principal");
		String queryPeriod = request.getParameter("period");
		String queryInterest = request.getParameter("interest");
		int principal_z = Integer.parseInt(getServletContext().getInitParameter("principal"));
		int period_z = Integer.parseInt(getServletContext().getInitParameter("period"));
		double interest_z = Double.parseDouble(getServletContext().getInitParameter("interest"));
		double fixedInterest = Double.parseDouble(getServletContext().getInitParameter("fixedInterest"));
		double gracePeriod = Double.parseDouble(getServletContext().getInitParameter("gracePeriod"));
		double monthlyPayment = 0.0;
		double graceInterest = 0.0;
		double graceMonthlyPayment = 0.0;
		int graceChecked_z = 0;


		if(request.getSession().getAttribute(GRACE_CHECKED) != null){graceChecked_z = (int) request.getSession().getAttribute(GRACE_CHECKED);}
		if(request.getSession().getAttribute(PRINCIPAL) != null){principal_z = (int) request.getSession().getAttribute(PRINCIPAL);}
		if(request.getSession().getAttribute(INTEREST) != null){interest_z = (double) request.getSession().getAttribute(INTEREST);}
		if(request.getSession().getAttribute(PERIOD) != null){period_z = (int) request.getSession().getAttribute(PERIOD);}
		if (queryPrincipal != null){principal_z = Integer.parseInt(queryPrincipal);}
		if (queryPeriod != null){period_z = Integer.parseInt(queryPeriod);}
		if (queryInterest != null){interest_z = Double.parseDouble(queryInterest);}




		monthlyPayment = (((interest_z/100) / 12) * principal_z) / 
				(1 - (Math.pow(1 + ((interest_z/100) / 12), -period_z)));
		monthlyPayment = round(monthlyPayment,1);
		graceInterest = principal_z * ((interest_z/100 + fixedInterest/100) / 12) * gracePeriod;
		graceMonthlyPayment = monthlyPayment + (graceInterest/ gracePeriod);

		if(request.getParameter("graceEnabled") == null)
		{
			graceInterest = interest_z;
			graceMonthlyPayment = monthlyPayment;

		}
		request.setAttribute(GRACE_PERIOD_INTEREST, round(graceInterest, 1));
		request.setAttribute(MONTHLY_PAYMENTS,round(graceMonthlyPayment, 1));
		request.getSession().setAttribute(INTEREST, interest_z);
		request.getSession().setAttribute(PRINCIPAL, principal_z);
		request.getSession().setAttribute(PERIOD, period_z);

		if(request.getParameter("submit") == null)
		{
			request.getRequestDispatcher(startPage).forward(request,response);
		}
		else if(request.getParameter("submit").equals("Submit"))
		{
			if(request.getParameter("graceEnabled")==null)
			{
				graceChecked_z = 0;
			}
			else if(request.getParameter("graceEnabled").equals("on"))
			{
				graceChecked_z = 1;
			}
			request.getSession().setAttribute(GRACE_CHECKED, graceChecked_z);
			request.getRequestDispatcher(resultPage).forward(request,response);
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
