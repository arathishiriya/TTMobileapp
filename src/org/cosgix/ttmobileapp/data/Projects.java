package org.cosgix.ttmobileapp.data;


/**
 * This class provides the project details getter and setter
 * @author Sanjib
 *
 */
public class Projects {

	// variables declaration
	private int projectId;
	private int projectClientId;
	private String projectName;

	// getter method for project id
	public int getProjectId() {
		return projectId;
	}

	// setter method for project id
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	// getter method for project client id
	public int getProjectClientId() {
		return projectClientId;
	}

	// setter method for project client id
	public void setProjectClientId(int projectClientId) {
		this.projectClientId = projectClientId;
	}

	// getter method for project name
	public String getProjectName() {
		return projectName;
	}

	// setter method for project name
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
