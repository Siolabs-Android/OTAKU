package database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class User_DAO {

	private SQLiteDatabase database;
	private MyDatabase dbHelper;
	private String[] allColumns = { "_id", "name", "email", "password",
			"reputation", "city", "country", "dob", "dp" };
	Context ctx;

	public User_DAO(Context context) {
		dbHelper = new MyDatabase(context);
		database = dbHelper.getWritableDatabase();
		ctx = context;
	}

	public void close() {
		dbHelper.close();
	}

	public User_table getUser(String email) {
		Cursor cursor = database.query("User", allColumns, "email=" + "'"
				+ email + "'", null, null, null, null);
		cursor.moveToFirst();
		if (cursor.moveToFirst())
			return cursorToUser(cursor);
		else
			return null;
	}
	
	public void updateReputation(int userId,int reputation) {
		ContentValues values = new ContentValues();
		values.put("reputation", reputation);
		
		database.update("User", values, "_id=" + userId, null);

	}
	public User_table getUser(int id) {
		Cursor cursor = database.query("User", allColumns, "_id=" + id, null,
				null, null, null);

		if (cursor.moveToFirst())

			return cursorToUser(cursor);

		else
			return null;

	}

	public ArrayList<User_table> publicSearch(String name) {
		ArrayList<User_table> user = new ArrayList<User_table>();

		Cursor cursor = database.query("User", allColumns, "name" + "='" + name
				+ "'", null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			User_table ut = cursorToUser(cursor);
			user.add(ut);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();

		return user;
	}

	public void createUser(User_table ut1) {
		ContentValues values = new ContentValues();
		values.put("name", ut1.getName());
		values.put("email", ut1.getEmail());
		values.put("password", ut1.getPassword());
		values.put("reputation", 10);
		database.insert("User", "    ", values);

	}

	public void editUser(User_table ut1) {
		ContentValues values = new ContentValues();
		values.put("name", ut1.getName());
		values.put("password", ut1.getPassword());
		values.put("city", ut1.getCity());
		values.put("country", ut1.getCountry());
		values.put("dob", ut1.getDob().getTime());
		values.put("dp", ut1.getDp());
		database.update("User", values, "_id=" + ut1.get_id(), null);

	}
	

	private User_table cursorToUser(Cursor cursor) {
		User_table ut = new User_table();
		
		ut.set_id(cursor.getInt(0));
		ut.setName(cursor.getString(1));
		ut.setEmail(cursor.getString(2));
		ut.setPassword(cursor.getString(3));
		ut.setReputation(cursor.getInt(4));
		ut.setCity(cursor.getString(5));
		ut.setCountry(cursor.getString(6));
		ut.setDob(new Date(cursor.getLong(7)));
		ut.setDp(cursor.getString(8));
		return ut;
	}
}
