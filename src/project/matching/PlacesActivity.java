package project.matching;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import matching.classmain.City;
import matching.classmain.District_Adapter;
import matching.classmain.GPSTracker;
import matching.classmain.Place_Adapter;
import matching.classmain.Places;
import matching.classmain.RequestHTTPGet;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class PlacesActivity extends Activity implements ConnectServer,OnItemSelectedListener{
	private ListView lv_place;
	private String path="";
	private Spinner spn_distance;
	private GPSTracker gps;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_places);
		Toast.makeText(this, "places",Toast.LENGTH_SHORT).show();
		spn_distance=(Spinner)findViewById(R.id.spn_distance);
		ArrayAdapter<CharSequence> sn_adapter=ArrayAdapter.createFromResource(this,R.array.select_distance,android.R.layout.simple_spinner_item);
		sn_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn_distance.setAdapter(sn_adapter);
		spn_distance.setOnItemSelectedListener(this);
		gps=new GPSTracker(this);
		
		//_requestGetPlace();
	}
	
	public void init(){
		lv_place=(ListView)findViewById(R.id.lv_place);
	}
	
	// function request get data to webservice
	private void _requestGetPlace(double lat,double lng,int distance){
		path=ConnectServer.ADDRESS_LOCALHOST+"/places/getPlaceByDistance?lat="+lat+"&lng= "+lng+"&distance="+distance;
		new RequestHTTPGet(){
			protected void onPostExecute(String result) {
				init();
				
				loadListPlace(getListPlaces(result));
				Log.i("réu",""+result);
			};
		}.execute(path);
	}
	
	// add data from webservice into app
	private void loadListPlace(ArrayList<Places> list_place){
		Place_Adapter place_Adapter=new Place_Adapter(this, android.R.layout.simple_list_item_1,list_place);
		lv_place.setAdapter(place_Adapter);
	}
	
	// function return arraylist<places> get list place 
	private ArrayList<Places> getListPlaces(String result){
		ArrayList<Places> list_place=new ArrayList<Places>();
		Places places;
		try{
			JSONArray jsonArray=new JSONArray(result);
    	    for(int i=0;i<jsonArray.length();i++){
    	    	JSONObject jsonObject=jsonArray.getJSONObject(i);
    	    	//Log.i("id: ",""+jsonObject.getString("id"));
    	    	//Log.i("avarta: ",""+jsonObject.getString("avarta"));
    	    	
    	    	places=new Places(Integer.parseInt(jsonObject.getString("id")),jsonObject.getString("place_name"),jsonObject.getString("address"),jsonObject.getString("avarta"));
    	    	list_place.add(places);
    	    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return list_place;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.matching, menu);
		return true;
	}
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View viewParent, int position,
			long arg3) {
		// TODO Auto-generated method stub
		//Log.i("hoho","click item"+position);
		if(gps.canGetLocation()){
			int distance=100;
			switch (position) {
				case 0:
					distance=100;
					break;
				case 1:
					distance=400;
					break;	
				case 2:
					distance=800;
					break;
				default:
					break;
			}
			Log.i("lat",""+gps.getLatitude());
			Log.i("lng",""+gps.getLongitude());
			double lat=gps.getLatitude();
			double lng=gps.getLongitude();
			_requestGetPlace(lat,lng,distance);
	}else
		Log.i("no","no");
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
