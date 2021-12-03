package com.gael4j.TableGenerator.JPAEntities;

import com.gael4j.Gael.Annotations.PrivateData;

import javax.persistence.*;

@PrivateData(primaryKey = "projectId")
@Entity
@Table(name = "projects")
public class Project {
	@Id
	@Column(name = "project_id")
	private String projectId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private Student student;

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
		return "Project [projectId=" + projectId + ", summary=" + summary + "] -> Student";
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
