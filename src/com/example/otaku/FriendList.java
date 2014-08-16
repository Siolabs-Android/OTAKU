package com.example.otaku;

import java.util.ArrayList;

import database.Friends_DAO;
import database.Friends_table;
import database.Performed_tasks_DAO;
import database.Performed_tasks_table;
import database.User_DAO;
import database.User_table;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FriendList extends Fragment {
	Context ctx;
	SharedPreferences sf;
	int userId;
	User_DAO ud ;
	CAforFriendRequest ca1;
	static ArrayList<User_table> requests;
	static ArrayList<User_table> friends;
	static ArrayList<String> s;
	static CAforFriends ca2 ;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_friend_list,
				container, false);
		ctx=getActivity();
		sf=ctx.getSharedPreferences("SP", Context.MODE_PRIVATE);
		ud = new User_DAO(ctx);
		ArrayList<Integer> requests1 = new ArrayList<Integer>();
		ArrayList<Integer> friends1 = new ArrayList<Integer>();
		requests = new ArrayList<User_table>();
		friends = new ArrayList<User_table>();
		s = new ArrayList<String>();
		ArrayList<String> s1 = new ArrayList<String>();
		Friends_DAO fd = new Friends_DAO(ctx);
		User_DAO ud = new User_DAO(ctx);
		ListView lv1 = (ListView) rootView.findViewById(R.id.listView1);
		ListView lv2 = (ListView) rootView.findViewById(R.id.listView2);
		requests1 = fd.getFriendRequests(sf.getInt("userId", userId));
		friends1 = fd.getFriends(sf.getInt("userId", userId));
		for (int i = 0; i < friends1.size(); i++) {
			friends.add(ud.getUser(friends1.get(i)));
			s.add("");
		}
		for (int i = 0; i < requests1.size(); i++) {
			requests.add(ud.getUser(requests1.get(i)));
			s1.add("");
		}

		ca1 = new CAforFriendRequest(ctx, requests, s1);
		ca2 = new CAforFriends(ctx, friends, s);
		lv1.setAdapter(ca1);
		lv2.setAdapter(ca2);

		return rootView;
	}
	public static void addFriend(int p){
		friends.add(requests.get(p));
		s.add("");
		ca2.notifyDataSetChanged();
	}
	
}
