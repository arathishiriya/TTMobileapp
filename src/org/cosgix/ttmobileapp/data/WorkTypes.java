package org.cosgix.ttmobileapp.data;

/**
 * This class provides the WorkTypes details from json server
 * @author Sanjib
 *
 */
public class WorkTypes {

	// variables declaration
	private int worktypeId;
	private String worktypeName;
	private String worktypeDescription;

	// getter method for work type id
	public int getWorktypeId() {
		return worktypeId;
	}

	// setter method for work type id
	public void setWorktypeId(int worktypeId) {
		this.worktypeId = worktypeId;
	}

	// getter method for work type name
	public String getWorktypeName() {
		return worktypeName;
	}

	// setter method for work type name
	public void setWorktypeName(String worktypeName) {
		this.worktypeName = worktypeName;
	}

	// getter method for work type description
	public String getWorktypeDescription() {
		return worktypeDescription;
	}

	// setter method for work type description
	public void setWorktypeDescription(String worktypeDescription) {
		this.worktypeDescription = worktypeDescription;
	}

}
