package project.matching;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import matching.classmain.FetchImageTask;
import matching.classmain.PersonalPage;
import matching.classmain.PersonalPageAdapter;
import matching.classmain.Place_Adapter;
import matching.classmain.Places;
import matching.classmain.RequestHTTPGet;
import matching.classmain.User;
import matching.classmain.User_Adapter;
import matching.functioninterface.ConnectServer;
import matching.loadimage.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.MailTo;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matching.R;

public class MenuFragment extends Fragment implements OnClickListener,OnItemClickListener{
    public static final String ARG_MENU_NUMBER = "menu_number";
    PlacesActivity placesActivity;
    MainActivity mainActivity;
    View rootView;
    Activity activity;
    String profile,place;
    private int position_place;
    int id_place[],id_friend[];
    ImageView img_cover;
    ImageView img_avarta;
    InputStream imageStream;
    private int SELECT_PHOTO;
    private JSONObject json_profile;
    private ListView lv_status;
    public static int position_status;
    ImageButton btn_postStatus,btn_list_friend,btn_add_friend;
    EditText ed_contentStatus;
    //upload file
    String upLoadServerUri = null;
    ProgressDialog dialog = null;
    int serverResponseCode = 0;
    String path_image_upload;
    private int id_user;
   // ListView lv_place;
    //TextView
    Button btn_LoadStatus;
    private int numPlusLimitStatus=3;
    private static int numLoadListStatus=3;

    
    public MenuFragment() {
        // Empty constructor required for fragment subclasses
    	
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	id_user=MainActivity.id_user;
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
				rootView=inflater.inflate(R.layout.activity_login, container, false);
				break;
			case 4:
				if(MainActivity.id_user==0){
					rootView=inflater.inflate(R.layout.activity_login, container, false);
				}else{
					rootView=inflater.inflate(R.layout.activity_list_favorite, container, false);
					
					_requestGetPlacesFavorite();
				}
				break;
			case 5:
				if(MainActivity.id_user==0){
					rootView=inflater.inflate(R.layout.activity_login, container, false);
				}else{
					rootView=inflater.inflate(R.layout.activity_list_friend, container, false);
					_requestListFriend();
				}
				break;
			case 6:
				if(MainActivity.id_user==0){
					rootView=inflater.inflate(R.layout.activity_login, container, false);
				}else{
					rootView=inflater.inflate(R.layout.activity_personal_page, container, false);
					lv_status=((ListView) rootView.findViewById(R.id.lv_status));
					btn_LoadStatus=new Button(getActivity());
					btn_LoadStatus.setText("View more");
					btn_LoadStatus.setId(-1);
					btn_LoadStatus.setOnClickListener(this);
					lv_status.addFooterView(btn_LoadStatus);
					_requestListStatus(MainActivity.id_user,numLoadListStatus);
				}
				break;
			case 7:
				if(MainActivity.id_user==0){
					rootView=inflater.inflate(R.layout.activity_login, container, false);
				}else{
					Intent intent=new Intent(getActivity(), NewFeedActivity.class);
					//intent.putExtra("profile_user",""+profile);
					startActivity(intent);
					
				}
				break;
			default:
				rootView = inflater.inflate(R.layout.activity_places, container, false);
				break;
		}
        getActivity().setTitle(titleMenu);
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	numLoadListStatus=numPlusLimitStatus;
    }
    /*
	 * save info alter auto
	 * */
    
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
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
						((TextView)rootView.findViewById(R.id.profile_address_id)).setText(json_profile.getString("address"));
						((TextView)rootView.findViewById(R.id.profile_name_id)).setText(json_profile.getString("user_name"));
						((TextView)rootView.findViewById(R.id.profile_email_id)).setText(json_profile.getString("email"));
						String url_img = ConnectServer.ADDRESS_LOCALHOST+json_profile.getString("avarta");
						ImageLoader imageLoader=new ImageLoader(getActivity().getApplicationContext());
						imageLoader.DisplayImage(url_img,((ImageView)rootView.findViewById(R.id.profile_image_id)));
						
    				} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			};
    		}.execute(path);
    	}
    	
    }
    
    private void _requestGetPlace(){
		String path=ConnectServer.ADDRESS_LOCALHOST+"/places/getallplace";
		new RequestHTTPGet(){
			protected void onPostExecute(String result) {
				place=result;
				//Log.i("place",""+place);
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
		
		((ListView) rootView.findViewById(R.id.lv_place)).setOnItemClickListener(this);
	}
	 
	 // request get places favorite of user
	 private void _requestGetPlacesFavorite(){
    	 String path=ConnectServer.ADDRESS_LOCALHOST+"/placefavorites/getPlacesFavorite?user_id="+MainActivity.id_user;
    	 new RequestHTTPGet(){
    		 protected void onPostExecute(String result) {
    			 place=result;
    			 loadListPlaceFavorite(getListPlaces(result));
    		 };
    	 }.execute(path);
     }
	 
	 private void loadListPlaceFavorite(ArrayList<Places> list_place){
			id_place=new int[list_place.size()];
			for(int i=0;i<list_place.size();i++){
				//Log.i("thu i",""+list_place.get(i).getId());
				id_place[i]=list_place.get(i).getId();
			}
			Place_Adapter place_Adapter=new Place_Adapter(getActivity(), android.R.layout.simple_list_item_1,list_place);
			//set adapter for listview
			((ListView) rootView.findViewById(R.id.id_list_favorite)).setAdapter(place_Adapter);
			
			((ListView) rootView.findViewById(R.id.id_list_favorite)).setOnItemClickListener(this);
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
	
	/*****************************************************************************
	 * event click
	 * 
	 * ***************************************************************************/
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.btn_edit_profile){
			Intent intent=new Intent(getActivity(), EditProfileActivity.class);
			intent.putExtra("profile_user",""+profile);
			startActivity(intent);
		}
		
		if(v.getId()==R.id.img_avarta){
			//getDialogChangeAvarta().show();
			SELECT_PHOTO=2;
			getDialogChangeImage("Do you want to change another avarta?").show();
		}
		if(v.getId()==R.id.img_cover){
			SELECT_PHOTO=1;
			getDialogChangeImage("Do you want to change another cover?").show();
		}
		
		if(v.getId()==R.id.btn_post_status){
			//Log.i("onclick", "post status");
			_requestPostStatus(ed_contentStatus.getText().toString());
		}
		if(v.getId()==-1){
			Log.i("load more", "ok");
			numLoadListStatus+=2;
			_requestListStatus(MainActivity.id_user,numLoadListStatus);
			//android.view.ViewGroup.LayoutParams
			//lv_status
		}
		if(v.getId()==R.id.btn_list_friend){
			Intent intent=new Intent(getActivity(), ListFriendActivity.class);
			intent.putExtra("user_id",""+MainActivity.id_user);
			startActivity(intent);
			
		}
	}
	//request post status
	private void _requestPostStatus(String message){
		String path =ConnectServer.ADDRESS_LOCALHOST+"/statuses/addStatus?user_id="+MainActivity.id_user+"&content="+message;
		new RequestHTTPGet(){
			protected void onPostExecute(String result) {
				if(result.equals("1")){
					//Log.i("post", "ok");
					_requestListStatus(MainActivity.id_user,numLoadListStatus);
					ed_contentStatus.setText("");
				}else{
					Log.i("post", "no");
				}
			};
		}.execute(path);
	}
	private AlertDialog getDialogChangeImage(String message){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(""+message)
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // MyActivity.this.finish();
		        	   Intent intent=new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		   			   startActivityForResult(intent,SELECT_PHOTO);
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
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {
	    case 1:
	        if(resultCode == Activity.RESULT_OK){  
	        	String url=getUrlImageFromSdcard(img_cover,data);
	        	String path=url;
	        	if(url!=null){
		    		path_image_upload=url;
	                Log.i("path",""+path_image_upload);
	                upLoadServerUri = ConnectServer.ADDRESS_LOCALHOST+"/images/upload_cover";
	                new Thread(new Runnable() {
	                    public void run() {
	                         uploadFile(path_image_upload);
	                    }
	                  }).start(); 
	    		}else
	    			Toast.makeText(getActivity(), "Size of the file is too large", Toast.LENGTH_SHORT).show();
	        }
	        break;
	    case 2:
	    	if(resultCode == Activity.RESULT_OK){
	    		String url=getUrlImageFromSdcard(img_avarta,data);
	    		if(url!=null){
		    		path_image_upload=url;
	                Log.i("path",""+path_image_upload);
	                upLoadServerUri = ConnectServer.ADDRESS_LOCALHOST+"/images/upload_avarta";
	                new Thread(new Runnable() {
	                    public void run() {
	                         uploadFile(path_image_upload);
	                    }
	                  }).start(); 
	    		}else
	    			Toast.makeText(getActivity(), "Size of the file is too large", Toast.LENGTH_SHORT).show();
	        }
	    	break;
	    }
	}
	//get bitmap from sdcard
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
	private String getUrlImageFromSdcard(ImageView imgView,Intent data){
		Uri selectedImage = data.getData();
			try {
				imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
         //Log.i("size file",""+yourSelectedImage.getByteCount());
         int file_size=yourSelectedImage.getByteCount();
         if(file_size<8000000){
	         imgView.setImageBitmap(yourSelectedImage);
	         final Uri contentUri = data.getData();
	         final String[] proj = { MediaStore.Images.Media.DATA};
	         final Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
	         final int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	         cursor.moveToLast();
	         final String path = cursor.getString(column_index);
	         return path;
         }
         return null;
	}
	/*
	 **********************************
	 * fuctions Friend
	 * ********************************
	 * */
	
	//request get friend of user
	private void _requestListFriend(){
		String path=ConnectServer.ADDRESS_LOCALHOST+"/friends/getFriends?user_id="+mainActivity.id_user;
		new RequestHTTPGet(){
			protected void onPostExecute(String result) {
				//Log.i("friend",""+result);
				loadListFriend(getListFriend(result));
			};
		}.execute(path);
		
	}
	
	 private void loadListFriend(ArrayList<User> list_user){
		 	id_friend=new int[list_user.size()];
			for(int i=0;i<list_user.size();i++){
				//Log.i("thu i",""+list_place.get(i).getId());
				id_friend[i]=list_user.get(i).getId();
			}
		 
			User_Adapter user_Adapter=new User_Adapter(getActivity(), android.R.layout.simple_list_item_1, list_user);
			//set adapter for listview
			((ListView) rootView.findViewById(R.id.id_list_friend)).setAdapter(user_Adapter);
			
			((ListView) rootView.findViewById(R.id.id_list_friend)).setOnItemClickListener(this);
			
		}
	
	// function return arraylist<places> get list place 
	private ArrayList<User> getListFriend(String result){
		ArrayList<User> list_friend=new ArrayList<User>();
		User user;
		try{
			JSONArray jsonArray=new JSONArray(result);
   	    for(int i=0;i<jsonArray.length();i++){
   	    	JSONObject jsonObject=jsonArray.getJSONObject(i);
   	    	//Log.i("id_réult",""+jsonObject.getString("user_name"));
   	    	
   	    	user=new User(Integer.parseInt(jsonObject.getString("id")), jsonObject.getString("user_name"), jsonObject.getString("email"), jsonObject.getString("avarta"), jsonObject.getString("address"));
   	    	list_friend.add(user);
   	    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return list_friend;
	}
	
	/*
	 **********************************
	 * STATUS
	 * ********************************
	 * */
	
	//request get friend of user
	public void _requestListStatus(int user_id,int numlimit){
		
		//Log.i("user_id",""+MainActivity.id_user);
		//Log.i("limit",""+numLoadListStatus);
		String path=ConnectServer.ADDRESS_LOCALHOST+"/statuses/getStatusPersonalPage?user_id="+user_id+"&limit="+numlimit;
		new RequestHTTPGet(){
			protected void onPostExecute(String result) {
				//Log.i("status",""+result);
				//if(!result.equals("2")){
					//Log.i("equals","2");
				
				loadListStatus(getListStatus(result),lv_status,numLoadListStatus);//1 exitst comment
				/*}else{
					
					loadListStatus(getListStatus(result,2),2);//2 not exitst comment
					btn_LoadStatus.setVisibility(View.INVISIBLE);
				}*/
			};
		}.execute(path);
	}
	 private void loadListStatus(ArrayList<PersonalPage> list_status,ListView lv_status,int numLimit){
		 	
		 	//Log.i("url_img cover",""+list_status.size());
		 	if(list_status.get(0).getContent()!=null){
				PersonalPageAdapter pageAdapter=new PersonalPageAdapter(getActivity(), android.R.layout.simple_list_item_1, list_status);
				//set height list view status
				lv_status.setAdapter(pageAdapter);
				lv_status.setOnItemClickListener(this);
				int num_itemAdapter=pageAdapter.getCount();
				//Log.i("numadapter",""+num_itemAdapter);
				//Log.i("numlimit",""+numLimit);
				if(num_itemAdapter>=numLimit){
					LayoutParams lParams=(LayoutParams)lv_status.getLayoutParams();
					lParams.height=num_itemAdapter*350;
					lv_status.setLayoutParams(lParams);
				}
		 	}
		 	if(list_status.size()>0){
				ImageLoader imageLoader=new ImageLoader(getActivity().getApplicationContext());
				imageLoader.clearCache();
				// show,edit image cover
				img_cover=(ImageView) rootView.findViewById(R.id.img_cover);
				String url=ConnectServer.ADDRESS_LOCALHOST+list_status.get(0).getImage_cover();
				//Log.i("url_img cover",""+url);
				imageLoader.DisplayImage(url, img_cover);
				img_cover.setOnClickListener(this);
				// show,edit image avarta
				img_avarta=(ImageView) rootView.findViewById(R.id.img_avarta);
				url=ConnectServer.ADDRESS_LOCALHOST+list_status.get(0).getAvarta();
				imageLoader.DisplayImage(url, img_avarta);
				img_avarta.setOnClickListener(this);
				
				//button post status
				btn_postStatus=(ImageButton)rootView.findViewById(R.id.btn_post_status);
				btn_postStatus.setOnClickListener(this);
				
				btn_list_friend=(ImageButton)rootView.findViewById(R.id.btn_list_friend);
				btn_list_friend.setOnClickListener(this);
				btn_add_friend=(ImageButton)rootView.findViewById(R.id.btn_add_friend);
				btn_add_friend.setVisibility(View.INVISIBLE);
				
				//edittext content status
				ed_contentStatus=(EditText)rootView.findViewById(R.id.ed_content_status);
				
				
				TextView tv_userName=(TextView)rootView.findViewById(R.id.tv_user_name_main);
				tv_userName.setText(""+list_status.get(0).getUser_name());
				//((ListView) rootView.findViewById(R.id.id_list_favorite)).setOnItemClickListener(new ListViewItemPlaceClickListener());
		 	}
		}
	
	// function return arraylist<places> get list place 
	private ArrayList<PersonalPage> getListStatus(String result){
		ArrayList<PersonalPage> list_status=new ArrayList<PersonalPage>();
		PersonalPage page;
		try{
			JSONArray jsonArray=new JSONArray(result);
   	    for(int i=0;i<jsonArray.length();i++){
   	    	JSONObject jsonObject=jsonArray.getJSONObject(i);
   	    	//Log.i("id_réult",""+jsonObject.length());
   	    	
   	    	if(jsonObject.length()!=4){
   	    		
   	    		page=new PersonalPage(Integer.parseInt(jsonObject.getString("id")), Integer.parseInt(jsonObject.getString("user_id"))
   	    			,jsonObject.getString("user_name"), jsonObject.getString("status_time"), jsonObject.getString("avarta"), jsonObject.getString("content"),jsonObject.getString("image_cover"),
   	    			Integer.parseInt(jsonObject.getString("num_like")),Integer.parseInt(jsonObject.getString("num_comment")));
   	    	}else
   	    	{
   	    		//Log.i("da","xuong"+check);
   	    		page=new PersonalPage(Integer.parseInt(jsonObject.getString("user_id")),jsonObject.getString("user_name"), jsonObject.getString("avarta"), jsonObject.getString("image_cover"));
   	    	}
   	    	list_status.add(page);
   	    }
		}catch(Exception e){
			e.printStackTrace();
		}
		return list_status;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Log.i("click item status", "ok"+arg0.getId());
		// TODO Auto-generated method stub
		position_place=position;
		if(arg0.getId()==R.id.lv_place||arg0.getId()==R.id.id_list_favorite){
				String path=ConnectServer.ADDRESS_LOCALHOST+"/ViewPlaces/saveViewPlace?user_id="+MainActivity.id_user+"&place_id="+id_place[position];
				new RequestHTTPGet(){
					protected void onPostExecute(String result) {
						Log.i("click item status", "ok"+result);
						Intent intent=new Intent(getActivity(),DetailPlaceActivity.class);
						//Log.i("")
						intent.putExtra("list_place",place);
						intent.putExtra("place_id",""+id_place[position_place]);
						startActivity(intent);
					};
				}.execute(path);
			
			
		}
		if(arg0.getId()==R.id.id_list_friend){
			Log.i("click item friend", "ok"+id_friend[position]);
			Intent intent=new Intent(getActivity(), PersonalPageActivity.class);
			intent.putExtra("user_id",""+id_friend[position]);
			startActivity(intent);
		}
	}
	
	/*
	 * UPLOAD FILE
	 * */
	public int uploadFile(String sourceFileUri) {
        
        String filetype=sourceFileUri.substring(sourceFileUri.lastIndexOf("."));
        String fileName = ""+MainActivity.id_user+""+filetype;
        
        HttpURLConnection conn = null;
        DataOutputStream dos = null;  
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024; 
        File sourceFile = new File(sourceFileUri); 
        if (!sourceFile.isFile()) {
             
            // dialog.dismiss(); 
              
            
             return 0;
          
        }
        else
        {
             try { 
                   // open a URL connection to the Servlet
                 FileInputStream fileInputStream = new FileInputStream(sourceFile);
                 URL url = new URL(upLoadServerUri);
                 
                 // Open a HTTP  connection to  the URL
                 conn = (HttpURLConnection) url.openConnection(); 
                 conn.setDoInput(true); // Allow Inputs
                 conn.setDoOutput(true); // Allow Outputs
                 conn.setUseCaches(false); // Don't use a Cached Copy
                 conn.setRequestMethod("POST");
                 conn.setRequestProperty("Connection", "Keep-Alive");
                 conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                 conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                 conn.setRequestProperty("uploaded_file", fileName); 
                 
                 dos = new DataOutputStream(conn.getOutputStream());
                 dos.writeBytes(twoHyphens + boundary + lineEnd); 
                 dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename="+ fileName + "" + lineEnd);
                 
                 dos.writeBytes(lineEnd);
                 // create a buffer of  maximum size
                 bytesAvailable = fileInputStream.available(); 
        
                 bufferSize = Math.min(bytesAvailable, maxBufferSize);
                 buffer = new byte[bufferSize];
        
                 // read file and write it into form...
                 bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
                 
                 while (bytesRead > 0) {
                      
                   dos.write(buffer, 0, bufferSize);
                   bytesAvailable = fileInputStream.available();
                   bufferSize = Math.min(bytesAvailable, maxBufferSize);
                   bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
                    
                  }
        
                 // send multipart form data necesssary after file data...
                 dos.writeBytes(lineEnd);
                 dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
        
                 // Responses from the server (code and message)
                 serverResponseCode = conn.getResponseCode();
                 String serverResponseMessage = conn.getResponseMessage();
                   
                 Log.i("uploadFile", "HTTP Response is : "
                         + serverResponseMessage + ": " + serverResponseCode);
                 
                 if(serverResponseCode == 200){
                     
                     getActivity().runOnUiThread(new Runnable() {
                          public void run() {
                               
                              
                              //messageText.setText(msg);
                              Toast.makeText(getActivity(), "File Upload Complete.", 
                                           Toast.LENGTH_SHORT).show();
                          }
                      });                
                 }else{
                	 Toast.makeText(getActivity(), "File size very max", Toast.LENGTH_SHORT).show();
                 }    
                  
                 //close the streams //
                 fileInputStream.close();
                 dos.flush();
                 dos.close();
                   
            } catch (MalformedURLException ex) {
                //dialog.dismiss();  
                ex.printStackTrace();
                 
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        //messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(getActivity(), "MalformedURLException", 
                                                            Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
            } catch (Exception e) {
                //dialog.dismiss();  
                e.printStackTrace();
                 
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                       // messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(getActivity(), "Got Exception : see logcat ", 
                                Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file to server Exception", "Exception : "
                                                 + e.getMessage(), e);  
                
            }
           // dialog.dismiss();       
            return serverResponseCode; 
             
         } // End else block 
       }
	
}