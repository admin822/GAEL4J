package com.gael4j.NonJPADemo.Entity;

import com.gael4j.Gael.Annotations.userdata;

@userdata
public class Grade {
	private Submission submission;
	private String graderId;
	private String gradeId;
	private int score;
	
	public Grade(String gradeId, String graderId, int score, Submission submission) {
		this.gradeId=gradeId;
		this.graderId=graderId;
		this.score=score;
		this.submission=submission;
	}
	public Grade() {
		
	}
	@Override
	public String toString() {
		return "Grade [submission=" + submission.getSubmissionId() + ", graderId=" + graderId + ", gradeId=" + gradeId + ", score="
				+ score + "]";
	}

	public Submission getSubmission() {
		return submission;
	}
	public void setSubmission(Submission submission) {
		this.submission = submission;
	}
	public String getGraderId() {
		return graderId;
	}
	public void setGraderId(String graderId) {
		this.graderId = graderId;
	}
	public String getGradeId() {
		return gradeId;
	}
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	
}
