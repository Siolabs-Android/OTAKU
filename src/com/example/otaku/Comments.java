package com.example.otaku;

import java.util.ArrayList;

import database.Comments_DAO;
import database.Comments_table;
import database.Performed_tasks_DAO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Comments extends Activity {
	int taskid,torc;
	Comments_DAO cd;
	CAforComments ca;
	SharedPreferences sf;
	int userId,p;
	ArrayList<String> s;
	ArrayList<Comments_table> comments;
	Performed_tasks_DAO ptd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comments);
		sf=this.getSharedPreferences("SP", Context.MODE_PRIVATE);
		comments = new ArrayList<Comments_table>();
		 s = new ArrayList<String>();
		 ptd=new Performed_tasks_DAO(this);
		ListView lv = (ListView) findViewById(R.id.listView1);
		cd = new Comments_DAO(this);
		Bundle b = getIntent().getExtras();     
		taskid= b.getInt("taskid");
		torc= b.getInt("torc");
		p= b.getInt("position");
		comments = cd.getComments(taskid,torc);
		
		for(int i=0;i<comments.size();i++)
			s.add("");
		ca = new CAforComments(this, comments, s);
		lv.setAdapter(ca);
	}

	public void comment(View view) {
		EditText et1 = (EditText) findViewById(R.id.editText1);
		String s1 = new String();
		s1 = et1.getText().toString();
		Comments_table ct=cd.createComment(s1,sf.getInt("userId",userId),torc,taskid);
        et1.setText("");
        Toast.makeText(this, ""+taskid,Toast.LENGTH_SHORT).show();
        ptd.updateCommentCount(taskid);
        comments.add(ct);
        s.add("");
        ca.notifyDataSetChanged();
	}
}
