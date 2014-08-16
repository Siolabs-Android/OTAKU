package com.example.otaku;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
	ArrayList<Integer> stakepoints = new ArrayList<Integer>();
	SharedPreferences sf;
	int userId;
	
	
	ArrayList<Integer> checkedfriends = new ArrayList<Integer>();

	public CAforSelectFriend(Context ctx, ArrayList<User_table> friends,
			ArrayList<String> s) {
		super(ctx, R.layout.selectfriend, R.id.textView1, s);
		this.ctx = ctx;
		this.friends = friends;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) ctx
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.selectfriend, parent, false);
		}
		sf = ctx.getSharedPreferences("SP", Context.MODE_PRIVATE);
		ImageView iv1 = (ImageView) row.findViewById(R.id.imageView1);
		final TextView tv1 = (TextView) row.findViewById(R.id.textView1);
		final EditText et1 = (EditText) row.findViewById(R.id.editText1);
		final CheckBox cb = (CheckBox) row.findViewById(R.id.checkBox1);
		tv1.setClickable(true);
		if (position < friends.size()) {
			if (friends.get(position).getDp() != null)
				iv1.setImageBitmap(BitmapFactory.decodeFile(friends.get(
						position).getDp()));
			tv1.setText(friends.get(position).getName());
			et1.setText("1");
			
			final Button temp = (Button) row.findViewById(R.id.button1);
			temp.setVisibility(View.GONE);
			temp.setTag(position);
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					int p = (Integer) temp.getTag();
					et1.setText(et1.getText().toString());
					int rs=Integer.parseInt(et1.getText().toString());
					if (isChecked)
						addCheckedFriendsInArray(p,rs);
					else
						removeUncheckedFriendsInArray(p);
				}
			});

			tv1.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					int p = (Integer) temp.getTag();
					OpenProfileOnClick(friends.get(p).get_id());
					
				}
			});
			et1.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					int p = (Integer) temp.getTag();
					cb.setChecked(false);
					
				}
			});

		}
		return row;
	}

	public void addCheckedFriendsInArray(int p,int rs) {
		for (int i = 0; i < checkedfriends.size(); i++)
			if (checkedfriends.get(i) == p)
				return;
		checkedfriends.add(p);
		stakepoints.add(rs);
	}

	public void removeUncheckedFriendsInArray(int p) {

		checkedfriends.remove(Integer.valueOf(p));
		stakepoints.remove(Integer.valueOf(p));
	}

	public ArrayList<Integer> getSelectedFriends() {
		return checkedfriends;
	}

	public ArrayList<Integer> getStakes() {
		return stakepoints;
	}

	public void OpenProfileOnClick(int id) {

		if (id == sf.getInt("userId", userId)) {
			Intent i = new Intent(ctx, Myprofile.class);
			ctx.startActivity(i);
		} else {
			Intent i = new Intent(ctx, Publicprofile.class);
			i.putExtra("_id", id);
			ctx.startActivity(i);
		}
	}
}
