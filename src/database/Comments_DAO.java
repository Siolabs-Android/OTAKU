package database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.example.otaku.Loginactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Comments_DAO {
	private SQLiteDatabase database;
	private MyDatabase dbHelper;
	private String[] allColumns  = {"_id","task_Id","comment","commentator_Id","commented_At","torc"};

	public Comments_DAO(Context context){
		dbHelper = new MyDatabase(context);
		database = dbHelper.getWritableDatabase();
	}

	

	public void close(){
		dbHelper.close();
	}



	public ArrayList<Comments_table> getComments(int taskid,int torc) {
		 ArrayList<Comments_table> comments=new ArrayList<Comments_table>();
		
			Cursor cursor = database.query("Comments",
					allColumns,"task_Id=? AND torc=?",new String[]{""+taskid,""+torc}, null, null,null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Comments_table ct = cursorToComment(cursor);
				comments.add(ct);
				cursor.moveToNext();
			}
			// make sure to close the cursor
			cursor.close();
			return comments;	
		}
		
	



	private Comments_table cursorToComment(Cursor cursor) {
		Comments_table ct=new Comments_table();
		ct.set_id(cursor.getInt(0));
		ct.setTask_Id(cursor.getInt(1));
		ct.setComment(cursor.getString(2));
		
		ct.setCommentatorId(cursor.getInt(3));
		ct.setCommented_At(new Date(cursor.getLong(4)));
	
		ct.setTorc(cursor.getInt(5));
		return ct;
	}
	



	public Comments_table createComment(String s, int userid, int torc,int taskid) {
		ContentValues v=new ContentValues();
		Comments_table ct=new Comments_table();
		v.put("comment", s);
		v.put("commentator_Id", userid);
		v.put("torc", torc);
		v.put("task_Id", taskid);
		v.put("commented_At",System.currentTimeMillis());
		ct.setComment(s);
		ct.setCommentatorId(userid);
		ct.setCommented_At(new Date(System.currentTimeMillis()));
		ct.setTask_Id(taskid);
		ct.setTorc(torc);
		database.insert("Comments","  ", v);
		return ct;
	}
	
}
