package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase extends SQLiteOpenHelper{
	public MyDatabase(Context context) {
	    super(context, "Otaku.db", null, 1);
	  }
	
	
	public void onCreate(SQLiteDatabase arg0) {
		
		arg0.execSQL(CreateUser);
		arg0.execSQL(PerformedTasks);
		arg0.execSQL(Comments);
		arg0.execSQL(Votes);
		arg0.execSQL(Challenges);
		arg0.execSQL(Friends);
	}
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}
	
	private static final String  CreateUser= "Create table User( _id integer primary key autoincrement, name text," +
			"email text,password text,reputation integer,city text,country text,dob  DATETIME DEFAULT CURRENT_DATE,dp text);";  
	private static final String  PerformedTasks= "Create table PerformedTasks( _id integer primary key autoincrement, " +
			"title text,proof text,description text,submitted_By integer,submitted_At  DATETIME DEFAULT CURRENT_TIMESTAMP," +
			"upvote_Count integer,comment_Count integer);";  
	private static final String  Comments= "Create table Comments( _id integer primary key autoincrement, " +
			"task_Id integer,comment text,commentator_Id integer,commented_At  DATETIME DEFAULT CURRENT_TIMESTAMP,torc integer);";  
	private static final String Votes= "Create table Votes( _id integer primary key autoincrement, " +
			"task_Id integer,given_By integer,torc integer);";  
	private static final String  Challenges= "Create table Challenges( _id integer primary key autoincrement, " +
			"task_Id integer,given_By integer,given_To integer,given_At  DATETIME DEFAULT CURRENT_TIMESTAMP,recieve_Status integer,"+
			"reputation_Stake integer,upvote_Count integer,comment_Count integer,proof text);";  
	private static final String  Friends= "Create table Friends( _id integer primary key autoincrement, " +
			"request_Sender_Id integer,request_Reciever_Id integer,friendship_Status integer);";  
}

