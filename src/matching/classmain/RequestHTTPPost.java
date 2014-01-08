package matching.classmain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import matching.functioninterface.ConnectServer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import project.matching.LoginActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class RequestHTTPPost extends AsyncTask<String, Void, String>{

	/**
	 * @param args
	 */
	//String result="";
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		
	    super.onPostExecute(result);
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String path=params[0];
		String email=params[1];
		String password=params[2];
	    String response="";
		try{
	            	HttpClient client = new DefaultHttpClient();
	        		HttpPost post = new HttpPost(path);
	                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	                nameValuePairs.add(new BasicNameValuePair("email",email));
	                nameValuePairs.add(new BasicNameValuePair("pass",password));
	                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	                HttpResponse res=client.execute(post);
	        		BufferedReader reader = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), "UTF-8"));
	        		
	        		response = reader.readLine();
	        		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return response;
	}

}
