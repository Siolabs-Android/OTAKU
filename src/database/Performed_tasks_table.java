package database;

import java.io.Serializable;
import java.util.Date;

public class Performed_tasks_table implements Serializable{

	int _id;
	String title;
	String Proof;
	String description;
	int submitted_By ;
	Date submitted_At;
	int upvote_Count,comment_Count;
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProof() {
		return Proof;
	}
	public void setProof(String proof) {
		Proof = proof;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getSubmitted_By() {
		return submitted_By;
	}
	public void setSubmitted_By(int submitted_By) {
		this.submitted_By = submitted_By;
	}
	public Date getSubmitted_At() {
		return submitted_At;
	}
	public void setSubmitted_At(Date submitted_At) {
		this.submitted_At = submitted_At;
	}
	public int getUpvote_Count() {
		return upvote_Count;
	}
	public void setUpvote_Count(int upvote_Count) {
		this.upvote_Count = upvote_Count;
	}
	public int getComment_Count() {
		return comment_Count;
	}
	public void setComment_Count(int comment_Count) {
		this.comment_Count = comment_Count;
	}
	
}
