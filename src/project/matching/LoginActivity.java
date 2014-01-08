package project.matching;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import matching.classmain.RequestHTTPGet;
import matching.classmain.RequestHTTPPost;
import matching.functioninterface.ConnectServer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.matching.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.app.Activity;
import android.database.CursorJoiner.Result;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements ConnectServer{
	EditText ed_email,ed_password;
	private String path,email,password;
	
	public static int login;
	private String ab;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
		/*
		new request(){
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				
				ab=result;
				Log.i("result",""+result);
				super.onPostExecute(result);
			}
		}.execute(" ");
		
		Timer timer=new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.i("ab",""+ab);
				//Toast.makeText(getApplicationContext(), "result: "+ab, Toast.LENGTH_SHORT).show();
			}
		},3000);*/
		
	}
	public void init(){
		ed_email=(EditText)findViewById(R.id.edit_email);
		ed_password=(EditText)findViewById(R.id.edit_password);
		ed_email.setText("");
		ed_password.setText("");
		path="";
		login=0;
		//requestServer=new RequestServer();
	}
	
	//set
	public void setLogin(int login) {
		this.login = login;
	}
	//get
	public int getLogin() {
		return login;
	}
	public void on_checklogin(View v){
		Toast.makeText(getApplicationContext(), "result: "+getLogin(), Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.matching, menu);
		return true;
	}
	
	public void on_login(){
		//Toast.makeText(getApplicationContext(), "login this", Toast.LENGTH_SHORT).show();
		//ed_email.getText();
		if(ed_email.getText().toString()=="" ||ed_password.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(),"Enter your email or your password", Toast.LENGTH_SHORT).show();
			
		}else{
			
			if(!isEmailValid(ed_email.getText().toString())){
				Toast.makeText(getApplicationContext(),"format email not valid", Toast.LENGTH_SHORT).show();
			}else{
				_request(ed_email.getText().toString(),ed_password.getText().toString());
			}
		}
	}
	//check fomat email
	private boolean isEmailValid(CharSequence email){
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}
	
	private void _request(String em,String pass){
		path=ConnectServer.ADDRESS_LOCALHOST+"/users/login";
		String ar_post[]={path,em,pass};
		new RequestHTTPPost(){
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				Log.i("result",""+result);
				if(result.equals("1")){
					setLogin(1);
					Toast.makeText(getApplicationContext(),"Complete", Toast.LENGTH_SHORT).show();
					
					
				}else{
					setLogin(0);
					Toast.makeText(getApplicationContext(),"Accout not valid", Toast.LENGTH_SHORT).show();
					
				}
			}
		}.execute(ar_post);
	
	}
	 
	
}
