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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentPlace_Addapter extends ArrayAdapter<CommentsPlace>{
	private Activity context;
	private TextView tv_email,tv_content;
	private ImageView img_avarta;
	private CommentsPlace commentsPlace;
	private ArrayList<CommentsPlace> list_comment=null;
	private String email="";
	View row ;
	int id_user;
	ImageLoader imageLoader=new ImageLoader(getContext());
	String message;
	public CommentPlace_Addapter(Activity context, int resource,ArrayList<CommentsPlace> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list_comment=objects;
		
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//return super.getView(position, convertView, parent);
		return initView(position, convertView, parent);
	}
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//return super.getDropDownView(position, convertView, parent);
		return initView(position, convertView, parent);
	}
	private View initView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.comment_places_adapter, parent, false);
        }
        CommentsPlace item = list_comment.get(position);
        //String test = item.getPlace_name();
       // Log.d("test ", test);
        if(item != null)
        {
        	id_user=item.getUser_id();
        	message=item.getMessage();
        	tv_email=(TextView)row.findViewById(R.id.tv_email_comment);
        	tv_content=(TextView)row.findViewById(R.id.tv_content_comment);
        	img_avarta=(ImageView)row.findViewById(R.id.img_avarta_comment);
        	String path=ConnectServer.ADDRESS_LOCALHOST+item.getAvarta();
        	tv_email.setText(""+item.getEmail());
        	tv_content.setText(message);
        	
        	imageLoader.DisplayImage(path, img_avarta);
        	//requestEmail();
        	
        }
        
        return row;
    }
	private void requestEmail(){
		String path=ConnectServer.ADDRESS_LOCALHOST+"/users/getEmailByIdUser?user_id="+id_user;
		new RequestHTTPGet(){
			protected void onPostExecute(String result) {
				//email=result;
				//Log.i("result_email",""+result);
				tv_email.setText(""+result);
	        	tv_content.setText(message);
			};
		}.execute(path);
		
	}
	
}
