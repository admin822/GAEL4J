package com.gael4j.NonJPADemo.Entity;

import com.gael4j.Gael.Annotations.userdata;

@userdata
public class Project {
	private Student student;
	private String projectId;
	private String summary;
	
	public Project() {
		
	}
	public Project(String projectId, Student student, String summary) {
		this.projectId=projectId;
		this.student=student;
		this.summary=summary;
	}
	
	@Override
	public String toString() {
		return "Project [student=" + student.getStudentId() + ", projectId=" + projectId + ", summary=" + summary + "]";
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
}
