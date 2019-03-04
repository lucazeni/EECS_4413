package ctrl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import DAO.StudentDAO;
import bean.StudentBean;
import model.SIS;

/**
 * Servlet implementation class Test
 */
@WebServlet("/Start")
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String startPage = "/Form.jspx";
	String donePage = "/Done.jspx";
	private static final String FILENAME = "fileName"; 
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	private SIS sis;
	private String surname;
	private String minCredit;
	private Map<String, StudentBean> studentList;
	private String id;
	private String name;
	private int creditsTaken;
	private int creditsToGraduate;

	public void init() throws ServletException {
		try {
			this.sis = new SIS();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Start() throws ServletException {
		this.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter responseWriter = response.getWriter();
		if (request.getParameter("submit") != null && request.getParameter("submit").equals("ajax")) {
			surname = request.getParameter("surname");
			minCredit = request.getParameter("minCredit");
			try {

				studentList = sis.retrieveStudents(surname, minCredit);
				responseWriter.println("<table border='1'>");
				responseWriter.println("<tr>");
				responseWriter.println("<td>Id</td>");
				responseWriter.println("<td>Name</td>");
				responseWriter.println("<td>Credits taken</td>");
				responseWriter.println("<td>Credits to graduate</td>");
				responseWriter.println("</tr>");

				Collection<StudentBean> sbean = studentList.values();
				Iterator<StudentBean> studentIterator = sbean.iterator();

				while (studentIterator.hasNext()) {
					StudentBean item = studentIterator.next();
					id = item.getSid();
					name = item.getName();
					creditsTaken = item.getCredit_taken();
					creditsToGraduate = item.getCredit_graduate();
					responseWriter.println("<tr>");
					responseWriter.print("<td>" + id + "</td>");
					responseWriter.print("<td>" + name + "</td>");
					responseWriter.print("<td>" + creditsTaken + "</td>");
					responseWriter.print("<td>" + creditsToGraduate + "</td>");
					responseWriter.println("</tr>");
				}

			} catch (Exception e) {
				if (surname.equals("") || surname.contains(" ")) {
					responseWriter.print("<p><font color=\"red\">" + "Illegal 'Name Prefix' Entry" + "</font></p>");

				} else if (minCredit.contains(".") || minCredit.equals("") || Integer.parseInt(minCredit) < 0) {
					responseWriter
							.print("<p><font color=\"red\">" + "Illegal 'Minimum Credit Taken' Entry" + "</font></p>");

				}

			}

		} else if (request.getParameter("genXML") != null && request.getParameter("genXML").equals("Generate XML")) {
			String f = "export/" + request.getSession().getId() + ".xml";
			String filename = this.getServletContext().getRealPath("/" + f);
			
			try {
				surname = request.getParameter("surname");
				minCredit = request.getParameter("minCredit");
				sis.export(this.surname, this.minCredit, filename);
				request.getSession().setAttribute(FILENAME, filename);
				request.getRequestDispatcher(donePage).forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//
		} else if (request.getParameter("report") == null && request.getParameter("submit") == null) {

			request.getRequestDispatcher(startPage).forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
