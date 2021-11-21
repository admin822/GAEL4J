package TableGenerator.JPAEntities;

import com.gael4j.Gael.Annotations.PrivateData;
import javax.persistence.*;

@PrivateData(primaryKey = "presentationId")
@Entity
@Table(name = "presentations")
public class Presentation {

	@Id
	@Column(name = "presentation_id")
	private String presentationId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;

	@Column(name = "duration_in_mins")
	private int durationInMins;

	public Presentation() {}

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
	
	@Override
	public String toString() {
		return "Presentation [presentationId=" + presentationId + ", durationInMins=" + durationInMins + "] -> Project";
	}
}
