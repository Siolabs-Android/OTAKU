package com.example.otaku;

import java.util.ArrayList;

import database.User_DAO;
import database.User_table;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class Searchresults extends Activity {
	SharedPreferences sf;
	int userId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchresults);
		sf=this.getSharedPreferences("SP", Context.MODE_PRIVATE);
	}

	public void publicSearch(View view) {
		User_DAO ud = new User_DAO(this);
		CAforSearchResults ca;
		EditText et = (EditText) findViewById(R.id.editText1);
		ListView lv = (ListView) findViewById(R.id.listView1);
		ArrayList<String> s1 = new ArrayList<String>();
		String s = et.getText().toString();
		final ArrayList<User_table> user = ud.publicSearch(s);
		for(int i=0;i<user.size();i++)
			s1.add("");

		ca = new CAforSearchResults(this, user, s1);
		lv.setAdapter(ca);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				OpenProfileOnClick(user.get(position).get_id());

			}
		});

	}

	public void OpenProfileOnClick(int id) {

		if (id ==sf.getInt("userId",userId)) {
			Intent i = new Intent(this, Myprofile.class);
			startActivity(i);
		} else {
			Intent i = new Intent(this, Publicprofile.class);
			i.putExtra("_id", id);
			startActivity(i);
		}
	}
}
