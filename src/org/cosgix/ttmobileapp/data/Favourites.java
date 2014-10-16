package org.cosgix.ttmobileapp.data;

public class Favourites {
	
	int projectId;
	int worktypeId;
	int taskId;
	//colour
	String colour;
	//nick name
	String nickName;

	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getWorktypeId() {
		return worktypeId;
	}
	public void setWorktypeId(int worktypeId) {
		this.worktypeId = worktypeId;
	}
	public int getTaskId() {
		return taskId;
	}
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
