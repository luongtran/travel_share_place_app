

package project.matching;

import java.util.List;
import java.util.Locale;

import matching.classmain.RequestHTTPPost;
import matching.functioninterface.ConnectServer;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.matching.R;
public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList,Lv_place;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    public static String[] mMenuTitles;
    public static int id_user;
    
    public static String email=null;
    private TextView ed_email;
	private TextView ed_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle(); 
        Lv_place=(ListView)findViewById(R.id.lv_place);
        
        
        getMenuTitles();
        
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mMenuTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null) {
            selectItem(0);
        }
        
    }
    private void getMenuTitles(){
    	 String ar[]=getResources().getStringArray(R.array.menus_array);
        // if(check_login==0){
         	mMenuTitles = ar;
         /*}else{
        	for(int i=0;i<ar.length;i++){
        		if(ar[i].equals("Login")){
        			ar[i]=getEmail();
        			break;
        		}
        	}
         	mMenuTitles=ar;
         }*/
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
       // menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_websearch:
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
   
    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    
    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putInt(MenuFragment.ARG_MENU_NUMBER, position);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mMenuTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
    
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    public void on_login(View v){
		//Toast.makeText(getApplicationContext(), "login this", Toast.LENGTH_SHORT).show();
		/*Intent intent=new Intent(getApplicationContext(),PlacesActivity.class);
		startActivity(intent);*/
		ed_email=(EditText)findViewById(R.id.edit_email);
		ed_password=(EditText)findViewById(R.id.edit_password);
		if(ed_email.getText().toString()=="" ||ed_password.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(),"Enter your email or your password", Toast.LENGTH_SHORT).show();
			setId_User(0);
		}else{
			
			if(!isEmailValid(ed_email.getText().toString())){
				Toast.makeText(getApplicationContext(),"format email not valid", Toast.LENGTH_SHORT).show();
				setId_User(0);
			}else{
				_request_login(ed_email.getText().toString(),ed_password.getText().toString());
				
			}
		}
    }
    
    private void _request_login(String em,String pass){
		String path=ConnectServer.ADDRESS_LOCALHOST+"/users/login";
		String ar_post[]={path,em,pass};
		new RequestHTTPPost(){
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				Log.i("result",""+result);
				if(result.equals("0")){
					//setLogin(1);
					Toast.makeText(getApplicationContext(),"Accout not valid", Toast.LENGTH_SHORT).show();
					setId_User(0);
				}else{
					//setLogin(0);
					//Toast.makeText(getApplicationContext(),"Complete", Toast.LENGTH_SHORT).show();
					setId_User(Integer.parseInt(result));
					setEmail(ed_email.getText().toString());
					Intent intent=new Intent(getBaseContext(), MainActivity.class);
					startActivity(intent);
				}
			}
		}.execute(ar_post);
	
	}
    public void on_register(View v){
    	Intent intent=new Intent(getBaseContext(), RegisterActivity.class);
    	startActivity(intent);
    }
    private boolean isEmailValid(CharSequence email){
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}
    //function edit profile 
    public void on_edit_profile(View v){
    	Log.i("click_edit","ok");
    	Intent intent=new Intent(getBaseContext(), EditProfileActivity.class);
    	startActivity(intent);
    }
    //get set
    public void setId_User(int check_login) {
		this.id_user = check_login;
	}
    
    public int getId_User() {
		return id_user;
	}
    public void setEmail(String email) {
		this.email = email;
	}
    public String getEmail() {
		return this.email;
	}
    
}