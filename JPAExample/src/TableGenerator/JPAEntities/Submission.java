package TableGenerator.JPAEntities;

import com.gael4j.Gael.Annotations.PrivateData;

import javax.persistence.*;

//@PrivateData(primaryKey = "submissionId")
@Entity
@Table(name = "submissions")
public class Submission {

	@Id
	@Column(name = "submission_id")
	private String submissionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private Student student;

	@Column(name = "submission_answer")
	private String submissionAnswer;
	
	public Submission() {}

	public Submission(String submissionId, Student student, String answer) {
		this.submissionId=submissionId;
		this.student=student;
		this.submissionAnswer=answer;
	}
	
	@Override
	public String toString() {
		return "Submission [submissionId=" + submissionId + ", submissionAnswer=" + submissionAnswer + "] -> Student";
	}
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(String submissionId) {
		this.submissionId = submissionId;
	}

	public String getSubmissionAnswer() {
		return submissionAnswer;
	}

	public void setSubmissionAnswer(String submissionAnswer) {
		this.submissionAnswer = submissionAnswer;
	}

}
