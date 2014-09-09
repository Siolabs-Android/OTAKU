package com.siolabs.asynctasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.otaku.Homepage;

public class LoginAsyncTask extends AsyncTask<String, Void, String> {

	
	Context context = null;
	
	public LoginAsyncTask(Context c){
		this.context = c;
	}


	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		// These two need to be declared outside the try/catch
		// so that they can be closed in the finally block.
		HttpURLConnection urlConnection = null;
		BufferedReader reader = null;
		// Will contain the raw JSON response as a string.
		String response = null;
		try {
			// Construct the URL for the OpenWeatherMap query
			// Possible parameters are avaiable at OWM's forecast API page, at
			// http://openweathermap.org/API#forecast
			URL url = new URL("http://10.0.2.2:8888/user");
			// Create the request to OpenWeatherMap, and open the connection
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setDoInput(true);
			
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setDoOutput(true);
			urlConnection.connect();

			OutputStream writer = urlConnection.getOutputStream();
			String name = params[0];
			String email = params[1];
			String password = params[2];

			JSONObject data = new JSONObject();
			try {
				data.put("name", name);
				data.put("email", email);
				data.put("password", password);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Log.e("Login Async Task", data.toString());
			writer.write(data.toString().getBytes());
			writer.flush();
			writer.close();

			// Read the input stream into a String
			InputStream inputStream = urlConnection.getInputStream();
			StringBuffer buffer = new StringBuffer();
			if (inputStream == null) {
				// Nothing to do.
				response = null;
			}
			reader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = reader.readLine()) != null) {
				// Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
				// But it does make debugging a *lot* easier if you print out the completed
				// buffer for debugging.
				buffer.append(line);
			}

			if (buffer.length() == 0) {
				// Stream was empty. No point in parsing.
				response = null;
			}
			response = buffer.toString();
		} catch (IOException e) {
			Log.e("Login AsyncTask", "Error ", e);
			// If the code didn't successfully get the weather data, there's no point in attemping
			// to parse it.
			response = null;
		} finally{
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (final IOException e) {
					Log.e("Login Async Task", "Error closing stream", e);
				}
			}
			
		}
		return response;
		
	}
	
	@Override
	protected void onPostExecute(String result) {
		Toast.makeText(context, result, Toast.LENGTH_LONG).show();
		
		//TODO
		//Save the user details to the local db
		
		//download the data for the user from the server
		
		//send the user to the home Screen 
		context.startActivity(new Intent(context, Homepage.class));
	}

}
