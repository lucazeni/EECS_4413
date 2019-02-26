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
		if (request.getParameter("report") == null) {

			request.getRequestDispatcher(startPage).forward(request, response);
		} else {

			responseWriter.println("<table border='1'>");
			responseWriter.println("<tr>");
			responseWriter.println("<td>Id</td>");
			responseWriter.println("<td>Name</td>");
			responseWriter.println("<td>Credits taken</td>");
			responseWriter.println("<td>Credits to graduate</td>");
			responseWriter.println("</tr>");
		}

		surname = request.getParameter("surname");
		minCredit = request.getParameter("minCredit");

		try {
			studentList = sis.retrieveStudents(surname, minCredit);
			Collection<StudentBean> sbean = studentList.values();
			Iterator<StudentBean> studentIterator = sbean.iterator();

			while (studentIterator.hasNext()) {
				StudentBean item = studentIterator.next();
				id = item.getSid();
				name = item.getName();
				creditsTaken = item.getCredit_taken();
				creditsToGraduate = item.getCredit_graduate();
				responseWriter.println("<tr>");
				responseWriter.print("<td>" + name + "</td>");
				responseWriter.print("<td>" + id + "</td>");
				responseWriter.print("<td>" + creditsTaken + "</td>");
				responseWriter.print("<td>" + creditsToGraduate + "</td>");
				responseWriter.println("</tr>");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
