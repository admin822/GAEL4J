package TableGenerator.Entities;

public class Submission {
	private Student student;
	private String submissionId;
	private String submissionAnswer;
	
	public Submission() {}
	public Submission(String submissionId, Student student, String answer) {
		this.submissionId=submissionId;
		this.student=student;
		this.submissionAnswer=answer;
	}
	
	@Override
	public String toString() {
		return "Submission [student=" + student.getProjects() + ", submissionId=" + submissionId + ", submissionAnswer="
				+ submissionAnswer + "]";
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
