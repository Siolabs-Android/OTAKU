package com.example.otaku;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import database.Challenges_DAO;
import database.Challenges_table;
import database.Friends_DAO;
import database.Friends_table;
import database.Performed_tasks_DAO;
import database.Performed_tasks_table;
import database.User_DAO;
import database.User_table;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Publicprofile extends Activity {
	int id;
	SharedPreferences sf;
	int userId;

	TextView name, reputationpoint, dob, city, country;
	ImageView dp;
	Button friend, performedtasks, challenges;
	ListView lv;
	CAforOthersChallenges coc;
	CAforPerformedTasks cpt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publicprofile);
		sf=this.getSharedPreferences("SP", Context.MODE_PRIVATE);
		Intent i = getIntent();
		Friends_table ftb = new Friends_table();
		final Friends_DAO fd = new Friends_DAO(this);
		id = i.getExtras().getInt("_id");
		User_DAO ud = new User_DAO(this);
		User_table user = ud.getUser(id);
		name = (TextView) findViewById(R.id.textView1);
		reputationpoint = (TextView) findViewById(R.id.textView2);
		dob = (TextView) findViewById(R.id.textView3);
		city = (TextView) findViewById(R.id.textView4);
		country = (TextView) findViewById(R.id.textView5);
		lv = (ListView) findViewById(R.id.listView1);
		friend = (Button) findViewById(R.id.button1);
		performedtasks = (Button) findViewById(R.id.button2);
		challenges = (Button) findViewById(R.id.button3);
		dp = (ImageView) findViewById(R.id.imageView2);
		name.setText(user.getName());
		reputationpoint.setText("" + user.getReputation());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy");
        String currentDate = simpleDateFormat.format(user.getDob());
         dob.setText(currentDate);
		city.setText(user.getCity());
		country.setText(user.getCountry());
		dp.setImageBitmap(BitmapFactory.decodeFile(user.getDp()));
		friendshipStatus();
		showPerformedTasks();
		performedtasks.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ArrayList<Performed_tasks_table> tasks = new ArrayList<Performed_tasks_table>();
				ArrayList<String> s = new ArrayList<String>();
				Performed_tasks_DAO ptd = new Performed_tasks_DAO(
						Publicprofile.this);
				performedtasks.setBackgroundColor(Color.GREEN);
				performedtasks.setTextColor(Color.WHITE);
				challenges.setBackgroundColor(Color.RED);
				challenges.setTextColor(Color.WHITE);
				tasks = ptd.getPerformedTasks(id);
				for (int i = 0; i < tasks.size(); i++)
					s.add("");
				cpt = new CAforPerformedTasks(Publicprofile.this, tasks, s);
				lv.setAdapter(cpt);
			}
		});
		challenges.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ArrayList<Challenges_table> challenge = new ArrayList<Challenges_table>();
				ArrayList<String> s = new ArrayList<String>();
				Challenges_DAO cd = new Challenges_DAO(Publicprofile.this);

				performedtasks.setBackgroundColor(Color.RED);
				performedtasks.setTextColor(Color.WHITE);
				challenges.setBackgroundColor(Color.GREEN);
				challenges.setTextColor(Color.WHITE);
				challenge = cd.getChallenges(id);
				for (int i = 0; i < challenge.size(); i++)
					s.add("");
				coc = new CAforOthersChallenges(Publicprofile.this, challenge,
						s);
				lv.setAdapter(coc);
			}
		});
		friend.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				String s = friend.getText().toString();
				String s1 = "Add Friend";
				String s2 = "Request Sent";
				String s3 = "Unfriend";
				String s4 = "Accept Request";
				if (s.equals(s1)) {
					fd.addFriend(id);
					friend.setText("Request Sent");
					friend.setBackgroundColor(Color.TRANSPARENT);
					friend.setTextColor(Color.BLACK);
				} else if (s.equals(s3)) {
					fd.deleteFriend(id);
					friend.setText("Add Friend");
					friend.setBackgroundColor(Color.BLUE);
					friend.setTextColor(Color.WHITE);
				} else if (s.equals(s4)) {
					fd.acceptFriend(id);
					friend.setText("Unfriend");
					friend.setBackgroundColor(Color.RED);
					friend.setTextColor(Color.WHITE);
				} else {

				}
			}
		});

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu1, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Intent j = new Intent(getApplicationContext(), Searchresults.class);
		startActivity(j);
		return true;
	}

	public void friendshipStatus() {
		Friends_table ftb = new Friends_table();
		final Friends_DAO fd = new Friends_DAO(this);
		ftb = fd.getFriendshipStatus(id);
		if (ftb == null) {
			friend.setText("Add Friend");
			friend.setBackgroundColor(Color.BLUE);
			friend.setTextColor(Color.WHITE);

		} else if (ftb.getFriendship_Status() == 0) {
			if (ftb.getRequest_Sender_Id() ==sf.getInt("userId",userId)) {
				friend.setText("Request Sent");
				friend.setBackgroundColor(Color.TRANSPARENT);
				friend.setTextColor(Color.BLACK);
			} else {
				friend.setText("Accept Request");
				friend.setBackgroundColor(Color.BLUE);
				friend.setTextColor(Color.WHITE);
			}

		} else {
			friend.setText("Unfriend");
			friend.setBackgroundColor(Color.RED);
			friend.setTextColor(Color.WHITE);
		}

	}

	public void showPerformedTasks() {
		ArrayList<Performed_tasks_table> tasks = new ArrayList<Performed_tasks_table>();
		ArrayList<String> s = new ArrayList<String>();
		Performed_tasks_DAO ptd = new Performed_tasks_DAO(Publicprofile.this);
		performedtasks.setBackgroundColor(Color.GREEN);
		performedtasks.setTextColor(Color.WHITE);
		challenges.setBackgroundColor(Color.RED);
		challenges.setTextColor(Color.WHITE);
		tasks = ptd.getPerformedTasks(id);
		for (int i = 0; i < tasks.size(); i++)
			s.add("");
		CAforPerformedTasks ca = new CAforPerformedTasks(Publicprofile.this,
				tasks, s);
		lv.setAdapter(ca);
	}
	public void openProfileOnClick(View view) {

		
			Intent i = new Intent(this, Publicprofile.class);
			i.putExtra("_id", id);
			startActivity(i);
		}
	

}
