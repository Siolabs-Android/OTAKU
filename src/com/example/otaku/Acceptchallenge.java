package com.example.otaku;

import database.Challenges_DAO;
import database.Performed_tasks_DAO;
import database.Performed_tasks_table;
import database.User_DAO;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class Acceptchallenge extends Activity {
	int taskid;
	int cid;
	String dp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_challengesubmission);
		Intent i = getIntent();
		taskid = i.getIntExtra("taskid", taskid);
		cid = i.getIntExtra("cid", cid);
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

	public void submitChallenge(View view) {

		Performed_tasks_table ptt = new Performed_tasks_table();
		Performed_tasks_DAO ptd = new Performed_tasks_DAO(this);
		Challenges_DAO cd = new Challenges_DAO(this);
		User_DAO ud = new User_DAO(this);
		int givenBy, givenTo, reputationWon, reputationLost;
		ptt.setTitle(ptd.getTaskById(taskid).getTitle());
		ptt.setDescription(ptd.getTaskById(taskid).getDescription());
		ptt.setProof(dp);
		ptd.createTask(ptt);
		givenTo = cd.getChallengeById(cid).getGiven_To();
		givenBy = cd.getChallengeById(cid).getGiven_By();
		reputationWon = ud.getUser(cd.getChallengeById(cid).getGiven_To())
				.getReputation()
				+ cd.getChallengeById(cid).getReputation_Stake();
		reputationLost = ud.getUser(cd.getChallengeById(cid).getGiven_By())
				.getReputation()
				- cd.getChallengeById(cid).getReputation_Stake();
		ud.updateReputation(givenTo, reputationWon);
		ud.updateReputation(givenBy, reputationLost);
		cd.deleteChallenge(cid);
		Intent i = new Intent(this, Homepage.class);
		startActivity(i);
	}
}
