package com.example.otaku;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import database.Challenges_DAO;
import database.Challenges_table;
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
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Myprofile extends Fragment {
	int id;

	TextView name, reputationpoint, dob, city, country;
	ImageView dp;
	Button friend, performedtasks, challenges;
	ListView lv;
	View rootView;
	SharedPreferences sf;
	int userId;
	Context ctx;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.activity_myprofile, container,
				false);
		ctx = getActivity();
		User_DAO ud = new User_DAO(ctx);
		sf = ctx.getSharedPreferences("SP", Context.MODE_PRIVATE);
		userId = sf.getInt("userId", 0);
		User_table user = ud.getUser(userId);
		
		findViewByIds();

		name.setText(user.getName());
		reputationpoint.setText("" + user.getReputation());

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy");
		String currentDate = simpleDateFormat.format(user.getDob());
		dob.setText(currentDate);
		city.setText(user.getCity());
		country.setText(user.getCountry());
		if (user.getDp() != null)
			dp.setImageBitmap(BitmapFactory.decodeFile(user.getDp()));
		showPerformedTasks();

		performedtasks.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ArrayList<Performed_tasks_table> tasks = new ArrayList<Performed_tasks_table>();
				ArrayList<String> s = new ArrayList<String>();
				Performed_tasks_DAO ptd = new Performed_tasks_DAO(ctx);
				performedtasks.setBackgroundColor(Color.GREEN);
				performedtasks.setTextColor(Color.WHITE);
				challenges.setBackgroundColor(Color.RED);
				challenges.setTextColor(Color.WHITE);
				tasks = ptd.getPerformedTasks(sf.getInt("userId", userId));
				for (int i = 0; i < tasks.size(); i++)
					s.add("");
				CAforMyPerformedTasks ca = new CAforMyPerformedTasks(ctx,
						tasks, s);

				lv.setAdapter(ca);
			}
		});
		challenges.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ArrayList<Challenges_table> challenge = new ArrayList<Challenges_table>();
				ArrayList<String> s = new ArrayList<String>();
				Challenges_DAO cd = new Challenges_DAO(ctx);
				performedtasks.setBackgroundColor(Color.RED);
				performedtasks.setTextColor(Color.WHITE);
				challenges.setBackgroundColor(Color.GREEN);
				challenges.setTextColor(Color.WHITE);
				challenge = cd.getChallenges(sf.getInt("userId", userId));
				for (int i = 0; i < challenge.size(); i++)
					s.add("");
				CAforChallenges ca = new CAforChallenges(ctx, challenge, s);

				lv.setAdapter(ca);
			}
		});

		return rootView;
	}

	private void findViewByIds() {
		name = (TextView) rootView.findViewById(R.id.textView1);
		reputationpoint = (TextView) rootView.findViewById(R.id.textView2);
		dob = (TextView) rootView.findViewById(R.id.textView3);
		city = (TextView) rootView.findViewById(R.id.textView4);
		country = (TextView) rootView.findViewById(R.id.textView5);
		lv = (ListView) rootView.findViewById(R.id.listView1);
		friend = (Button) rootView.findViewById(R.id.button1);
		performedtasks = (Button) rootView.findViewById(R.id.button2);
		challenges = (Button) rootView.findViewById(R.id.button3);
		dp = (ImageView) rootView.findViewById(R.id.imageView2);

	}

	public void showPerformedTasks() {
		ArrayList<Performed_tasks_table> tasks = new ArrayList<Performed_tasks_table>();
		ArrayList<String> s = new ArrayList<String>();
		Performed_tasks_DAO ptd = new Performed_tasks_DAO(ctx);
		performedtasks.setBackgroundColor(Color.GREEN);
		performedtasks.setTextColor(Color.WHITE);
		challenges.setBackgroundColor(Color.RED);
		challenges.setTextColor(Color.WHITE);
		tasks = ptd.getPerformedTasks(sf.getInt("userId", userId));
		for (int i = 0; i < tasks.size(); i++)
			s.add("");
		CAforMyPerformedTasks ca = new CAforMyPerformedTasks(ctx, tasks, s);
		lv.setAdapter(ca);
	}

}
