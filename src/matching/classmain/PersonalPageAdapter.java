package matching.classmain;

import java.util.ArrayList;
import java.util.List;

import project.matching.CommentStatusActivity;
import project.matching.MainActivity;
import project.matching.MenuFragment;

import matching.functioninterface.ConnectServer;
import matching.loadimage.ImageLoader;

import com.example.matching.R;
import com.google.android.gms.internal.cn;

import android.Manifest.permission;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalPageAdapter extends ArrayAdapter<PersonalPage> implements OnClickListener{
	ImageView img_avarta;
	TextView tv_user_name,tv_time,tv_status,tv_numlike,tv_numcomment;
	ImageButton btn_like,btn_edit,btn_comment,btn_status,btn_delete;
	Activity context;
	private int position_count;
	View row ;
	ImageLoader imageLoader;
	ArrayList<PersonalPage> list_activity=null;
	int user_id;
	int status_id;
	int num_like;
	int num_comment;
	View view;
	public PersonalPageAdapter(Activity context, int resource, ArrayList<PersonalPage> objects) {
		
		super(context, resource, objects);
		this.context=context;
		this.list_activity=objects;
		//Log.i("size_status",""+objects.size());
		
		imageLoader=new ImageLoader(context.getApplicationContext());
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return initView(position, convertView, parent);
	}
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return initView(position, convertView, parent);
	}
	private View initView(int position, View convertView, ViewGroup parent) {
        row = convertView;
        if(row == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.personal_page_adapter, parent, false);
        }
        PersonalPage item = list_activity.get(position);
        String test = item.getUser_name();
       // Log.d("test ", test);

        if(item != null)
        {
        	//id_status=item.getStatus_id();
        	//position_count=position;
        	
        	img_avarta=(ImageView)row.findViewById(R.id.img_avarta_user);
        	String url=ConnectServer.ADDRESS_LOCALHOST+item.getAvarta();
        	imageLoader.DisplayImage(url, img_avarta);
        	
        	
        	tv_status=(TextView)row.findViewById(R.id.tv_status_user);
        	tv_time=(TextView)row.findViewById(R.id.tv_time_activity);
        	tv_user_name=(TextView)row.findViewById(R.id.tv_name_user);
        	
        	btn_comment=(ImageButton)row.findViewById(R.id.btn_comment_status);
        	btn_comment.setOnClickListener(this);
        	btn_comment.setTag(position);
        	
        	btn_edit=(ImageButton)row.findViewById(R.id.btn_edit_status);
        	btn_edit.setOnClickListener(this);
        	btn_edit.setTag(position);
        	
        	btn_like=(ImageButton)row.findViewById(R.id.btn_like_status);
        	btn_like.setOnClickListener(this);
        	btn_like.setTag(position);
        	
        	btn_delete=(ImageButton)row.findViewById(R.id.btn_delete);
        	btn_delete.setOnClickListener(this);
        	btn_delete.setTag(position);
        	
        	tv_numlike=(TextView)row.findViewById(R.id.tv_numlike_status);
        	tv_numlike.setText(""+item.getNum_like());
        	tv_numlike.setTag(position);
        	
        	tv_numcomment=(TextView)row.findViewById(R.id.tv_numcomment_status);
        	tv_numcomment.setText(""+item.getNum_comment());
        	
        	tv_status.setText(""+item.getContent());
        	tv_time.setText(""+item.getTime());
        	tv_user_name.setText(""+item.getUser_name());
        	
        }
        return row;
    }
	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.i("click image","edit status"+list_activity.get(Integer.parseInt(""+v.getTag())).getStatus_id());
		Log.i("click image","edit status"+v.getId());
		user_id=MainActivity.id_user;
		status_id=list_activity.get(Integer.parseInt(""+v.getTag())).getStatus_id();
		num_like=list_activity.get(Integer.parseInt(""+v.getTag())).getNum_like();
		num_comment=list_activity.get(Integer.parseInt(""+v.getTag())).getNum_comment();
		view=v;
		
		if(v.getId()==R.id.btn_comment_status){
			Log.i("comment","ok");
			Intent intent=new Intent(getContext(), CommentStatusActivity.class);
			intent.putExtra("status_id", ""+status_id);
			intent.putExtra("url_avarta",""+list_activity.get(Integer.parseInt(""+v.getTag())).getAvarta());
			intent.putExtra("content",""+list_activity.get(Integer.parseInt(""+v.getTag())).getContent());
			intent.putExtra("user_name",""+list_activity.get(Integer.parseInt(""+v.getTag())).getUser_name());
			intent.putExtra("time",""+list_activity.get(Integer.parseInt(""+v.getTag())).getTime());
			intent.putExtra("numlike",""+list_activity.get(Integer.parseInt(""+v.getTag())).getNum_like());
			getContext().startActivity(intent);
			
		}
		if(v.getId()==R.id.btn_delete){
			Log.i("delete","ok"+list_activity.get(Integer.parseInt(""+v.getTag())).getUser_id());
			//list_activity.remove(Integer.parseInt(""+v.getTag()));
			//notifyDataSetChanged();
			if(list_activity.get(Integer.parseInt(""+v.getTag())).getUser_id()==MainActivity.id_user){
				getDialogDeleteStatus("Do you want to delete?").show();
			}else{
				Toast.makeText(getContext(), "You are not allowed to delete", Toast.LENGTH_SHORT).show();
			}
		}
		
		if(v.getId()==R.id.btn_like_status){
			//Log.i("btn like","ok");
			_requestLikeStatus();
			
		}
		
		if(v.getId()==R.id.btn_edit_status){
			Log.i("btn edit status","ok");
		}
		
	}
	private void _requestLikeStatus(){
		String path=ConnectServer.ADDRESS_LOCALHOST+"/likestatuses/addLikeStatus?user_id="+user_id+"&status_id="+status_id;
		new RequestHTTPGet(){
			protected void onPostExecute(String result) {
				if(result.equals("1")){
					if(num_like!=0){
						list_activity.get(Integer.parseInt(""+view.getTag())).setNum_like(num_like-1);
						notifyDataSetChanged();
					}
				}
				if(result.equals("2")){
					list_activity.get(Integer.parseInt(""+view.getTag())).setNum_like(num_like+1);
					notifyDataSetChanged();
				}
				
			};
		}.execute(path);
		
	}
	private AlertDialog getDialogDeleteStatus(String message){
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setMessage(""+message)
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   String path=ConnectServer.ADDRESS_LOCALHOST+"/statuses/deleteStatus?status_id="+status_id;
			   			new RequestHTTPGet(){
			   				protected void onPostExecute(String result) {
			   					if(result.equals("1")){
			   						list_activity.remove(Integer.parseInt(""+view.getTag()));
			   						notifyDataSetChanged();
			   					}
			   				};
			   			}.execute(path);
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		return alert;
	}
	
	
}
