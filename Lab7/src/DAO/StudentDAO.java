package DAO;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.StudentBean;

public class StudentDAO {
	DataSource ds;

	public StudentDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public Map<String, StudentBean> retrieveStudents(String namePrefix, int credit_taken) throws SQLException {
		String query = "Select STD.SID, STD.GIVENNAME, STD.SURNAME, STD.CREDIT_GRADUATE, STD.CREDIT_TAKEN, SUM(ENR.CREDIT) as CREDIT_INPROGRESS\r\n" + 
				"from STUDENTS as STD\r\n" + 
				"JOIN Enrollment as ENR on STD.SID = ENR.SID \r\n" + 
				"WHERE STD.SURNAME like '%" + namePrefix + "%' and CREDIT_TAKEN >= " + credit_taken + "\r\n" + 
				"GROUP BY STD.SID, STD.GIVENNAME, STD.SURNAME, STD.CREDIT_GRADUATE, STD.CREDIT_TAKEN";
		Map<String, StudentBean> rv = new HashMap<String, StudentBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			
			String name = r.getString("GIVENNAME") + ", " + r.getString("SURNAME");
			String sid = r.getString("SID");
			int creditTaken = r.getInt("CREDIT_TAKEN");
			int creditGraduate = r.getInt("CREDIT_GRADUATE");
			int creditTaking = r.getInt("CREDIT_INPROGRESS");
			StudentBean s = new StudentBean(name, sid, creditTaken, creditGraduate, creditTaking);
			rv.put(sid, s);
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}

}
