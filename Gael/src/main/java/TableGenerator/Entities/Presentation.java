package TableGenerator.Entities;


public class Presentation {
	private Project project;
	private String presentationId;
	private int durationInMins;
	
	public Presentation(String presentationId, int duration, Project project) {
		this.presentationId=presentationId;
		this.durationInMins=duration;
		this.project=project;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getPresentationId() {
		return presentationId;
	}

	public void setPresentationId(String presentationId) {
		this.presentationId = presentationId;
	}

	public int getDurationInMins() {
		return durationInMins;
	}

	public void setDurationInMins(int durationInMins) {
		this.durationInMins = durationInMins;
	}
	
	
	
	
}
