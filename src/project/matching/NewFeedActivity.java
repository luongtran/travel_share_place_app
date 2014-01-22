package project.matching;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import matching.classmain.ActivityUser;
import matching.classmain.ActivityUserAdapter;
import matching.classmain.PersonalPage;
import matching.classmain.PersonalPageAdapter;
import matching.classmain.RequestHTTPGet;
import matching.functioninterface.ConnectServer;
import matching.gallery.GalleryImageAdapter;
import matching.loadimage.ImageLoader;

import com.example.matching.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class NewFeedActivity extends Activity{
	ListView lv_activity;
	Timer timer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_new_feed);
		timer=new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//Log.i("delay","abc");
				init();
			}
		},0,50000); //5 minutes update new feed
		
		//init();
	}
	private void init() {
		lv_activity=(ListView)findViewById(R.id.lv_new_feed);
		_requestActivity(MainActivity.id_user);
		
	}
	
	private void _requestActivity(int id_user) {
		String path=ConnectServer.ADDRESS_LOCALHOST+"/activities/getAllActivity?user_id="+id_user;
		new RequestHTTPGet(){
			protected void onPostExecute(String result) {
				//change num like and num comment into request 
				Log.i("result_activity",""+result);
				String path=ConnectServer.ADDRESS_LOCALHOST+"/activities/getAllActivity?user_id="+MainActivity.id_user;
				new RequestHTTPGet(){
					protected void onPostExecute(String result) {
						
						loadListStatus(getListStatus(result),lv_activity);
					};
				}.execute(path);
			};
		}.execute(path);
	}
	 private void loadListStatus(ArrayList<ActivityUser> list_activity,ListView lv_activity){
		 	
		 	Log.i("url_img cover",""+list_activity.size());
		 	if(list_activity.get(0).getContent()!=null){
				ActivityUserAdapter activityAdapter=new ActivityUserAdapter(this, android.R.layout.simple_list_item_1, list_activity);
				//set height list view status
				lv_activity.setAdapter(activityAdapter);
				//lv_status.setOnItemClickListener(this);
				
		 	}
		}
	
	// function return arraylist<places> get list place 
	private ArrayList<ActivityUser> getListStatus(String result){
		ArrayList<ActivityUser> list_activity=new ArrayList<ActivityUser>();
		ActivityUser activityUser;
		try{
			JSONArray jsonArray=new JSONArray(result);
	    for(int i=0;i<jsonArray.length();i++){
	    	JSONObject jsonObject=jsonArray.getJSONObject(i);
	    	//Log.i("id_réult",""+jsonObject.length());
	    		
	    		activityUser=new ActivityUser(Integer.parseInt(jsonObject.getString("id")), Integer.parseInt(jsonObject.getString("user_id"))
	    				, Integer.parseInt(jsonObject.getString("category_id")), Integer.parseInt(jsonObject.getString("activity_id")), 
	    				jsonObject.getString("cate_name"), jsonObject.getString("event_name"), jsonObject.getString("activity_time"), jsonObject.getString("content")
	    				, jsonObject.getString("user_name"), jsonObject.getString("avarta"),2,3); //change numlike 2 and numcomment 3
	    	
	    		list_activity.add(activityUser);
	    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return list_activity;
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		Log.i("exit activity","ok");
		timer.cancel();
		super.onPause();
	}
}
