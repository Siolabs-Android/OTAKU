package database;

import java.io.Serializable;

public class Votes_table implements Serializable {
	int _id;
	int task_Id;
	int given_By;
	int torc;
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public int getTask_Id() {
		return task_Id;
	}
	public void setTask_Id(int task_Id) {
		this.task_Id = task_Id;
	}
	public int getGiven_By() {
		return given_By;
	}
	public void setGiven_By(int given_By) {
		this.given_By = given_By;
	}
	public int getTorc() {
		return torc;
	}
	public void setTorc(int torc) {
		this.torc = torc;
	}

	
}
