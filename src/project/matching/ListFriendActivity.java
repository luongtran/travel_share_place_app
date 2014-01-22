package project.matching;

import java.util.ArrayList;

import matching.classmain.RequestHTTPGet;
import matching.classmain.User;
import matching.classmain.User_Adapter;
import matching.functioninterface.ConnectServer;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.matching.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListFriendActivity extends Activity implements OnItemClickListener{
	ListView lv_friend;
	int id_friend[];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_friend);
		init();
	}
	
	private void init(){
		Bundle bundle=getIntent().getExtras();
		Log.i("user_id", ""+bundle.getString("user_id"));
		lv_friend=(ListView)findViewById(R.id.id_list_friend);
		lv_friend.setOnItemClickListener(this);
		_requestListFriend(Integer.parseInt(bundle.getString("user_id")));
	}
	private void _requestListFriend(int user_id){
		String path=ConnectServer.ADDRESS_LOCALHOST+"/friends/getFriends?user_id="+user_id;
		new RequestHTTPGet(){
			protected void onPostExecute(String result) {
				//Log.i("friend",""+result);
				loadListFriend(getListFriend(result));
			};
		}.execute(path);
		
	}
	
	 private void loadListFriend(ArrayList<User> list_user){
		 	id_friend=new int[list_user.size()];
			for(int i=0;i<list_user.size();i++){
				//Log.i("thu i",""+list_place.get(i).getId());
				id_friend[i]=list_user.get(i).getId();
			}
			
			User_Adapter user_Adapter=new User_Adapter(this, android.R.layout.simple_list_item_1, list_user);
			//set adapter for listview
			lv_friend.setAdapter(user_Adapter);
			
			//lv_friend.setOnItemClickListener(this);
			
		}
	
	// function return arraylist<places> get list place 
	private ArrayList<User> getListFriend(String result){
		ArrayList<User> list_friend=new ArrayList<User>();
		User user;
		try{
			JSONArray jsonArray=new JSONArray(result);
   	    for(int i=0;i<jsonArray.length();i++){
   	    	JSONObject jsonObject=jsonArray.getJSONObject(i);
   	    	//Log.i("id_réult",""+jsonObject.getString("user_name"));
   	    	
   	    	user=new User(Integer.parseInt(jsonObject.getString("id")), jsonObject.getString("user_name"), jsonObject.getString("email"), jsonObject.getString("avarta"), jsonObject.getString("address"));
   	    	list_friend.add(user);
   	    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return list_friend;
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
		// TODO Auto-generated method stub
		if(adapterView.getId()==R.id.id_list_friend){
			Log.i("click item friend", "ok"+id_friend[position]);
			Intent intent=new Intent(this, PersonalPageActivity.class);
			intent.putExtra("user_id",""+id_friend[position]);
			startActivity(intent);
		}
	}

}
