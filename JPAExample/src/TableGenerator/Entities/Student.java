package TableGenerator.Entities;


import java.util.HashSet;
import java.util.Set;

import com.gael4j.Gael.Annotations.PrivateData;
import com.gael4j.Gael.Annotations.userdata;

@userdata
public class Student {
	private String studentId;
	private Set<Submission> submissions=new HashSet<Submission>();
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

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		String submissionString="";
		for(Submission s:this.submissions) {
			submissionString+=(s.toString()+"\n");
		}
		String projectString="";
		for(Project p:this.projects) {
			projectString+=(p.toString()+"\n");
		}
		return "Student: "+studentId+"\n"+submissionString+projectString;
	}
	
	
	
	
}
