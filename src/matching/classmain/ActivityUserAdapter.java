package matching.classmain;

import java.util.ArrayList;
import java.util.List;

import matching.functioninterface.ConnectServer;
import matching.loadimage.ImageLoader;

import com.example.matching.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityUserAdapter extends ArrayAdapter<ActivityUser> implements OnClickListener{

	Activity context; 
	ArrayList<ActivityUser> list_activity;
	
	TextView tv_userName,tv_time,tv_content,tv_numLike,tv_numComment;
	ImageView img_avarta;
	ImageButton btn_comment,btn_like;
	ImageLoader imageLoader;
	public ActivityUserAdapter(Activity context, int textViewResourceId,
			ArrayList<ActivityUser> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list_activity=objects;
		imageLoader=new ImageLoader(getContext());
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
        View row = convertView;
        if(row == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.activity_user_adapter, parent, false);
        }
        ActivityUser item = list_activity.get(position);
        //String test = item.getPlace_name();
       // Log.d("test ", test);
        if(item != null)
        {   
        	//init
            tv_content=(TextView)row.findViewById(R.id.tv_content_activity);
            tv_numComment=(TextView)row.findViewById(R.id.tv_numcomment_activity);
            tv_numLike=(TextView)row.findViewById(R.id.tv_numlike_activity);
            tv_userName=(TextView)row.findViewById(R.id.tv_name_user_activity);
            tv_time=(TextView)row.findViewById(R.id.tv_time_activity);
            
            img_avarta=(ImageView)row.findViewById(R.id.img_avarta_user_activity);
            btn_comment=(ImageButton)row.findViewById(R.id.btn_comment_activity);
            btn_comment.setOnClickListener(this);
            btn_like=(ImageButton)row.findViewById(R.id.btn_like_activity);
            btn_like.setOnClickListener(this);
            
            //set view
            tv_content.setText(""+item.getContent());
            tv_userName.setText(""+item.getUser_name());
            tv_time.setText(""+item.getActivity_time());
            String url=ConnectServer.ADDRESS_LOCALHOST+item.getAvarta();
            imageLoader.DisplayImage(url, img_avarta);
            
        }

        return row;
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.btn_comment_activity){
			Log.i("comment","activity");
		}
		if(v.getId()==R.id.btn_like_activity){
			Log.i("like","activity");
		}
	}

	
}
