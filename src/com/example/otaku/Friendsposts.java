package com.example.otaku;

import java.util.ArrayList;

import database.Friends_DAO;
import database.Performed_tasks_DAO;
import database.Performed_tasks_table;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class Friendsposts extends Fragment {
	static Context ctx;
	SharedPreferences sf;
	int userId;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_friendsposts,
				container, false);
		ctx=getActivity();
		ArrayList<Performed_tasks_table> tasks = new ArrayList<Performed_tasks_table>();
		ArrayList<Integer> friends = new ArrayList<Integer>();
		Friends_DAO fd=new Friends_DAO(ctx);
		sf=ctx.getSharedPreferences("SP", Context.MODE_PRIVATE);
		ArrayList<String> s = new ArrayList<String>();
		Performed_tasks_DAO ptd = new Performed_tasks_DAO(ctx);
		ListView lv = (ListView) rootView.findViewById(R.id.listView1);
		friends=fd.getFriends(sf.getInt("userId",userId));
		for(int i=0;i<friends.size();i++){
			tasks.addAll(ptd.getPerformedTasks(friends.get(i)));
		}
		for(int i=0;i<tasks.size();i++)
			s.add("");
		CAforPerformedTasks ca = new CAforPerformedTasks(ctx, tasks, s);
		lv.setAdapter(ca);

		return rootView;
	}
	
}
