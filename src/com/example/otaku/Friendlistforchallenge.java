package com.example.otaku;

import java.util.ArrayList;

import database.Challenges_DAO;
import database.Friends_DAO;
import database.User_DAO;
import database.User_table;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class Friendlistforchallenge extends Activity {
	ListView lv;
	CAforSelectFriend ca;
	int taskid;
	ArrayList<Integer> friends1;
	SharedPreferences sf;
	int userId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friendlistforchallenge);
        Intent i=getIntent();
        i.getIntExtra("taskid",taskid);
		friends1 = new ArrayList<Integer>();
		sf=this.getSharedPreferences("SP", Context.MODE_PRIVATE);
		ArrayList<User_table> friends = new ArrayList<User_table>();
		ArrayList<String> s = new ArrayList<String>();

		Friends_DAO fd = new Friends_DAO(this);
		User_DAO ud = new User_DAO(this);
		lv = (ListView) findViewById(R.id.listView1);
		friends1 = fd.getFriends(sf.getInt("userId",userId));
		for (int i1 = 0; i1 < friends1.size(); i1++) {
			friends.add(ud.getUser(friends1.get(i1)));
			s.add("");
		}
		ca = new CAforSelectFriend(this, friends, s);
		lv.setAdapter(ca);
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

	}

	public void onClick(View v) {
		SparseBooleanArray checked = lv.getCheckedItemPositions();
		Challenges_DAO cd = new Challenges_DAO(this);
		for (int i = 0; i < checked.size(); i++) {
			int position = checked.keyAt(i);
			if (checked.valueAt(i))
				cd.createChallenge(friends1.get(position), taskid,
						CAforSelectFriend.stakepoints.get(position));
		}
		Intent i=new Intent(this,Homepage.class);
		startActivity(i);
	}
}
