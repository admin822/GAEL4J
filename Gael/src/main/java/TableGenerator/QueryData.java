package TableGenerator;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import TableGenerator.Entities.Grade;
import TableGenerator.Entities.Student;
import TableGenerator.Entities.Submission;

public class QueryData {
	public static void query(Session session) {
		//Student student1=session.load(Student.class, "1");
		//Grade grade1=session.load(Grade.class,"1");
		String hql = "SELECT g.score FROM Grade g WHERE g.submission.submissionId=1";
		Query query = session.createQuery(hql);
		List results = query.list();
		//Submission sub1=session.load(Submission.class, "1");
//		for(Submission s:student1.getSubmissions()) {
//			System.out.println(s.toString());
//		}
		//System.out.println(student1.toString());
		System.out.println(results.get(0));
		//System.out.println(sub1.toString());
	}
}
