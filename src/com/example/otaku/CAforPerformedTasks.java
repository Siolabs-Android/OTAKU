package com.example.otaku;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import database.Performed_tasks_DAO;
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
import android.graphics.drawable.ColorDrawable;
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
	Votes_DAO vd;
	Performed_tasks_DAO ptd;

	public CAforPerformedTasks(Context ctx,
			ArrayList<Performed_tasks_table> tasks, ArrayList<String> s) {
		super(ctx, R.layout.myperformedtasks, R.id.textView1, s);
		this.ctx = ctx;
		this.tasks = tasks;

	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.myperformedtasks, parent, false);
		}
		sf = ctx.getSharedPreferences("SP", Context.MODE_PRIVATE);
		ImageView iv1 = (ImageView) row.findViewById(R.id.imageView1);
		TextView username = (TextView) row.findViewById(R.id.textView1);
		TextView tasktitle = (TextView) row.findViewById(R.id.textView2);
		TextView date = (TextView) row.findViewById(R.id.textView4);
		TextView description = (TextView) row.findViewById(R.id.textView3);
		final Button upvote = (Button) row.findViewById(R.id.button1);
		final Button comments = (Button) row.findViewById(R.id.button3);
		Button givechallenge = (Button) row.findViewById(R.id.button4);
		givechallenge.setVisibility(View.GONE);
		User_DAO ud = new User_DAO(ctx);
		vd = new Votes_DAO(ctx);
		ptd = new Performed_tasks_DAO(ctx);
		upvote.setTag((Integer) position);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"MMM dd,yyyy HH:MM");
		if (position < tasks.size()) {
			iv1.setImageBitmap(BitmapFactory.decodeFile(tasks.get(position)
					.getProof()));
			username.setText(ud.getUser(tasks.get(position).getSubmitted_By())
					.getName());
			tasktitle.setText(tasks.get(position).getTitle());
			date.setText(simpleDateFormat.format(tasks.get(position)
					.getSubmitted_At()));
			description.setText(tasks.get(position).getDescription());
			comments.setText("Comment..."
					+ tasks.get(position).getComment_Count());
			Votes_table vt = vd.getVoteStatus(tasks.get(position).get_id(), 1,
					sf.getInt("userId", userId));

			upvote.setText("Upvote..." + tasks.get(position).getUpvote_Count());

			if (vt != null) {
				upvote.setPressed(true);
				upvote.setBackgroundColor(Color.YELLOW);

			} else {
				upvote.setPressed(false);
				upvote.setBackgroundColor(Color.BLUE);

			}

			final ColorDrawable buttonColor = (ColorDrawable) upvote
					.getBackground();
			upvote.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (buttonColor.getColor() == Color.BLUE) {
						int p = (Integer) upvote.getTag();
						vd.createVote(sf.getInt("userId", userId), tasks.get(p)
								.get_id(), 1);
						ptd.updateUpvoteCount(tasks.get(p).get_id());
						upvote.setText("Upvote..."
								+ ptd.getTaskById(tasks.get(p).get_id())
										.getUpvote_Count());
						upvote.setPressed(true);
						upvote.setBackgroundColor(Color.YELLOW);
					}
				}
			});
			comments.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					int p = (Integer) upvote.getTag();
					Intent i = new Intent(ctx, Comments.class);
					i.putExtra("taskid", tasks.get(p).get_id());
					i.putExtra("torc", 1);
					i.putExtra("position", p);
					ctx.startActivity(i);
				}
			});
		}

		return row;
	}

}
