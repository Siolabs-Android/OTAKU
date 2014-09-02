package com.example.otaku;

import database.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Loginactivity extends Activity {

	EditText email;
	EditText password;
	SharedPreferences sf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loginactivity);

	}

	// Button to create an account
	public void createNewAccount(View v) {
		
		Intent i = new Intent(getApplicationContext(), Createaccount.class);
		startActivity(i);
	}

	// Code to login User
	public void login(View v) {
//		User_table ut;
//		User_DAO ud = new User_DAO(this);
//		// Get the data from the editTexts
//		sf = this.getSharedPreferences("SP", Context.MODE_PRIVATE);
//		email = (EditText) findViewById(R.id.editText1);
//		password = (EditText) findViewById(R.id.editText2);
//		String emailID = email.getText().toString();
//		String pass = password.getText().toString();
//		ut = ud.getUser(emailID);
//		if (ut == null)
//			Toast.makeText(this, "This user deosn't exist!!",
//					Toast.LENGTH_SHORT).show();
//
//		else if (!ut.getPassword().equals(pass))
//			Toast.makeText(this, "You have entered a wrong password!!",
//					Toast.LENGTH_SHORT).show();
//
//		else {
//			int p=ut.get_id();
//			SharedPreferences.Editor editor = sf.edit();
//			editor.putString("userEmail", ut.getEmail());
//			editor.putInt("userId", ut.get_id());
//
//			editor.commit();
//
//			Intent i = new Intent(getApplicationContext(), Homepage.class);
//			startActivity(i);
//		}

	}

}
