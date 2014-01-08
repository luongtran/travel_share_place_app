package project.matching;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.matching.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MatchingActivity extends Activity {
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matching);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.matching, menu);
		return true;
		
	}
	public void on_login(View v){
		//Toast.makeText(getBaseContext(), "login",Toast.LENGTH_SHORT).show();
		intent=new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
	public void on_register(View v){
		//Toast.makeText(getApplicationContext(), "Register",Toast.LENGTH_SHORT).show();
		intent=new Intent(this, RegisterActivity.class);
		startActivity(intent);
	}
	public void on_loginFacebook(View v){
		Toast.makeText(getApplicationContext(), "login facebook",Toast.LENGTH_SHORT).show();
	}
	public void on_places(View v){
		intent=new Intent(this, PlacesActivity.class);
		startActivity(intent);
	}
}
