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
		String query = "select * from students where surname like '%" + namePrefix + "%' and credit_taken >= "
				+ credit_taken;
		Map<String, StudentBean> rv = new HashMap<String, StudentBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String name = r.getString("GIVENNAME") + ", " + r.getString("SURNAME");
			String sid = r.getString("SID");
			int creditTaken = r.getInt("CREDIT_TAKEN");
			int creditGraduate = r.getInt("CREDIT_GRADUATE");
			StudentBean s = new StudentBean(name, sid, creditTaken, creditGraduate);
			rv.put(sid, s);
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}

}
