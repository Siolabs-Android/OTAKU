package com.example.otaku;

import com.siolabs.asynctasks.LoginAsyncTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import database.*;

public class Createaccount extends Activity {
	String name, email, password1, password2;
	EditText etName, etEmail, etPassword;
	SharedPreferences sf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_createaccount);
		sf=this.getSharedPreferences("SP", Context.MODE_PRIVATE);
		etName = (EditText) findViewById(R.id.etName);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etPassword = (EditText) findViewById(R.id.etPass);
		
	}
	
	

	public void create(View view) {
		name = etName.getText().toString();
		email = etEmail.getText().toString();
		password1 = etPassword.getText().toString();
		
		 
		
		//send to server
		new LoginAsyncTask(this).execute(name,email,password1);
		
		//get the status from server 
		
		//show to user the homescreen
		
//		User_table ut;
//		User_DAO ud = new User_DAO(this);
//		ut = ud.getUser(email);
//		if (ut == null) {
//			if (!password1.equals(password2)) {
//				Toast.makeText(this, "enter same password in both fields",
//						Toast.LENGTH_SHORT).show();
//			} else {
//                ut=new User_table();
//				ut.setName(name);
//				ut.setEmail(email);
//				ut.setPassword(password1);
//				ud.createUser(ut);
//				ut=ud.getUser(email);
//				sf = this.getSharedPreferences("SP", Context.MODE_PRIVATE);
//				SharedPreferences.Editor editor = sf.edit();
//				editor.putString("userEmail", ut.getEmail());
//				editor.putInt("userId", ut.get_id());
//				editor.commit();
//				Intent i = new Intent(this, Homepage.class);
//				startActivity(i);
//			}
//		} else
//			Toast.makeText(this, "This user Already exist", Toast.LENGTH_SHORT)
//					.show();
	}
}
