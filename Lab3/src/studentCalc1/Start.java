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
		// intialize
		response.setContentType("text/plain");
		Writer resOut = response.getWriter();
		String queryPrincipal = request.getParameter("principal");
		String queryPeriod = request.getParameter("period");
		String queryInterest = request.getParameter("interest");
		int principal = Integer.parseInt(getServletContext().getInitParameter("principal"));
		int period = Integer.parseInt(getServletContext().getInitParameter("period"));
		double interest = Double.parseDouble(getServletContext().getInitParameter("interest"));
		double fixedInterest = Double.parseDouble(getServletContext().getInitParameter("fixedInterest"));
		double gracePeriod = Double.parseDouble(getServletContext().getInitParameter("gracePeriod"));
		double monthlyPayment = 0.0;
		double graceInterest = 0.0;
		double graceMonthlyPayment = 0.0;
		int graceChecked = 0;

		//check session/parameters and update variables affected
		if(request.getSession().getAttribute(GRACE_CHECKED) != null){graceChecked = (int) request.getSession().getAttribute(GRACE_CHECKED);}
		if(request.getSession().getAttribute(PRINCIPAL) != null){principal = (int) request.getSession().getAttribute(PRINCIPAL);}
		if(request.getSession().getAttribute(INTEREST) != null){interest = (double) request.getSession().getAttribute(INTEREST);}
		if(request.getSession().getAttribute(PERIOD) != null){period = (int) request.getSession().getAttribute(PERIOD);}
		if (queryPrincipal != null){principal = Integer.parseInt(queryPrincipal);}
		if (queryPeriod != null){period = Integer.parseInt(queryPeriod);}
		if (queryInterest != null){interest = Double.parseDouble(queryInterest);}

		//calculate formula
		monthlyPayment = (((interest/100) / 12) * principal) / 
						 (1 - (Math.pow(1 + ((interest/100) / 12), -period)));
		monthlyPayment = round(monthlyPayment,1);
		graceInterest = principal * ((interest/100 + fixedInterest/100) / 12) * gracePeriod;
		graceMonthlyPayment = monthlyPayment + (graceInterest/ gracePeriod);

		
		if(request.getParameter("submit") == null)
		{
			request.getRequestDispatcher(startPage).forward(request,response);
		}
		else if(request.getParameter("submit").equals("Submit")) // click submit
		{
			graceChecked = isChecked(request);
			if(graceChecked == 0)
			{
				graceInterest = interest;
				graceMonthlyPayment = monthlyPayment;
			}
			request.setAttribute(GRACE_PERIOD_INTEREST, round(graceInterest, 1));
			request.setAttribute(MONTHLY_PAYMENTS,round(graceMonthlyPayment, 1));
			request.getSession().setAttribute(INTEREST, interest);
			request.getSession().setAttribute(PRINCIPAL, principal);
			request.getSession().setAttribute(PERIOD, period);
			request.getSession().setAttribute(GRACE_CHECKED, graceChecked);

			request.getRequestDispatcher(resultPage).forward(request,response); //forward to the result page
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
	private static int isChecked(HttpServletRequest request)
	{
		int x = 0;
		if(request.getParameter("graceEnabled")==null)
		{
			x = 0;
		}
		else if(request.getParameter("graceEnabled").equals("on"))
		{
			x = 1;
		}
		return x;
	}
}
