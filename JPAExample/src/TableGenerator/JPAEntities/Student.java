package TableGenerator.JPAEntities;

import com.gael4j.Gael.Annotations.PrivateData;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//@PrivateData(primaryKey = "studentId")
@Entity
@Table(name = "students")
public class Student {

	@Id
	@Column(name = "student_id", unique=true, nullable=false)
	private String studentId;

	@OneToMany(mappedBy = "student")
	private Set<Submission> submissions=new HashSet<Submission>();

	@OneToMany(mappedBy = "student")
	private Set<Project> projects=new HashSet<Project>();

	public Student() {}

	public Student(String studentId) {
		this.studentId=studentId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public Set<Submission> getSubmissions() {
		return submissions;
	}

	public void setSubmissions(Set<Submission> submissions) {
		this.submissions = submissions;
	}

	public void addSubmission(Submission submission) {
		if (submissions.contains(submission))
			return;
		submissions.add(submission);
		submission.setStudent(this);
	}

	public void removeSubmission(Submission submission) {
		if (submissions.contains(submission))
			return;
		submissions.remove(submission);
		submission.setStudent(null);
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	public void addProject(Project project) {
		if (projects.contains(project))
			return;
		projects.add(project);
		project.setStudent(this);
	}

	public void removeProject(Project project) {
		if (projects.contains(project))
			return;
		projects.remove(project);
		project.setStudent(null);
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + "]";
	}
}
