package com.example.otaku;

import java.util.ArrayList;

import database.Friends_DAO;
import database.Friends_table;
import database.User_DAO;
import database.User_table;

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

public class CAforFriendRequest extends ArrayAdapter{
	Context ctx;
	SharedPreferences sf;
	int userId;
	ArrayList<User_table> requests = new ArrayList<User_table>();
	 public CAforFriendRequest(Context ctx,ArrayList<User_table> requests,ArrayList<String> s){
		super(ctx,R.layout.friendrequest, R.id.textView1,s);
		this.ctx = ctx;
		this.requests=requests;
		sf=ctx.getSharedPreferences("SP", Context.MODE_PRIVATE);
	}
	 @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row=convertView;
			if(row==null)
			{
				LayoutInflater inflater =(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row= inflater.inflate(R.layout.friendrequest, parent, false);	
			}
			

			ImageView iv1 =(ImageView) row.findViewById(R.id.imageView1);
			TextView tv1 = (TextView)row.findViewById(R.id.textView1);
			Button accept=(Button)row.findViewById(R.id.button1);
			Button reject=(Button)row.findViewById(R.id.button2);
			if(position<requests.size()){
			iv1.setImageBitmap(BitmapFactory.decodeFile(requests.get(position).getDp()));
			tv1.setText(requests.get(position).getName());
			tv1.setClickable(true);
		    accept.setBackgroundColor(Color.GREEN) ;
		    accept.setTextColor(Color.WHITE) ;
		    reject.setBackgroundColor(Color.RED) ;
		    reject.setTextColor(Color.WHITE) ;
		    final int p=position;
		    final Friends_DAO fd=new Friends_DAO(ctx);
		    accept.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 fd.acceptFriend(requests.get(p).get_id());
	            	 requests.remove(p);
	             }
	         });
		    reject.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 fd.deleteFriend(requests.get(p).get_id());
	            	 requests.remove(p);
	             }
	         });
		    tv1.setClickable(true);
		    tv1.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 OpenProfileOnClick(requests.get(p).get_id());	 
	             }
	         });
			}
			return row;
		}
	 public void OpenProfileOnClick(int id) {

			if (id == sf.getInt("userId",userId)) {
				Intent i = new Intent(ctx, Myprofile.class);
				ctx.startActivity(i);
			} else {
				Intent i = new Intent(ctx, Publicprofile.class);
				i.putExtra("_id", id);
				ctx.startActivity(i);
			}
		}
}
