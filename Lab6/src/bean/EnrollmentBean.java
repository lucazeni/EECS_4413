package bean;

import java.util.ArrayList;

public class EnrollmentBean {

	private String cid;
	private ArrayList<String> students = new ArrayList<String>();
	private int credit;

	public EnrollmentBean(String cid, ArrayList<String> students, int credit)
	{
		this.cid = cid;
		this.students = students;
		this.credit = credit;
	}
	public String getCid() {
		return cid;
	}

	public ArrayList<String> getStudents() {
		return students;
	}

	public int getCredit() {
		return credit;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public void setStudents(ArrayList<String> students) {
		this.students = students;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

}
