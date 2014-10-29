package org.cosgix.ttmobileapp.data;

/**
 * This class provides the Tasks details getter and setter
 * @author Sanjib
 *
 */
public class Tasks {

	// variables declaration
	int projectId;
	int taskId;
	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	String taskName;
	
	// getter method for task id
	public int getprojectId() {
		return projectId;
	}

	// setter method for task id
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	// getter method for task name
	public String getTaskName() {
		return taskName;
	}

	// setter method for task name
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

}
