package matching.detailplace;

import matching.functioninterface.ConnectServer;
import matching.loadimage.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import project.matching.DetailPlaceActivity.AppSectionsPagerAdapter;

import com.example.matching.R;

import android.R.integer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailPlaceFragment extends Fragment{
	String json;
	int id_place;
	ImageView img_place;
	TextView tv_address;
	TextView tv_mobile;
	ImageButton btn_favorite;
	ImageButton btn_like;
	TextView tv_name;
	
	public void transfer_data(String json,String id_place) {
		// TODO Auto-generated constructor stub
		this.json=json;
		this.id_place=Integer.parseInt(id_place);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	//int i=getArguments().getInt(ARG_SECTION_NUMBER);
    	//Log.i("i2=",""+json+id_place);
    	//Log.i("jjjjjson",""+json);
        View rootView = inflater.inflate(R.layout.fragment_detail_place, container, false);
        img_place=((ImageView)rootView.findViewById(R.id.detail_place_avarta));
        tv_name=((TextView)rootView.findViewById(R.id.detail_place_name_id));
        tv_address=((TextView)rootView.findViewById(R.id.detail_place_address));
        tv_mobile=((TextView)rootView.findViewById(R.id.detail_place_mobile));
        btn_favorite=((ImageButton)rootView.findViewById(R.id.btn_favorite_place));
        btn_like=((ImageButton)rootView.findViewById(R.id.btn_like_place));
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	if(savedInstanceState!=null){
    		Log.i("abab","hoho"+savedInstanceState.getString("json"));
    		json=savedInstanceState.getString("json");
    		id_place=savedInstanceState.getInt("id_place");
    		
    	}
    	
        //tv_name.setText("abc");
        //set view
        try {
			JSONArray jsonArray=new JSONArray(json);
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				int id_place_json=Integer.parseInt(jsonObject.getString("id"));
				if(id_place_json==id_place){
					Log.i("id_place_json",""+jsonObject.getString("place_name"));
					tv_name.setText(jsonObject.getString("place_name"));
					tv_mobile.setText(jsonObject.getString("mobile"));
					tv_address.setText(jsonObject.getString("address"));
					String url_img=ConnectServer.ADDRESS_LOCALHOST+""+jsonObject.getString("avarta");
					ImageLoader imageLoader=new ImageLoader(getActivity().getApplicationContext());
					imageLoader.DisplayImage(url_img,img_place);
					break;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
    	// TODO Auto-generated method stub
    	super.onSaveInstanceState(outState);
    	outState.putString("json",json);
    	outState.putInt("id_place",id_place);
    }
    
}
