package com.example.otaku;

import java.util.ArrayList;

import database.Performed_tasks_DAO;
import database.Performed_tasks_table;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ListView;

public class Topposts extends Fragment {
	static Context ctx;
	SharedPreferences sf;
	int userId;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_topposts, container,
				false);
		sf=ctx.getSharedPreferences("SP", Context.MODE_PRIVATE);
		ArrayList<Performed_tasks_table> tasks = new ArrayList<Performed_tasks_table>();
		ArrayList<String> s = new ArrayList<String>();
		Performed_tasks_DAO ptd = new Performed_tasks_DAO(ctx);
		ListView lv = (ListView) rootView.findViewById(R.id.listView1);
		tasks = ptd.getTopposts();
		for(int i=0;i<tasks.size();i++)
			s.add("");
		CAforPerformedTasks ca = new CAforPerformedTasks(ctx, tasks, s);
		lv.setAdapter(ca);

		return rootView;
	}
	 public void onAttach(Activity activity) {
	        super.onAttach(activity);
	       ctx=activity;
	    }
}
