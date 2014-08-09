package com.example.otaku;

import java.util.ArrayList;

import database.Performed_tasks_table;
import database.User_DAO;
import database.User_table;
import database.Votes_DAO;
import database.Votes_table;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CAforPerformedTasks extends ArrayAdapter {
	Context ctx;
	ArrayList<Performed_tasks_table> tasks;
	SharedPreferences sf;
	int userId;

	public CAforPerformedTasks(Context ctx,
			ArrayList<Performed_tasks_table> tasks, ArrayList<String> s) {
		super(ctx, R.layout.myperformedtasks, R.id.textView1, s);
		this.ctx = ctx;
		this.tasks = tasks;
		sf=ctx.getSharedPreferences("SP", Context.MODE_PRIVATE);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.myperformedtasks, parent, false);
		}

		ImageView iv1 = (ImageView) row.findViewById(R.id.imageView1);
		TextView username = (TextView) row.findViewById(R.id.textView1);
		TextView tasktitle = (TextView) row.findViewById(R.id.textView2);
		TextView date = (TextView) row.findViewById(R.id.textView4);
		TextView description = (TextView) row.findViewById(R.id.textView3);
		final Button upvote = (Button) row.findViewById(R.id.button1);
		final Button comments = (Button) row.findViewById(R.id.button3);
		Button a = (Button) row.findViewById(R.id.button4);
		a.setVisibility(View.GONE);
		User_DAO ud = new User_DAO(ctx);
		final Votes_DAO vd = new Votes_DAO(ctx);
		
		
		final int p = position;
		if (position < tasks.size()) {
			Votes_table vt=vd.getVoteStatus(tasks.get(position).get_id(),1,sf.getInt("userId",userId));
			if(vt!=null)
			
				upvote.setPressed(true);
			
			iv1.setImageBitmap(BitmapFactory.decodeFile(tasks.get(position).getProof()));
			username.setText(ud.getUser(tasks.get(position).getSubmitted_By()).getName());
			tasktitle.setText(tasks.get(position).getTitle());
			date.setText((tasks.get(position).getSubmitted_At()).toString());
			description.setText(tasks.get(position).getDescription());

			upvote.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (!(upvote.isPressed() )) {
						vd.createVote(sf.getInt("userId",userId), tasks.get(p).get_id(), 1);
						upvote.setPressed(true);
						upvote.setBackgroundColor(Color.GRAY);
					}
				}
			});
			
			comments.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(ctx, Comments.class);
					i.putExtra("taskid",tasks.get(p)
							.get_id());
					i.putExtra("torc",1);
					ctx.startActivity(i);
				}
			});

		}

		return row;
	}

}
