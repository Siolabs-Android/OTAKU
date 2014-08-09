package database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.example.otaku.Loginactivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Challenges_DAO {
	private SQLiteDatabase database;
	private MyDatabase dbHelper;
	private String[] allColumns  = {"_id","task_Id","given_By","given_To","given_At","recieve_Status","reputation_Stake",
			"upvote_Count","comment_Count","proof"};
	static SharedPreferences sf;
	int userId;
	public Challenges_DAO(Context context){
		dbHelper = new MyDatabase(context);
		database = dbHelper.getWritableDatabase();
		sf=context.getSharedPreferences("SP", Context.MODE_PRIVATE);
	}

	

	public void close(){
		dbHelper.close();
	}
	public ArrayList<Challenges_table> getChallenges(int id){
		ArrayList<Challenges_table> challenges=new ArrayList<Challenges_table>();
		Cursor cursor = database.query("Challenges",
				allColumns, "given_To="+id, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Challenges_table ch = cursorToChallenges(cursor);
			challenges.add(ch);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return challenges;
	}
	public void createChallenge(int givento,int taskid,int points){
		ContentValues v=new ContentValues();
		v.put("given_By", sf.getInt("userId",userId) );
		v.put("given_To",givento);
		v.put("task_Id",taskid );
		v.put("reputation_Stake",points);
		v.put("given_At",System.currentTimeMillis() );
		v.put("recieve_Status",0);
		v.put("upvote_Count",0);
		v.put("comment_Count",0);
		database.insert("Challenges","    ",
				v);
	}
	

	public void updateCommentCount(int taskid) {
		Cursor cursor = database.query("Challenges", allColumns,"_id="+taskid,
				null, null, null,null);
		ContentValues v=new ContentValues();
		v.put("comment_Count",cursor.getInt(7)+1);
		database.update("Challenges", v,"_id="+taskid, null);
	}

	public void updateUpvoteCount(int taskid) {
		Cursor cursor = database.query("Challenges", allColumns,"_id="+taskid,
				null, null, null,null);
		ContentValues v=new ContentValues();
		v.put("upvote_Count",cursor.getInt(6)+1);
		database.update("Challenges", v,"_id="+taskid, null);
	}

	private Challenges_table cursorToChallenges(Cursor cursor) {
		Challenges_table ch  = new Challenges_table();
		ch.set_id(cursor.getInt(0));
		ch.setTask_Id(cursor.getInt(1));
		ch.setGiven_By(cursor.getInt(2));
		ch.setGiven_To(cursor.getInt(3));
		ch.setGiven_At(new Date(cursor.getLong(4)));
		ch.setRecieve_Status(cursor.getInt(5));
		ch.setReputation_Stake(cursor.getInt(6));
		ch.setUpvote_Count(cursor.getInt(7));
		ch.setComment_Count(cursor.getInt(8));
		ch.setProof(cursor.getString(9));
		return ch;
	}



	public void deleteChallenge(int cid) {
		database.delete("Challenges","_id="+cid,null);
		
	}
}
