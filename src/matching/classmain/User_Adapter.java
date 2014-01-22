package matching.classmain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.json.JSONObject;

import project.matching.MainActivity;
import project.matching.PersonalPageActivity;

import matching.functioninterface.ConnectServer;
import matching.loadimage.ImageLoader;

import com.example.matching.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class User_Adapter extends ArrayAdapter<User> implements OnClickListener{
	
	private TextView tv_status,tv_name;
	private ImageView img_avarta;
	private ImageButton btn_detail_friend;
	private ImageLoader imageLoader;
	private User item;
	private String path;
	private Activity context;
	private ArrayList<User> list_user=null;
	public User_Adapter(Activity context, int resource,
			ArrayList<User> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list_user=objects;
		imageLoader=new ImageLoader(getContext());
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return initView(position,convertView,parent);
		
	}
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return initView(position,convertView,parent);
	}
	private View initView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.friend_adapter, parent, false);
        }
        User item = list_user.get(position);
        String test = item.getUser_name();
       // Log.d("test ", test);

        if(item != null)
        {   
        	
        	btn_detail_friend=(ImageButton)row.findViewById(R.id.btn_detail_friend);
        	
        	btn_detail_friend.setOnClickListener(this);
        	btn_detail_friend.setTag(position);
        	tv_status=(TextView) row.findViewById(R.id.tv_status_user);
        	//tv_status.setText("rau qua");
        	tv_status.setText(""+item.getAddress());
            tv_name=(TextView)row.findViewById(R.id.tv_name_user);
            img_avarta=(ImageView)row.findViewById(R.id.img_avarta_user);
            String url=ConnectServer.ADDRESS_LOCALHOST+""+item.getAvarta();
            imageLoader.DisplayImage(url, img_avarta);
            tv_name.setText(""+item.getUser_name());
            
        }
        return row;
    }
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.i("v tag",""+v.getTag());
		int user_id=list_user.get(Integer.parseInt(""+v.getTag())).getId();
		
		if(v.getId()==R.id.btn_detail_friend){
			Log.i("detail","friend"+user_id);
			Intent intent=new Intent(getContext(), PersonalPageActivity.class);
			intent.putExtra("user_id",""+user_id);
			getContext().startActivity(intent);
		}
	}
	
	
}
