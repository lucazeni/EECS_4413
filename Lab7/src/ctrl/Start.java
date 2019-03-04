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
	private static final String FILEPATH = "filePath";
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
				Collection<StudentBean> sbean = studentList.values();
				Iterator<StudentBean> studentIterator = sbean.iterator();
				responseWriter.println("<tr>");
				responseWriter.println("<td>There are " + sbean.size() + " entries." + "</td>");
				responseWriter.println("</tr>");
				responseWriter.println("<table border='1'>");
				responseWriter.println("<tr>");
				responseWriter.println("<td>Id</td>");
				responseWriter.println("<td>Name</td>");
				responseWriter.println("<td>Credits taken</td>");
				responseWriter.println("<td>Credits to graduate</td>");
				responseWriter.println("</tr>");

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
				if (surname.equals("")) {
					responseWriter.print("<p><font color=\"red\">" + "Illegal 'Name Prefix' Entry: Cannot Be Blank!"
							+ "</font></p>");

				} else if (containsDigit(surname)) {
					responseWriter.print("<p><font color=\"red\">"
							+ "Illegal 'Name Prefix' Entry: Cannot Contain Numbers!" + "</font></p>");

				} else if (!isAlpha(surname)) {
					responseWriter.print("<p><font color=\"red\">"
							+ "Illegal 'Name Prefix' Entry: Cannot Contain Special Characters!" + "</font></p>");

				}

				else if (minCredit.contains(".")) {
					responseWriter.print("<p><font color=\"red\">"
							+ "Illegal 'Minimum Credit Taken' Entry: Must be Integer Value!" + "</font></p>");

				} else if (minCredit.equals("")) {
					responseWriter.print("<p><font color=\"red\">"
							+ "Illegal 'Minimum Credit Taken' Entry: Cannot Be Blank!" + "</font></p>");

				} else if (Integer.parseInt(minCredit) < 0) {
					responseWriter.print("<p><font color=\"red\">"
							+ "Illegal 'Minimum Credit Taken' Entry: Cannot Be Less Than Zero!" + "</font></p>");

				}

			}

		} else if (request.getParameter("genXML") != null && request.getParameter("genXML").equals("Generate XML")) {
			String f = "export/" + request.getSession().getId() + ".xml";
			String filename = this.getServletContext().getRealPath("/" + f);

			try {
				surname = request.getParameter("surname");
				minCredit = request.getParameter("minCredit");
				sis.export(this.surname, this.minCredit, filename);
				request.getSession().setAttribute(FILENAME, f);
				request.getSession().setAttribute(FILEPATH, filename);
				request.getRequestDispatcher(donePage).forward(request, response);
			} catch (Exception e) {
				if (surname.equals("")) {
					responseWriter.print("<p><font color=\"red\">" + "Illegal 'Name Prefix' Entry: Cannot Be Blank!"
							+ "</font></p>");

				} else if (containsDigit(surname)) {
					responseWriter.print("<p><font color=\"red\">"
							+ "Illegal 'Name Prefix' Entry: Cannot Contain Numbers!" + "</font></p>");

				} else if (!isAlpha(surname)) {
					responseWriter.print("<p><font color=\"red\">"
							+ "Illegal 'Name Prefix' Entry: Cannot Contain Special Characters!" + "</font></p>");
				} else if (minCredit.contains(".")) {
					responseWriter.print("<p><font color=\"red\">"
							+ "Illegal 'Minimum Credit Taken' Entry: Must be Integer Value!" + "</font></p>");

				} else if (minCredit.equals("")) {
					responseWriter.print("<p><font color=\"red\">"
							+ "Illegal 'Minimum Credit Taken' Entry: Cannot Be Blank!" + "</font></p>");

				} else if (Integer.parseInt(minCredit) < 0) {
					responseWriter.print("<p><font color=\"red\">"
							+ "Illegal 'Minimum Credit Taken' Entry: Cannot Be Less Than Zero!" + "</font></p>");

				}
			}

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

	public final boolean containsDigit(String s) {
		boolean containsDigit = false;

		if (s != null && !s.isEmpty()) {
			for (char c : s.toCharArray()) {
				if (containsDigit = Character.isDigit(c)) {
					break;
				}
			}
		}

		return containsDigit;
	}

	public boolean isAlpha(String name) {
		return name.matches("[a-zA-Z]+");
	}
}
