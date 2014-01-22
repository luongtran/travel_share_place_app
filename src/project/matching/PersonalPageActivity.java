package project.matching;

import java.util.ArrayList;

import matching.classmain.PersonalPage;
import matching.classmain.PersonalPageAdapter;
import matching.classmain.RequestHTTPGet;
import matching.functioninterface.ConnectServer;
import matching.loadimage.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.matching.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalPageActivity extends Activity implements OnItemClickListener,OnClickListener{
	TextView tv_user_name;
	ListView lv_status;
	ImageView img_cover,img_avarta;
	int numLoadListStatus=3,user_id;
	MenuFragment menuFragment;
	ImageButton btn_postStatus,btn_add_friend,btn_listFriend;
	EditText ed_contentStatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_page);
		init();
	}
	private void init(){
		lv_status=(ListView)findViewById(R.id.lv_status);
		Bundle bundle=getIntent().getExtras();
		user_id=Integer.parseInt(bundle.getString("user_id"));
		btn_add_friend=(ImageButton)findViewById(R.id.btn_add_friend);
		btn_add_friend.setOnClickListener(this);
		
		btn_listFriend=(ImageButton)findViewById(R.id.btn_list_friend);
		btn_listFriend.setOnClickListener(this);
		Log.i("user_id",""+user_id);
		_requestListStatus(user_id,numLoadListStatus);
		_requestCheckFriend(user_id);
		
	}
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		user_id=savedInstanceState.getInt("user_id");
		numLoadListStatus=savedInstanceState.getInt("num_limit");
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt("user_id",user_id);
		outState.putInt("num_limit",numLoadListStatus);
	}
	
	//request check friend
	private void _requestCheckFriend(int user_id){
		String path=ConnectServer.ADDRESS_LOCALHOST+"/friends/getCheckFriendById?user_id="+MainActivity.id_user+"&friend_id="+user_id;
		new RequestHTTPGet(){
			protected void onPostExecute(String result) {
				if(result.equals("1")){
					btn_add_friend.setVisibility(View.VISIBLE);
					btn_add_friend.setBackgroundResource(R.drawable.ic_sub_friend);
				}else{
					if(result.equals("2")){
						btn_add_friend.setVisibility(View.INVISIBLE);
					}else
					{
						btn_add_friend.setVisibility(View.VISIBLE);
						btn_add_friend.setBackgroundResource(R.drawable.ic_add_friend);
					}
				}
			};
		}.execute(path);
	}
	//request get friend of user
		public void _requestListStatus(int user_id,int numlimit){
			String path=ConnectServer.ADDRESS_LOCALHOST+"/statuses/getStatusPersonalPage?user_id="+user_id+"&limit="+numlimit;
			new RequestHTTPGet(){
				protected void onPostExecute(String result) {
					
					loadListStatus(getListStatus(result),lv_status,numLoadListStatus);//1 exitst comment
					
				};
			}.execute(path);
		}
		 private void loadListStatus(ArrayList<PersonalPage> list_status,ListView lv_status,int numLimit){
			 	
			 	//Log.i("url_img cover",""+list_status.size());
			 	if(list_status.get(0).getContent()!=null){
					PersonalPageAdapter pageAdapter=new PersonalPageAdapter(this, android.R.layout.simple_list_item_1, list_status);
					//set height list view status
					lv_status.setAdapter(pageAdapter);
					//lv_status.setOnItemClickListener(this);
					
			 	}
			 	if(list_status.size()>0){
			 		
					ImageLoader imageLoader=new ImageLoader(getApplicationContext());
					imageLoader.clearCache();
					// show,edit image cover
					img_cover=(ImageView)findViewById(R.id.img_cover);
					String url=ConnectServer.ADDRESS_LOCALHOST+list_status.get(0).getImage_cover();
					Log.i("url_img cover",""+url);
					imageLoader.DisplayImage(url, img_cover);
					//img_cover.setOnClickListener(this);
					
					// show,edit image avarta
					img_avarta=(ImageView)findViewById(R.id.img_avarta);
					url=ConnectServer.ADDRESS_LOCALHOST+list_status.get(0).getAvarta();
					Log.i("url_img avarta",""+url);
					imageLoader.DisplayImage(url, img_avarta);
					//img_avarta.setOnClickListener(this);
					
					//button post status
					btn_postStatus=(ImageButton)findViewById(R.id.btn_post_status);
					btn_postStatus.setOnClickListener(this);
					
					//edittext content status
					ed_contentStatus=(EditText)findViewById(R.id.ed_content_status);
					
					TextView tv_userName=(TextView)findViewById(R.id.tv_user_name_main);
					tv_userName.setText(""+list_status.get(0).getUser_name());
					//((ListView) rootView.findViewById(R.id.id_list_favorite)).setOnItemClickListener(new ListViewItemPlaceClickListener());
			 	}
			}
		
		// function return arraylist<places> get list place 
		private ArrayList<PersonalPage> getListStatus(String result){
			ArrayList<PersonalPage> list_status=new ArrayList<PersonalPage>();
			PersonalPage page;
			try{
				JSONArray jsonArray=new JSONArray(result);
	   	    for(int i=0;i<jsonArray.length();i++){
	   	    	JSONObject jsonObject=jsonArray.getJSONObject(i);
	   	    	//Log.i("id_réult",""+jsonObject.length());
	   	    	
	   	    	if(jsonObject.length()!=4){ //num row request
	   	    		
	   	    		page=new PersonalPage(Integer.parseInt(jsonObject.getString("id")), Integer.parseInt(jsonObject.getString("user_id"))
	   	    			,jsonObject.getString("user_name"), jsonObject.getString("status_time"), jsonObject.getString("avarta"), jsonObject.getString("content"),jsonObject.getString("image_cover"),
	   	    			Integer.parseInt(jsonObject.getString("num_like")),Integer.parseInt(jsonObject.getString("num_comment")));
	   	    	}else
	   	    	{
	   	    		//Log.i("da","xuong"+check);
	   	    		page=new PersonalPage(Integer.parseInt(jsonObject.getString("user_id")),jsonObject.getString("user_name"), jsonObject.getString("avarta"), jsonObject.getString("image_cover"));
	   	    	}
	   	    	list_status.add(page);
	   	    }
			}catch(Exception e){
				e.printStackTrace();
			}
			return list_status;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId()==R.id.btn_list_friend){
				//Log.i("list", "friend");
				Intent intent=new Intent(getApplicationContext(), ListFriendActivity.class);
				intent.putExtra("user_id",""+user_id);
				startActivity(intent);
			}
			if(v.getId()==R.id.btn_add_friend){
				String path=ConnectServer.ADDRESS_LOCALHOST+"/friends/addFriend?user_id="+MainActivity.id_user+"&friend_id="+user_id;
				new RequestHTTPGet(){
					protected void onPostExecute(String result) {
						if(result.equals("1")){ //add friend
							btn_add_friend.setBackgroundResource(R.drawable.ic_sub_friend);
						}else{
							if(result.equals("2")){
								btn_add_friend.setBackgroundResource(R.drawable.ic_add_friend);
							}else{
								Toast.makeText(getApplicationContext(),"sorry", Toast.LENGTH_SHORT).show();
							}
						}
					};
				}.execute(path);
			}
		}
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
		}
}
