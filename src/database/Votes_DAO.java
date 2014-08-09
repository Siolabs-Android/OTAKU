package database;

import com.example.otaku.Loginactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Votes_DAO {
	private SQLiteDatabase database;
	private MyDatabase dbHelper;
	private String[] allColumns  = {"_id",
			"task_Id","given_By","torc"};

	public Votes_DAO(Context context){
		dbHelper = new MyDatabase(context);
		database = dbHelper.getWritableDatabase();
	}

	

	public void close(){
		dbHelper.close();
	}



	public void createVote(int userid, int taskid, int torc) {
		ContentValues v=new ContentValues();
		v.put("given_By", userid);
		v.put("task_Id", taskid);
		v.put("torc", torc);
		database.insert("Votes","  ", v);
		
	}



	public Votes_table getVoteStatus(int taskid,int torc,int givenby) {
		
		Cursor cursor = database.query("Votes",
				allColumns, "task_Id=? AND torc=? AND given_By=?",
				new String[]{""+taskid,""+torc,""+givenby}, null, null, null);
		if(cursor.moveToFirst())
		return cursorToVote(cursor);
		else 
			return null;
	}



	private Votes_table cursorToVote(Cursor cursor) {
		Votes_table vtb=new Votes_table();
		vtb.set_id(cursor.getInt(0));
		vtb.setTask_Id(cursor.getInt(1));
		vtb.setGiven_By(cursor.getInt(2));
		vtb.setTorc(cursor.getInt(3));
		return vtb;
	}



}
