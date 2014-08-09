package database;

import java.util.ArrayList;

import com.example.otaku.Loginactivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Friends_DAO {
	private SQLiteDatabase database;
	private MyDatabase dbHelper;
	private String[] allColumns  = {"_id","request_Sender_Id","request_Reciever_Id","friendship_Status"};
    static Context c;
    static SharedPreferences sf;
	int userId;
	public Friends_DAO(Context context){
		dbHelper = new MyDatabase(context);
		database = dbHelper.getWritableDatabase();
		c=context;
		sf=c.getSharedPreferences("SP", Context.MODE_PRIVATE);
	}

	

	public void close(){
		dbHelper.close();
	}
	public Friends_table getFriendshipStatus(int friendid){
		Friends_table ftb=new Friends_table();
		Cursor cursor = database.query("Friends",
				allColumns, "(request_Sender_Id=? AND request_Reciever_Id=?) OR (request_Sender_Id=? AND request_Reciever_Id=?)",
				new String[]{""+friendid,""+ sf.getInt("userId",userId),""+ sf.getInt("userId",userId),""+friendid}, null, null, null);
		if(cursor.moveToFirst())
		return cursorToFriend(cursor);
		else 
			return null;
	}
 public void addFriend(int friendid){
	 ContentValues values = new ContentValues();
	 User_table ut=new User_table();
	 User_DAO ud=new User_DAO(c);
	 ut=ud.getUser(friendid);
		values.put("request_Sender_Id", sf.getInt("userId",userId));
		values.put("request_Reciever_Id",ut.get_id());
		values.put("friendship_Status",0);
		database.insert("Friends",null,
				values);
 }
 public void acceptFriend(int friendid){
	 ContentValues values = new ContentValues();
	 values.put("friendship_Status",1);
	 database.update("Friends",values,"request_Sender_Id=? AND request_Reciever_Id=?",new String[]{""+friendid,""+ sf.getInt("userId",userId)});
 }
 public void deleteFriend(int friendid){
	 database.delete("Friends","(request_Sender_Id=? AND request_Reciever_Id=?) OR (request_Sender_Id=? AND request_Reciever_Id=?)",
				new String[]{""+friendid,""+ sf.getInt("userId",userId),""+ sf.getInt("userId",userId),""+friendid});
 }
 public ArrayList<Integer> getFriendRequests(int id)
 {
	 
	 ArrayList<Integer> i=new ArrayList<Integer>();
	 Cursor cursor = database.query("Friends",
				allColumns, "(request_Sender_Id=? OR request_Reciever_Id=?) AND friendship_Status=?",
				new String[]{""+id,""+id,"0"}, null, null, null);
	 cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			if(cursor.getInt(1)==id)
			i.add(cursor.getInt(2));
			else
				i.add(cursor.getInt(1));	
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
	 return i;
 }
 public ArrayList<Integer> getFriends(int id)
 {
	 ArrayList<Integer> i=new ArrayList<Integer>();
	 Cursor cursor = database.query("Friends",
				allColumns, "(request_Sender_Id=? OR request_Reciever_Id=?) AND friendship_Status=?",
				new String[]{""+id,""+id,"1"}, null, null, null);
	 cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			if(cursor.getInt(1)==id)
			i.add(cursor.getInt(2));
			else
				i.add(cursor.getInt(1));	
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
	 return i;
 }
 private Friends_table cursorToFriend(Cursor cursor) {
		Friends_table ut  = new Friends_table();
		ut.set_id(cursor.getInt(0));
		ut.setRequest_Sender_Id(cursor.getInt(1));
		ut.setRequest_Reciever_Id(cursor.getInt(2));
		ut.setFriendship_Status(cursor.getInt(3));
		
		return ut;
	}
}
