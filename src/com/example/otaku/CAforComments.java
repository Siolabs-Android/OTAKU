package com.example.otaku;

import java.util.ArrayList;

import database.Comments_table;
import database.Friends_table;
import database.User_DAO;
import database.User_table;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CAforComments extends ArrayAdapter{
	SharedPreferences sf;
	int userId;
	Context ctx;
	ArrayList<Comments_table> comments;
	User_table ut=new User_table();
	User_DAO ud;
	 public CAforComments(Context ctx,ArrayList<Comments_table> comments,ArrayList<String> s){
		super(ctx,R.layout.comment, R.id.textView1,s);
		this.ctx = ctx;
		this.comments=comments;
		sf=ctx.getSharedPreferences("SP", Context.MODE_PRIVATE);
	}
	 @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row=convertView;
			if(row==null)
			{
				LayoutInflater inflater =(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row= inflater.inflate(R.layout.comment, parent, false);	
			}
			

			ud =new User_DAO(ctx);
			TextView tv1 = (TextView)row.findViewById(R.id.textView1);
			TextView tv2 = (TextView)row.findViewById(R.id.textView2);
			final int p=position;
			if(position<comments.size()){
			
			tv1.setText(ud.getUser(comments.get(position).getCommentatorId()).getName());
			tv2.setText(comments.get(position).getComment());
			tv1.setClickable(true);
			tv1.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					OpenProfileOnClick(ud.getUser(comments.get(p).getCommentatorId()).get_id());	
				}
			});
			}
			return row;
		}
	 public void OpenProfileOnClick(int id) {

			if (id == sf.getInt("userId",userId)) {
				Intent i = new Intent(ctx, Homepage.class);
				ctx.startActivity(i);
			} else {
				Intent i = new Intent(ctx, Publicprofile.class);
				i.putExtra("_id", id);
				ctx.startActivity(i);
			}
		}
}

