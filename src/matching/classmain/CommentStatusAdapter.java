package matching.classmain;

import java.util.ArrayList;

import com.example.matching.R;

import matching.functioninterface.ConnectServer;
import matching.loadimage.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentStatusAdapter extends ArrayAdapter<CommentStatus> {
	//
	private TextView tv_userName,tv_message,tv_commentTime;
	private ImageView img_cm_avarta;
	
	Activity context;
	ArrayList<CommentStatus> list_cmstatus;
	ImageLoader imageLoader;
	View row;
	public CommentStatusAdapter(Activity context, int resource, ArrayList<CommentStatus> objects) {
		
		super(context, resource, objects);
		this.context=context;
		this.list_cmstatus=objects;
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
		row=convertView;
		if(row == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.comment_status_adapter, parent, false);
        }
        CommentStatus item = list_cmstatus.get(position);
        String test = item.getUser_name();
        if(item != null)
        {
        	img_cm_avarta=(ImageView)row.findViewById(R.id.img_avarta_user);
        	String url=ConnectServer.ADDRESS_LOCALHOST+item.getAvarta();
        	imageLoader.DisplayImage(url,img_cm_avarta);
        	
        	tv_commentTime=(TextView)row.findViewById(R.id.tv_comment_time);
        	tv_commentTime.setText(""+item.getTime());
        	
        	tv_message=(TextView)row.findViewById(R.id.tv_comment);
        	tv_message.setText(""+item.getMessage());
        	
        	tv_userName=(TextView)row.findViewById(R.id.tv_name_user);
        	tv_userName.setText(""+item.getUser_name());
        	
        }
		return row;
	}

	

}
