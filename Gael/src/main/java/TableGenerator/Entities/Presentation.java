package TableGenerator.Entities;

import com.gael4j.Gael.Annotations.userdata;

@userdata
public class Presentation {
	private Project project;
	private String presentationId;
	private int durationInMins;
	public Presentation() {
		
	}
	public Presentation(String presentationId, int duration, Project project) {
		this.presentationId=presentationId;
		this.durationInMins=duration;
		this.project=project;
	}
	
	@Override
	public String toString() {
		return "Presentation [project=" + project.getProjectId() + ", presentationId=" + presentationId + ", durationInMins="
				+ durationInMins + "]";
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
