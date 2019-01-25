package ctrl;

import java.io.IOException;
import java.io.Writer;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Loan;
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
	private static final String LEGEND_NAME = "legendName";
	private static final String MODEL = "model";
	private Loan loan;
	int principal;
	int period;
	double interest;
	double fixedInterest;
	double gracePeriod;
	double monthlyPayment;
	double graceInterest;
	int graceChecked;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Start() {
		super();
		// TODO Auto-generated constructor stub
	}
	public void init() throws ServletException{
		loan = new Loan();
		getServletContext().setAttribute(MODEL, loan);
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
		principal = Integer.parseInt(getServletContext().getInitParameter("principal"));
		period = Integer.parseInt(getServletContext().getInitParameter("period"));
		interest = Double.parseDouble(getServletContext().getInitParameter("interest"));
		fixedInterest = Double.parseDouble(getServletContext().getInitParameter("fixedInterest"));
		gracePeriod = Double.parseDouble(getServletContext().getInitParameter("gracePeriod"));
		monthlyPayment = 0.0;
		graceInterest = 0.0;
		graceChecked = 0;

		//check session/parameters and update variables affected
		if(request.getSession().getAttribute(GRACE_CHECKED) != null){graceChecked = (int) request.getSession().getAttribute(GRACE_CHECKED);}
		if(request.getSession().getAttribute(PRINCIPAL) != null){principal = (int) request.getSession().getAttribute(PRINCIPAL);}
		if(request.getSession().getAttribute(INTEREST) != null){interest = (double) request.getSession().getAttribute(INTEREST);}
		if(request.getSession().getAttribute(PERIOD) != null){period = (int) request.getSession().getAttribute(PERIOD);}
		if (queryPrincipal != null){principal = Integer.parseInt(queryPrincipal);}
		if (queryPeriod != null){period = Integer.parseInt(queryPeriod);}
		if (queryInterest != null){interest = Double.parseDouble(queryInterest);}

		if(request.getParameter("submit") == null)
		{
			request.getServletContext().setAttribute(LEGEND_NAME, "Student Loan Calculator");
			request.getSession().setAttribute(INTEREST, interest);
			request.getSession().setAttribute(PRINCIPAL, principal);
			request.getSession().setAttribute(PERIOD, period);
			request.getRequestDispatcher(startPage).forward(request,response);
		}
		else if(request.getParameter("submit").equals("Submit")) // click submit
		{

			graceChecked = isChecked(request);
			try 
			{
				graceInterest = loan.computeGraceInterest(principal, gracePeriod, interest, fixedInterest, graceChecked, period);
				monthlyPayment = loan.computePayment(principal, interest, period, fixedInterest, graceChecked, graceInterest, gracePeriod);
			}
			catch(Exception e)
			{
				System.out.println("error");
			}
			setAttributes(request);
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
	public void setAttributes(HttpServletRequest request)
	{
		request.setAttribute(GRACE_PERIOD_INTEREST, graceInterest);
		request.setAttribute(MONTHLY_PAYMENTS,round(monthlyPayment,1));
		request.getSession().setAttribute(INTEREST, interest);
		request.getSession().setAttribute(PRINCIPAL, principal);
		request.getSession().setAttribute(PERIOD, period);
		request.getSession().setAttribute(GRACE_CHECKED, graceChecked);
	}

	private static double round (double value, int precision) 
	{
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