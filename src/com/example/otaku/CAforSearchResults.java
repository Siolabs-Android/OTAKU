package com.example.otaku;

import java.util.ArrayList;

import database.User_table;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CAforSearchResults extends ArrayAdapter {
	Context ctx;
	ArrayList<User_table> user;
	SharedPreferences sf;
	int userId;
	public CAforSearchResults(Context ctx, ArrayList<User_table> user,
			ArrayList<String> s) {
		super(ctx, R.layout.searchresult, R.id.textView1, s);
		this.ctx = ctx;
		this.user = user;
		sf=ctx.getSharedPreferences("SP", Context.MODE_PRIVATE);
        userId=sf.getInt("userId",0);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.searchresult, parent, false);
		}
		 
		ImageView iv1 = (ImageView) row.findViewById(R.id.imageView1);
		TextView tv1 = (TextView) row.findViewById(R.id.textView1);
		final int p=position;
		if (position < user.size()) {
			if(user.get(position).getDp()!=null){
			iv1.setImageBitmap(BitmapFactory.decodeFile(user.get(position).getDp()));	
			}
			else
				iv1.setImageResource(R.drawable.ic_launcher);
			tv1.setText(user.get(position).getName());
			tv1.setClickable(true);
			tv1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					OpenProfileOnClick(user.get(p).get_id());
					
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
