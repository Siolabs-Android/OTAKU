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

public class Performed_tasks_DAO {

	private SQLiteDatabase database;
	private MyDatabase dbHelper;
	private String[] allColumns = { "_id", "title", "proof", "description",
			"submitted_By", "submitted_At", "upvote_Count", "comment_Count" };
	static SharedPreferences sf;
	int userId;

	public Performed_tasks_DAO(Context context) {
		dbHelper = new MyDatabase(context);
		database = dbHelper.getWritableDatabase();
		sf = context.getSharedPreferences("SP", Context.MODE_PRIVATE);
	}

	public void close() {
		dbHelper.close();
	}

	public ArrayList<Performed_tasks_table> getPerformedTasks(int i) {
		ArrayList<Performed_tasks_table> tasks = new ArrayList<Performed_tasks_table>();
		Cursor cursor = database.query("PerformedTasks", allColumns,
				"submitted_By=" + i, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Performed_tasks_table ptt = cursorToPerformedTasks(cursor);
			tasks.add(ptt);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();

		return tasks;
	}

	private Performed_tasks_table cursorToPerformedTasks(Cursor cursor) {
		Performed_tasks_table ptt = new Performed_tasks_table();
		ptt.set_id(cursor.getInt(0));
		ptt.setTitle(cursor.getString(1));
		ptt.setProof(cursor.getString(2));
		ptt.setDescription(cursor.getString(3));
		ptt.setSubmitted_By(cursor.getInt(4));
		ptt.setSubmitted_At(new Date(cursor.getLong(5)));
		ptt.setUpvote_Count(cursor.getInt(6));
		ptt.setComment_Count(cursor.getInt(7));
		return ptt;
	}

	public Performed_tasks_table getTaskById(int taskid) {

		Cursor cursor = database.query("PerformedTasks", allColumns, "_id="
				+ taskid, null, null, null, null);
		cursor.moveToFirst();
		return cursorToPerformedTasks(cursor);
	}

	public ArrayList<Performed_tasks_table> getTopposts() {
		ArrayList<Performed_tasks_table> tasks = new ArrayList<Performed_tasks_table>();
		Cursor cursor = database.query("PerformedTasks", allColumns, null,
				null, null, null, "upvote_Count");

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Performed_tasks_table ptt = cursorToPerformedTasks(cursor);
			tasks.add(ptt);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();

		return tasks;
	}

	public void updateCommentCount(int taskid) {
		Performed_tasks_table ptt = new Performed_tasks_table();
		ptt = getTaskById(taskid);
		ContentValues v = new ContentValues();
		v.put("comment_Count", ptt.getComment_Count()+1);
		database.update("PerformedTasks", v, "_id=" + taskid, null);

	}

	public void updateUpvoteCount(int taskid) {
		Performed_tasks_table ptt = new Performed_tasks_table();
		ptt = getTaskById(taskid);
		ContentValues v = new ContentValues();
		v.put("upvote_Count", ptt.getUpvote_Count()+1);
		database.update("PerformedTasks", v, "_id=" + taskid, null);

	}

	public long createTask(Performed_tasks_table ptt) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put("title", ptt.getTitle());
		values.put("description", ptt.getDescription());
		values.put("submitted_By", sf.getInt("userId", userId));
		values.put("proof", ptt.getProof());
		values.put("submitted_At", System.currentTimeMillis());
		values.put("upvote_Count", 0);
		values.put("comment_Count", 0);
		long p = database.insert("PerformedTasks", "    ", values);
		return p;

	}
}
