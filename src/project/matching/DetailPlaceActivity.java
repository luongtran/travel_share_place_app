package project.matching;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import matching.classmain.CommentPlace_Addapter;
import matching.classmain.CommentsPlace;
import matching.classmain.Places;
import matching.classmain.RequestHTTPGet;
import matching.classmain.RequestHTTPPost;
import matching.detailplace.DetailPlaceFragment;
import matching.detailplace.ImagePlaceFragment;
import matching.detailplace.MapPlaceFragment;
import matching.functioninterface.ConnectServer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.matching.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.app.Activity;
import android.database.CursorJoiner.Result;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailPlaceActivity extends FragmentActivity implements ActionBar.TabListener {

    AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    String json_place;
    String id_place;
    ViewPager mViewPager;
    private EditText ed_comment;
    ActionBar.Tab tab1;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        
        //get data from activity mainActivity
        Bundle bundle=getIntent().getExtras();
        
        json_place=bundle.getString("list_place");
        id_place=bundle.getString("place_id");
        //init
        ed_comment=(EditText)findViewById(R.id.ed_comment_place);
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager(),json_place,id_place);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        // Specify that the Home/Up button should not be enabled, since there is no hierarchical
        // parent.
        
       // actionBar.setHomeButtonEnabled(false);

        // Specify that we will be displaying tabs in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager, attaching the adapter and setting up a listener for when the
        // user swipes between sections.
        
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When swiping between different app sections, select the corresponding tab.
                // We can also use ActionBar.Tab#select() to do this if we have a reference to the
                // Tab.
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in the ViewPager.
    	//Log.i("position",""+tab.getPosition());
    	tab1=tab;
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {
    	
    	String json_place,place_id;
    	
        public AppSectionsPagerAdapter(FragmentManager fm,String json,String id_place) {
            super(fm);
            this.json_place=json;
            this.place_id=id_place;
        }
        
        @Override
        public Fragment getItem(int i) {
        	//Log.i("abcd",""+abcd);
        	
            switch (i) {
                case 0:
                    // The first section of the app is the most interesting -- it offers
                    // a launchpad into the other demonstrations in this example application.
                	//Log.i("comments","abc0");
                	DetailPlaceFragment detailPlaceFragment=new DetailPlaceFragment();
                	detailPlaceFragment.transfer_data(json_place,place_id);
                    return detailPlaceFragment;

                case 1:
                	ImagePlaceFragment imagePlaceFragment=new ImagePlaceFragment();
                	//Log.i("comments","abc2");
                	imagePlaceFragment.transfer_data(json_place,place_id);
                    return imagePlaceFragment;
               
                default:
                	//Log.i("comments","abc3");
                	MapPlaceFragment mapPlaceFragment=new MapPlaceFragment();
                	mapPlaceFragment.transfer_data(json_place,place_id);
                    return mapPlaceFragment;
            }
        }
        // number tab
        @Override
        public int getCount() {
            return 3;
        }
        // get name tab
        @Override
        public CharSequence getPageTitle(int position) {
        	if(position==0){
        		return "Place";
        	}
        	if(position==1)
        		return "Images";
        	return "Map";
        }
    }

 
   
    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    
    /*public static class MapPlaceFragment extends Fragment {
        public static final String ARG_SECTION_NUMBER = "section_number";
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_map_place, container, false);
            Bundle args = getArguments();
            
            ((TextView) rootView.findViewById(android.R.id.text1)).setText("MAP");
            return rootView;
        }
    }*//*
    public static class CommentFragment extends Fragment {
    	@Override
    	public View onCreateView(LayoutInflater inflater, ViewGroup container,
    			Bundle savedInstanceState) {
    		// TODO Auto-generated method stub
    		View rootView=inflater.inflate(R.layout.f, root)
    		return super.onCreateView(inflater, container, savedInstanceState);
    	}
    }*/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	// TODO Auto-generated method stub
    	
    	super.onSaveInstanceState(outState);
    	//Log.i("auto_json",""+json_place);
    	outState.putString("result_places",json_place);
    	outState.putString("id_place",id_place);
    	
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onRestoreInstanceState(savedInstanceState);
    	//Log.i("get_auto_json",""+savedInstanceState.getString("id_place"));
    	json_place=savedInstanceState.getString("result_places");
    	id_place=savedInstanceState.getString("id_place");
    	mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager(),json_place,id_place);
    	
    }
    //function process event click button comment
    
    public void on_comment(View v){
    	//Log.i("btn_comment","ok");
    	if(MainActivity.id_user==0){
    		getToast("not logged in");
    	}else
    	{
    		int id_user=MainActivity.id_user;
    		//String id_place=id_place;
    		ed_comment=(EditText)findViewById(R.id.ed_comment_place);
    		String message=ed_comment.getText().toString();
    		String path=ConnectServer.ADDRESS_LOCALHOST+"/PlaceComments/saveComment?place_id="+id_place+"&user_id="+id_user+"&message="+message;
    		new RequestHTTPGet(){
    			protected void onPostExecute(String result) {
    				//Log.i("result_comment",""+result);
    				if(result.equals("1")){
    					ed_comment.setText("");
    					//back detail place
    					//mViewPager.setCurrentItem(0);
    					//DetailPlaceFragment detailPlaceFragment=new DetailPlaceFragment();
    					//detailPlaceFragment.abc();
    				}
    			};
    		}.execute(path);
    	}
    	
    }
   
    public void getToast(String message){
    	Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_SHORT).show();
    }
}

