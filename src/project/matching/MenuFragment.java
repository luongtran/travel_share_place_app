package project.matching;

import java.util.ArrayList;

import matching.classmain.FetchImageTask;
import matching.classmain.Place_Adapter;
import matching.classmain.Places;
import matching.classmain.RequestHTTPGet;
import matching.functioninterface.ConnectServer;
import matching.loadimage.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.MailTo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matching.R;

public class MenuFragment extends Fragment implements OnClickListener{
    public static final String ARG_MENU_NUMBER = "menu_number";
    PlacesActivity placesActivity;
    MainActivity mainActivity;
    View rootView;
    Activity activity;
    String profile;
    int id_place[];
    private JSONObject json_profile;
    
   // ListView lv_place;
    //TextView
    
    public MenuFragment() {
        // Empty constructor required for fragment subclasses
    	
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        int i = getArguments().getInt(ARG_MENU_NUMBER);
        String titleMenu = MainActivity.mMenuTitles[i];
        rootView = inflater.inflate(R.layout.activity_places, container, false);
        int id_user=MainActivity.id_user;
        switch (i) {
			case 0:
				rootView = inflater.inflate(R.layout.activity_places, container, false);
				getActivityPlace();
				break;
			case 1:
				if(id_user==0)
					rootView = inflater.inflate(R.layout.activity_login, container, false);
				else{
					rootView = inflater.inflate(R.layout.activity_places, container, false);
					getActivityPlace();
					Toast.makeText(getActivity(), "Logged", Toast.LENGTH_SHORT).show();
				}
				break;
				
			case 2:
				//Log.i("login",""+MainActivity.email);
				
				if(id_user==0){
					rootView=inflater.inflate(R.layout.activity_login, container, false);
				}
				else{
					rootView = inflater.inflate(R.layout.activity_profile, container, false);// output profile
					requestGetProfile();
					((Button)rootView.findViewById(R.id.btn_edit_profile)).setOnClickListener(this);
				}
				break;
			case 3:
				MainActivity.id_user=0;
				//MainActivity.mMenuTitles=getResources().getStringArray(R.array.menus_array);
				
				rootView=inflater.inflate(R.layout.activity_login, container, false);
				/*String ar[]={"a","b"};
				
				((ListView)rootView.findViewById(R.id.left_drawer)).setAdapter(new ArrayAdapter<String>(getActivity(),
		                R.layout.drawer_list_item, ar));*/
				break;
			default:
				break;
		}
        getActivity().setTitle(titleMenu);
        
        return rootView;
    }
    private void getActivityPlace(){
		activity=(Activity)rootView.getContext();
		_requestGetPlace();
    }
    private void requestGetProfile(){
    	int id_user=MainActivity.id_user;
    	if(id_user!=0){
    		String path=ConnectServer.ADDRESS_LOCALHOST+"/users/getProfile?user_id="+id_user;
    		new RequestHTTPGet(){
    			protected void onPostExecute(String result) {
    				//Log.i("profile",""+result);
    				try {
						json_profile=new JSONObject(result);
						profile=result;
						//Log.i("json_email",""+jsonObject.get("email"));
						((TextView)rootView.findViewById(R.id.profile_address_id)).setText(json_profile.getString("address"));
						((TextView)rootView.findViewById(R.id.profile_name_id)).setText(json_profile.getString("user_name"));
						((TextView)rootView.findViewById(R.id.profile_email_id)).setText(json_profile.getString("email"));
						//load avarta
						String url_img = ConnectServer.ADDRESS_LOCALHOST+json_profile.getString("avarta");
						//requestImageAvarta(json_profile);
						Log.i("url",""+url_img);
						ImageLoader imageLoader=new ImageLoader(getActivity().getApplicationContext());
						imageLoader.DisplayImage(url_img,((ImageView)rootView.findViewById(R.id.profile_image_id)));
						
    				} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			};
    		}.execute(path);
    	}
    	
    }/*
    private void requestImageAvarta(JSONObject jsonObject){
    	String url_img=null;
		try {
			url_img = ConnectServer.ADDRESS_LOCALHOST+jsonObject.getString("avarta");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new FetchImageTask(){
			protected void onPostExecute(android.graphics.Bitmap result) {
				//Bitmap bm=new Bitmap(result);
				if(result!=null){
					BitmapDrawable ob=new BitmapDrawable(result);
					((ImageView)rootView.findViewById(R.id.profile_image_id)).setBackgroundDrawable(ob);
					//((ImageView)rootView.findViewById(R.id.profile_image_id)).setImageBitmap(result);
				}
				
			};
		}.execute(url_img);
    }
    */
    private void _requestGetPlace(){
		String path=ConnectServer.ADDRESS_LOCALHOST+"/places/getallplace";
		new RequestHTTPGet(){
			protected void onPostExecute(String result) {
				loadListPlace(getListPlaces(result));
			};
		}.execute(path);
	}
    
	// add data from webservice into app
	private void loadListPlace(ArrayList<Places> list_place){
		id_place=new int[list_place.size()];
		for(int i=0;i<list_place.size();i++){
			//Log.i("thu i",""+list_place.get(i).getId());
			id_place[i]=list_place.get(i).getId();
		}
		Place_Adapter place_Adapter=new Place_Adapter(getActivity(), android.R.layout.simple_list_item_1,list_place);
		//set adapter for listview
		((ListView) rootView.findViewById(R.id.lv_place)).setAdapter(place_Adapter);
		
		((ListView) rootView.findViewById(R.id.lv_place)).setOnItemClickListener(new ListViewItemPlaceClickListener());
	}
	 private class ListViewItemPlaceClickListener implements ListView.OnItemClickListener{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.i("list place",""+id_place[arg2]);
				
			}
	    	
	    }
	// function return arraylist<places> get list place 
	private ArrayList<Places> getListPlaces(String result){
		ArrayList<Places> list_place=new ArrayList<Places>();
		Places places;
		try{
			JSONArray jsonArray=new JSONArray(result);
    	    for(int i=0;i<jsonArray.length();i++){
    	    	JSONObject jsonObject=jsonArray.getJSONObject(i);
    	    	places=new Places(Integer.parseInt(jsonObject.getString("id")),jsonObject.getString("place_name"),jsonObject.getString("address"),jsonObject.getString("avarta"));
    	    	list_place.add(places);
    	    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return list_place;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(getActivity(), EditProfileActivity.class);
		intent.putExtra("profile_user",""+profile);
		startActivity(intent);
	}
	
	
}