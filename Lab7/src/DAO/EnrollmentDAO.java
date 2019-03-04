package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import bean.EnrollmentBean;

public class EnrollmentDAO {

	DataSource ds;

	public EnrollmentDAO() throws ClassNotFoundException {
		try {
			ds = (DataSource) (new InitialContext()).lookup("java:/comp/env/jdbc/EECS");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public Map<String, EnrollmentBean> retrieveEnrollment(String namePrefix, int credit_taken) throws SQLException {
		String query = "select * from enrollment where sid in (select sid from students where surname like '%"
				+ namePrefix + "%' and credit_taken >= " + credit_taken + ") order by CID";
		Map<String, EnrollmentBean> rv = new HashMap<String, EnrollmentBean>();
		Connection con = this.ds.getConnection();
		PreparedStatement p = con.prepareStatement(query);
		ResultSet r = p.executeQuery();
		while (r.next()) {
			String cid = r.getString("CID");
			int credit = r.getInt("CREDIT");
			String sid = r.getString("SID");
			ArrayList<String> students = new ArrayList<String>();
			students.add(sid);

			while (r.next()) {
				if (r.getString("CID").equals(cid)) {
					students.add(r.getString("SID"));
				} else {
					r.previous();
					break;
				}
			}
			EnrollmentBean e = new EnrollmentBean(cid, students, credit);
			rv.put(cid, e);
		}
		r.close();
		p.close();
		con.close();
		return rv;
	}

}
