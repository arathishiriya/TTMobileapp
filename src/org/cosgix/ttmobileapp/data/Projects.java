package org.cosgix.ttmobileapp.data;


/**
 * This class provides the project details getter and setter
 * @author admin1
 *
 */
public class Projects {
	
	private int projectId;
	private int projectClientId;
	private String projectName;
	
	
	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getProjectClientId() {
		return projectClientId;
	}

	public void setProjectClientId(int projectClientId) {
		this.projectClientId = projectClientId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
