package project.matching;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import matching.classmain.CommentStatus;
import matching.classmain.CommentStatusAdapter;
import matching.classmain.PersonalPage;
import matching.classmain.RequestHTTPGet;
import matching.functioninterface.ConnectServer;
import matching.loadimage.ImageLoader;

import com.example.matching.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CommentStatusActivity extends Activity{
	private int status_id,numlike;
	private String content,url_avarta,user_name,time;
	TextView tv_userName,tv_content,tv_time,tv_numlike;
	private EditText ed_cmstatus_content;
	ImageView img_avarta;
	private ImageButton btn_cmstatus_post;
	private ListView lv_cmstatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_status);
		Bundle bundle=getIntent().getExtras();
		status_id=Integer.parseInt(bundle.getString("status_id"));
		//Toast.makeText(this, "status_id="+status_id, Toast.LENGTH_SHORT).show();
		Log.i("avarta",""+bundle.getString("url_avarta"));
		Log.i("content",""+bundle.getString("content"));
		Log.i("user_name",""+bundle.getString("user_name"));
		
		init();
		//init
	}
	private void init(){
		img_avarta=(ImageView)findViewById(R.id.img_cmstatus_avarta);
		tv_time=(TextView)findViewById(R.id.tv_cmstatus_time);
		tv_userName=(TextView)findViewById(R.id.tv_cmstatus_name);
		tv_content=(TextView)findViewById(R.id.tv_cmstatus_content);
		tv_numlike=(TextView)findViewById(R.id.tv_cmstatus_numlike);
		btn_cmstatus_post=(ImageButton)findViewById(R.id.btn_cmstatus_post);
		ImageLoader imageLoader=new ImageLoader(getApplicationContext());
		ed_cmstatus_content=(EditText)findViewById(R.id.ed_cmstatus_content);
		
		Bundle bundle=getIntent().getExtras();
		String url=ConnectServer.ADDRESS_LOCALHOST+bundle.getString("url_avarta");
		//set image avarta
		imageLoader.DisplayImage(url,img_avarta);
		
		tv_content.setText(""+bundle.getString("content"));
		tv_time.setText(""+bundle.getString("time"));
		tv_userName.setText(""+bundle.getString("user_name"));
		tv_numlike.setText(""+bundle.getString("numlike"));
		_requestListComment();
	}
	private void _requestStatus(){
		
	}
	private void _requestListComment(){
		String path=ConnectServer.ADDRESS_LOCALHOST+"/statuscomments/getCommentById?status_id="+status_id;
		new RequestHTTPGet(){
			protected void onPostExecute(String result) {
				loadListCommentStatus(getListCommetStatus(result));
			};
		}.execute(path);
	}
	private void loadListCommentStatus(ArrayList<CommentStatus> list_cmstatus){
		CommentStatusAdapter adapter=new CommentStatusAdapter(this, android.R.layout.simple_list_item_1, list_cmstatus);
		lv_cmstatus=(ListView)findViewById(R.id.lv_cmstatus);
		lv_cmstatus.setAdapter(adapter);
	}
	
	private ArrayList<CommentStatus> getListCommetStatus(String result){
		ArrayList<CommentStatus> list_cmstatus=new ArrayList<CommentStatus>();
		CommentStatus cmstatus;
		try{
			JSONArray jsonArray=new JSONArray(result);
   	    for(int i=0;i<jsonArray.length();i++){
   	    	JSONObject jsonObject=jsonArray.getJSONObject(i);
   	    	//Log.i("id_réult",""+jsonObject.length());
   	    	
   	    	cmstatus=new CommentStatus(Integer.parseInt(jsonObject.getString("id")),Integer.parseInt(jsonObject.getString("status_id")),Integer.parseInt(jsonObject.getString("user_id")), 
   	    			jsonObject.getString("avarta"), jsonObject.getString("user_name"), jsonObject.getString("message"), jsonObject.getString("comment_time"));
   	    	
   	    	list_cmstatus.add(cmstatus);
   	    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return list_cmstatus;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("status_id", ""+status_id);
		outState.putString("content", ""+content);
		outState.putString("url_avarta", ""+url_avarta);
		outState.putString("user_name", ""+user_name);
		outState.putString("time", ""+time);
		outState.putString("numlike", ""+numlike);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		status_id=Integer.parseInt(savedInstanceState.getString("status_id"));
		content=savedInstanceState.getString("content");
		url_avarta=savedInstanceState.getString("url_avarta");
		user_name=savedInstanceState.getString("user_name");
		time=savedInstanceState.getString("time");
		numlike=Integer.parseInt(savedInstanceState.getString("numlike"));
	}
	
	public void on_post_comment(View v){
		Log.i("onclick", "post");
		String ed_message=ed_cmstatus_content.getText().toString();
		if(!ed_message.matches("")||ed_message.length()>5){
			String path=ConnectServer.ADDRESS_LOCALHOST+"/statuscomments/addCommentStatus?user_id="+MainActivity.id_user+"&status_id="+status_id+"&message="+ed_cmstatus_content.getText();
			new RequestHTTPGet(){
				protected void onPostExecute(String result) {
					if(result.equals("1")){
						_requestListComment();
						ed_cmstatus_content.setText("");
					}
				};
			}.execute(path);
		}else{
			Toast.makeText(getApplicationContext(), "Enter your content,please!", Toast.LENGTH_SHORT).show();
		}
	}
}
