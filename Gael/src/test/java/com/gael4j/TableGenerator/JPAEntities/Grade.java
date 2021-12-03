package com.gael4j.TableGenerator.JPAEntities;
import com.gael4j.Gael.Annotations.PrivateData;

import javax.persistence.*;

@PrivateData(primaryKey = "gradeId")
@Entity
@Table(name = "grades")
public class Grade {

	@Id
	@Column(name = "grade_id")
	private String gradeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "submission_id")
	private Submission submission;

	@Column(name = "grader_id")
	private String graderId;

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
		return "Grade [graderId=" + graderId + ", gradeId=" + gradeId + ", score=" + score + "] -> Submission";
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
