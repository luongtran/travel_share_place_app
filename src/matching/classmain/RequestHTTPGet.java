package matching.classmain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
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

public class RequestHTTPGet extends AsyncTask<String, Void, String>{

	/**
	 * @param args
	 */
	//String result="";
	URI myUri;
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		
	    super.onPostExecute(result);
	}
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		//String path=ConnectServer.ADDRESS_LOCALHOST+"/cities/getallcity";
		String path=params[0];
		
		try {
			myUri = new URI(path.replace(" ","%20"));
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    String response="";
		try{
	            	HttpClient client = new DefaultHttpClient();
	        		HttpGet get = new HttpGet(myUri);
	                HttpResponse res=client.execute(get);
	        		BufferedReader reader = new BufferedReader(new InputStreamReader(res.getEntity().getContent(), "UTF-8"));
	        		
	        		response = reader.readLine();
	        		
		}catch(Exception e){
			e.printStackTrace();
		}
		//Log.i("sb","aaa"+response);
		
		
		return response;
	}

}
