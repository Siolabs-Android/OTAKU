package com.example.otaku;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import database.User_DAO;
import database.User_table;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Editprofile extends Activity {

	User_table ut;
	User_DAO ud;
	EditText name;
	EditText password1;
	EditText password2;
	EditText city;
	EditText country;
	String dp;
    ImageView iv;
    private TextView Output;
    private Button changeDate;
    Date date1;
    private int year;
    private int month;
    private int day;
    static Date date;
    static final int DATE_PICKER_ID = 1111;
    SharedPreferences sf;
	int userId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editprofile);
		ut = new User_table();
		ud = new User_DAO(this);
		sf=this.getSharedPreferences("SP", Context.MODE_PRIVATE);
		 Output = (TextView) findViewById(R.id.textView4);
	        changeDate = (Button) findViewById(R.id.button3);
	 
	        // Get current date by calender
	         
	        final Calendar c = Calendar.getInstance();
	       

		name = (EditText) findViewById(R.id.editText2);
		password1 = (EditText) findViewById(R.id.editText5);
		password2 = (EditText) findViewById(R.id.editText6);
		city = (EditText) findViewById(R.id.editText3);
		country = (EditText) findViewById(R.id.editText4);
		iv=(ImageView)findViewById(R.id.imageView1);
		ut = ud.getUser(sf.getInt("userId",userId));

		name.setText(ut.getName());
		password1.setText(ut.getPassword());
		password2.setText(ut.getPassword());
		city.setText(ut.getCity());
		country.setText(ut.getCountry());
		iv.setImageBitmap(BitmapFactory.decodeFile(ut.getDp()));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy");
        String currentDate = simpleDateFormat.format(ut.getDob());
       Output.setText(currentDate);

		
	        changeDate.setOnClickListener(new OnClickListener() {
	 
	            @Override
	            public void onClick(View v) {
	                showDialog(DATE_PICKER_ID);
	            }
	 
	        });
	}
	protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_PICKER_ID:
             
            
            return new DatePickerDialog(this, pickerListener, year, month,day);
        }
        return null;
    }
 
    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
 
        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                int selectedMonth, int selectedDay) {
             
            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;
            date=new Date(year-1900,month,day);
           date1=new Date(date.getTime());
            
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy");
            String currentDate = simpleDateFormat.format(date);
            Output.setText(currentDate); 
            
     
           }
        };

	public void browseImage(View view) {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

		startActivityForResult(i, 1);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			dp = cursor.getString(columnIndex);
			cursor.close();

			iv.setImageBitmap(BitmapFactory.decodeFile(dp));

		}

	}

	public void update(View view) {
		if (password1.getText().toString()
				.equals(password2.getText().toString())) {
			ut.setName(name.getText().toString());
			
			ut.setPassword(password1.getText().toString());
			ut.setCity(city.getText().toString());
			ut.setCountry(country.getText().toString());
			ut.setDob(date);
			ut.setDp(dp);
			ud.editUser(ut);
			Intent i = new Intent(this, Homepage.class);
			startActivity(i);
		}
	}

}
