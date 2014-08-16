package database;

import java.io.Serializable;

public class Friends_table implements Serializable{
	int _id ;
	int request_Sender_Id;
	int request_Reciever_Id;
	int friendship_Status;
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public int getRequest_Sender_Id() {
		return request_Sender_Id;
	}
	public void setRequest_Sender_Id(int request_Sender_Id) {
		this.request_Sender_Id = request_Sender_Id;
	}
	public int getRequest_Reciever_Id() {
		return request_Reciever_Id;
	}
	public void setRequest_Reciever_Id(int request_Reciever_Id) {
		this.request_Reciever_Id = request_Reciever_Id;
	}
	public int getFriendship_Status() {
		return friendship_Status;
	}
	public void setFriendship_Status(int friendship_Status) {
		this.friendship_Status = friendship_Status;
	}
	

}
