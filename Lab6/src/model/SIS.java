package model;
import java.util.Map;
import DAO.*;
import bean.*;

public class SIS {

	private StudentDAO students;
	private EnrollmentDAO enrollments;

	public SIS() throws ClassNotFoundException {
		students = new StudentDAO();
		enrollments = new EnrollmentDAO();
	}

	public Map<String, StudentBean> retrieveStudents(String namePrefix, String credit_taken) throws Exception {
		try {
			int credits = Integer.parseInt(credit_taken);
			return students.retrieveStudents(namePrefix, credits);
		} catch (Exception e) {
			throw new Exception();
		}
	}

	public Map<String, EnrollmentBean> retriveEnrollment() throws Exception {
		try {
			int credits = Integer.parseInt("20");
			return enrollments.retrieveEnrollment("Rodrigez", credits);
		} catch (Exception e) {
			throw new Exception();
		}
	}
}