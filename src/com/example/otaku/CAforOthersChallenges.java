package com.example.otaku;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
import database.Challenges_table;
import database.Performed_tasks_DAO;
import database.Performed_tasks_table;
import database.User_DAO;
import database.Votes_DAO;
import database.Votes_table;

public class CAforOthersChallenges extends ArrayAdapter {
	Context ctx;
	ArrayList<Challenges_table> challenge;
	Button upvote, comments;
	SharedPreferences sf;
	int userId;

	public CAforOthersChallenges(Context ctx,
			ArrayList<Challenges_table> challenge, ArrayList<String> s) {
		super(ctx, R.layout.myperformedtasks, R.id.textView1, s);
		this.ctx = ctx;
		this.challenge = challenge;
		sf = ctx.getSharedPreferences("SP", Context.MODE_PRIVATE);
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
		TextView challengetitle = (TextView) row.findViewById(R.id.textView2);
		TextView date = (TextView) row.findViewById(R.id.textView4);
		TextView description = (TextView) row.findViewById(R.id.textView3);
		upvote = (Button) row.findViewById(R.id.button1);
		comments = (Button) row.findViewById(R.id.button3);
		Button acceptchallenge = (Button) row.findViewById(R.id.button4);
		TextView reputationStake = (TextView) row.findViewById(R.id.textView5);
		acceptchallenge.setVisibility(View.GONE);
		User_DAO ud = new User_DAO(ctx);
		Performed_tasks_DAO ptd = new Performed_tasks_DAO(ctx);
		final Votes_DAO vd = new Votes_DAO(ctx);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"MMM dd,yyyy HH:MM");
		final int p = position;
		if (position < challenge.size()) {
			iv1.setImageBitmap(BitmapFactory.decodeFile(ptd.getTaskById(
					challenge.get(position).getTask_Id()).getProof()));

			username.setText(ud.getUser(challenge.get(position).getGiven_By())
					.getName());
			challengetitle.setText(ptd.getTaskById(
					challenge.get(position).getTask_Id()).getTitle());
			date.setText(simpleDateFormat.format(challenge.get(position)
					.getGiven_At()));
			reputationStake.setText(""
					+ challenge.get(position).getReputation_Stake());
			description.setText(ptd.getTaskById(
					challenge.get(position).getTask_Id()).getDescription());
			Votes_table vt = vd.getVoteStatus(challenge.get(position).get_id(),
					0, sf.getInt("userId", userId));
			if (vt != null)

				upvote.setPressed(true);

			upvote.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (!(upvote.isPressed())) {
						vd.createVote(sf.getInt("userId", userId), challenge
								.get(p).get_id(), 0);
						upvote.setPressed(true);
						upvote.setBackgroundColor(Color.GRAY);
					}
				}
			});

			comments.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(ctx, Comments.class);
					i.putExtra("taskid", challenge.get(p).get_id());
					i.putExtra("torc", 0);
					ctx.startActivity(i);
				}
			});

		}
		return row;
	}

}
