package org.cosgix.ttmobileapp.data;

/**
 * This class provides the WorkTypes details from json server
 * @author admin1
 *
 */
public class WorkTypes {

	private int worktypeId;
	private String worktypeName;
	private String worktypeDescription;

	public int getWorktypeId() {
		return worktypeId;
	}

	public void setWorktypeId(int worktypeId) {
		this.worktypeId = worktypeId;
	}

	public String getWorktypeName() {
		return worktypeName;
	}

	public void setWorktypeName(String worktypeName) {
		this.worktypeName = worktypeName;
	}

	public String getWorktypeDescription() {
		return worktypeDescription;
	}

	public void setWorktypeDescription(String worktypeDescription) {
		this.worktypeDescription = worktypeDescription;
	}

}
