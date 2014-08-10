package com.example.otaku;

import database.Performed_tasks_DAO;
import database.Performed_tasks_table;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Tasksubmission extends Activity {
	String dp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tasksubmission);
		

	}

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

			ImageView imageView = (ImageView) findViewById(R.id.imageView1);
			imageView.setImageBitmap(BitmapFactory.decodeFile(dp));

		}
	}

	public void submitTask(View view) {
		EditText title = (EditText) findViewById(R.id.editText1);
		EditText description = (EditText) findViewById(R.id.editText2);
		Performed_tasks_table ptt = new Performed_tasks_table();
		Performed_tasks_DAO ptd = new Performed_tasks_DAO(this);
		ptt.setTitle(title.getText().toString());
		ptt.setDescription(description.getText().toString());
		ptt.setProof(dp);
		long p=ptd.createTask(ptt);
		Intent i = new Intent(this, Homepage.class);
		startActivity(i);
	}
}
