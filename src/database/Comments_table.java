package database;

import java.io.Serializable;
import java.util.Date;

public class Comments_table implements Serializable {
	int _id;
	int task_Id;
	String comment;
	int commentatorId;
	Date commented_At;
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getCommentatorId() {
		return commentatorId;
	}
	public void setCommentatorId(int commentatorId) {
		this.commentatorId = commentatorId;
	}
	public Date getCommented_At() {
		return commented_At;
	}
	public void setCommented_At(Date commented_At) {
		this.commented_At = commented_At;
	}
	public int getTorc() {
		return torc;
	}
	public void setTorc(int torc) {
		this.torc = torc;
	}

	
}
