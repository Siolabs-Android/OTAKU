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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Searchresults extends Activity {
	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchresults);
		lv = (ListView) findViewById(R.id.listView1);
	}

	public void publicSearch(View view) {
		User_DAO ud = new User_DAO(this);
		EditText et = (EditText) findViewById(R.id.editText1);
		ArrayList<String> s1 = new ArrayList<String>();
		String s = et.getText().toString();
		ArrayList<User_table> user = ud.publicSearch(s);
		for (int i = 0; i < user.size(); i++)
			s1.add("");
		//Toast.makeText(this, user.get(0).getDp(),Toast.LENGTH_SHORT).show();
		CAforSearchResults ca = new CAforSearchResults(this, user, s1);
		lv.setAdapter(ca);

	}

}
