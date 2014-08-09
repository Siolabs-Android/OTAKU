package com.example.otaku;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.sax.StartElementListener;
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

public class CAforChallenges extends ArrayAdapter {
	SharedPreferences sf;
	Context ctx;
	ArrayList<Challenges_table> challenge;
	Votes_DAO vd ;
	int userId;

	public CAforChallenges(Context ctx, ArrayList<Challenges_table> challenge,
			ArrayList<String> s) {
		super(ctx, R.layout.myperformedtasks, R.id.textView1, s);
		this.ctx = ctx;
		this.challenge = challenge;
		
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
		final Button upvote = (Button) row.findViewById(R.id.button1);
		Button comments = (Button) row.findViewById(R.id.button3);
		Button acceptchallenge = (Button) row.findViewById(R.id.button4);
		User_DAO ud = new User_DAO(ctx);
		vd= new Votes_DAO(ctx);
		sf=ctx.getSharedPreferences("SP", Context.MODE_PRIVATE);
		Performed_tasks_DAO ptd = new Performed_tasks_DAO(ctx);
		if (position < challenge.size()) {
			acceptchallenge.setText("Accept");
			iv1.setImageBitmap(BitmapFactory.decodeFile(ptd.getTaskById(
					challenge.get(position).getTask_Id()).getProof()));
			username.setText(ud.getUser(challenge.get(position).getGiven_By())
					.getName());
			challengetitle.setText(ptd.getTaskById(
					challenge.get(position).getTask_Id()).getTitle());
			date.setText((challenge.get(position).getGiven_At()).toString());
			description.setText(ptd.getTaskById(
					challenge.get(position).getTask_Id()).getDescription());
			Votes_table vt=vd.getVoteStatus(challenge.get(position).get_id(),0,sf.getInt("userId",userId));
			if(vt!=null)
				upvote.setPressed(true);
			final int p = position;
			upvote.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					
					 
					if (!(upvote.isPressed() )) {
						vd.createVote(sf.getInt("userId",userId), challenge.get(p)
								.get_id(),0);
						upvote.setPressed(true);
						upvote.setBackgroundColor(Color.GRAY);
					}
				}
			});

			
			comments.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(ctx, Comments.class);
					i.putExtra("taskid", challenge.get(p).get_id());
					i.putExtra("torc",1);
					ctx.startActivity(i);
				}
			});

			acceptchallenge.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(ctx, Acceptchallenge.class);
					i.putExtra("taskid", challenge.get(p).getTask_Id());
					i.putExtra("cid",challenge.get(p).get_id());
					ctx.startActivity(i);
				}
			});
		}

		return row;
	}

}
