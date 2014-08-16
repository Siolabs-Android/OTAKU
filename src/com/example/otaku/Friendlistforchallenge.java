package com.example.otaku;

import java.util.ArrayList;

import database.Challenges_DAO;
import database.Challenges_table;
import database.Friends_DAO;
import database.User_DAO;
import database.User_table;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.ListView;
import android.widget.Toast;

public class Friendlistforchallenge extends Activity {
	ListView lv;
	CAforSelectFriend ca;
	int taskid;
	ArrayList<Integer> friends1;
	SharedPreferences sf;
	int userId;
	User_DAO ud;
	ArrayList<Integer> stakepoints;
	ArrayList<Integer> selectedFriends;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friendlistforchallenge);
		Intent i = getIntent();
		taskid = i.getIntExtra("taskid", taskid);
		
		friends1 = new ArrayList<Integer>();
		stakepoints = new ArrayList<Integer>();
		sf = this.getSharedPreferences("SP", Context.MODE_PRIVATE);
		userId = sf.getInt("userId", userId);
		ArrayList<User_table> friends = new ArrayList<User_table>();
		ArrayList<String> s = new ArrayList<String>();
		selectedFriends = new ArrayList<Integer>();
		Friends_DAO fd = new Friends_DAO(this);
		ud = new User_DAO(this);
		lv = (ListView) findViewById(R.id.listView1);
		friends1 = fd.getFriends(userId);
		for (int i1 = 0; i1 < friends1.size(); i1++) {
			friends.add(ud.getUser(friends1.get(i1)));
			s.add("");
		}
		ca = new CAforSelectFriend(this, friends, s);
		lv.setAdapter(ca);

	}

	public void onClick(View v) {
		Challenges_DAO cd = new Challenges_DAO(this);
		int sum = 0, rp;
		selectedFriends = ca.getSelectedFriends();
		stakepoints = ca.getStakes();
		rp = ud.getUser(userId).getReputation();
		for (int j = 0; j < stakepoints.size(); j++) {
			sum = sum + stakepoints.get(j);
		}
		if (rp < sum)
			Toast.makeText(this, "you dont have enough reputation points",
					Toast.LENGTH_LONG).show();
		for (int j = 0; j < selectedFriends.size(); j++) {
			cd.createChallenge(friends1.get(selectedFriends.get(j)), taskid,
					stakepoints.get(j));
		}

		Intent i = new Intent(this, Homepage.class);
		startActivity(i);
	}
}
