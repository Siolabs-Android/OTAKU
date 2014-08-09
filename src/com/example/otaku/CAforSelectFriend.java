package com.example.otaku;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import database.Challenges_DAO;
import database.Friends_DAO;
import database.Friends_table;
import database.User_table;

public class CAforSelectFriend extends ArrayAdapter {
	Context ctx;
	ArrayList<User_table> friends = new ArrayList<User_table>();
	static ArrayList<Integer> stakepoints = new ArrayList<Integer>();
	SharedPreferences sf;
	int userId;
	public CAforSelectFriend(Context ctx, ArrayList<User_table> friends,
			ArrayList<String> s) {
		super(ctx, R.layout.selectfriend, R.id.textView1, s);
		this.ctx = ctx;
		this.friends = friends;
		sf=ctx.getSharedPreferences("SP", Context.MODE_PRIVATE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.selectfriend, parent, false);
		}

		ImageView iv1 = (ImageView) row.findViewById(R.id.imageView1);
		TextView tv1 = (TextView) row.findViewById(R.id.textView1);
		EditText et1 = (EditText) row.findViewById(R.id.editText1);
		tv1.setClickable(true);
		if (position < friends.size()) {
			iv1.setImageBitmap(BitmapFactory.decodeFile(friends.get(position).getDp()));
			tv1.setText(friends.get(position).getName());
			et1.setText(1);
			stakepoints.add(Integer.parseInt(et1.getText().toString()));
			final int p = position;

			tv1.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					OpenProfileOnClick(friends.get(p).get_id());
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
