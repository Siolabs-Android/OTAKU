package database;

import java.io.Serializable;
import java.util.Date;

public class Challenges_table implements Serializable {

	int _id;
	int task_Id;
	int given_By;
	int given_To;
	Date given_At;
	int recieve_Status;
	int reputation_Stake;
	String proof;
	int upvote_Count, comment_Count;
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
	public int getGiven_To() {
		return given_To;
	}
	public void setGiven_To(int given_To) {
		this.given_To = given_To;
	}
	public Date getGiven_At() {
		return given_At;
	}
	public void setGiven_At(Date given_At) {
		this.given_At = given_At;
	}
	public int getRecieve_Status() {
		return recieve_Status;
	}
	public void setRecieve_Status(int recieve_Status) {
		this.recieve_Status = recieve_Status;
	}
	public int getReputation_Stake() {
		return reputation_Stake;
	}
	public void setReputation_Stake(int reputation_Stake) {
		this.reputation_Stake = reputation_Stake;
	}
	public String getProof() {
		return proof;
	}
	public void setProof(String proof) {
		this.proof = proof;
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
