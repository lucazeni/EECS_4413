package model;

import java.util.Map;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import com.sun.xml.internal.txw2.annotation.XmlAttribute;
import com.sun.xml.internal.txw2.annotation.XmlElement;

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
			if (credit_taken.contains(".")) {
				throw new NumberFormatException();
			}
			int credits = Integer.parseInt(credit_taken);
			if (namePrefix.equals("") || credits < 0 || containsDigit(namePrefix) || !isAlpha(namePrefix)) {
				throw new Exception();
			}
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

	public void export(String namePrefix, String credit_taken, String filename) throws Exception {

		int credits = Integer.parseInt(credit_taken);
		List<StudentBean> list = new ArrayList<StudentBean>();
		Collection<StudentBean> sbean = this.retrieveStudents(namePrefix, credit_taken).values();
		Iterator<StudentBean> item = sbean.iterator();
		while (item.hasNext()) {
			list.add(item.next());
		}
		ListWrapper lw = new ListWrapper(namePrefix, credits, list);
		try {
			JAXBContext jc = JAXBContext.newInstance(lw.getClass());
			Marshaller marshaller = jc.createMarshaller();
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			String path = new File(filename).getParent();
			Schema schema = sf.newSchema(new File(path + "/SIS.xsd"));
			marshaller.setSchema(schema);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			StringWriter sw = new StringWriter();
			sw.write("\n");
			marshaller.marshal(lw, new StreamResult(sw));

			System.out.println(sw.toString()); // for debugging
			FileWriter fw = new FileWriter(filename);
			fw.write(sw.toString());
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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