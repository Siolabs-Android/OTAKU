package com.example.otaku;

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
	EditText et1, et2, et3, et4;
	SharedPreferences sf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_createaccount);
		sf=this.getSharedPreferences("SP", Context.MODE_PRIVATE);
		et1 = (EditText) findViewById(R.id.editText2);
		et2 = (EditText) findViewById(R.id.editText3);
		et3 = (EditText) findViewById(R.id.editText5);
		et4 = (EditText) findViewById(R.id.editText6);
	}

	public void create(View view) {
		name = et1.getText().toString();
		email = et2.getText().toString();
		password1 = et3.getText().toString();
		password2 = et4.getText().toString();
		User_table ut;
		User_DAO ud = new User_DAO(this);
		ut = ud.getUser(email);
		if (ut == null) {
			if (!password1.equals(password2)) {
				Toast.makeText(this, "enter same password in both fields",
						Toast.LENGTH_SHORT).show();
			} else {
                ut=new User_table();
				ut.setName(name);
				ut.setEmail(email);
				ut.setPassword(password1);
				ud.createUser(ut);
				ut=ud.getUser(email);
				sf = this.getSharedPreferences("SP", Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sf.edit();
				editor.putString("userEmail", ut.getEmail());
				editor.putInt("userId", ut.get_id());
				editor.commit();
				Intent i = new Intent(this, Homepage.class);
				startActivity(i);
			}
		} else
			Toast.makeText(this, "This user Already exist", Toast.LENGTH_SHORT)
					.show();
	}
}
