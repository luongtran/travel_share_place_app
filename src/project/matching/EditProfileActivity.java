package project.matching;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import matching.classmain.RequestHTTPGet;
import matching.classmain.RequestHTTPPost;
import matching.functioninterface.ConnectServer;
import matching.loadimage.ImageLoader;

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

import android.R.integer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorJoiner.Result;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditProfileActivity extends Activity{
	private JSONObject json_profile;
	private EditText ed_name,ed_address;
	private TextView tv_email;
	private ImageView img_profile;
	private ImageLoader imageLoader;
	private String id;
	private static final int SELECT_PHOTO = 100;
	InputStream imageStream;
	 TextView messageText;
	    Button uploadButton;
	    int serverResponseCode = 0;
	    ProgressDialog dialog = null;
	       
	    String upLoadServerUri = null;
	    
	    /**********  File Path *************/
	    final String uploadFilePath = "/mnt/sdcard/Pictures/";
	    final String uploadFileName = "avarta.jpg";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		init();
		
	}
	
	private void init(){
		Bundle bundle=getIntent().getExtras();
		String profile_user=""+bundle.get("profile_user");
		ed_name=(EditText)findViewById(R.id.profile_edit_name_id);
		tv_email=(TextView)findViewById(R.id.profile_edit_email_id);
		ed_address=(EditText)findViewById(R.id.profile_edit_address_id);
		img_profile=(ImageView)findViewById(R.id.profile_edit_image_id);
		imageLoader = new ImageLoader(getApplicationContext());
		
		try{
			json_profile=new JSONObject(profile_user);
			//Log.i("json",""+json_profile.get("user_name"));
			ed_name.setText(""+json_profile.get("user_name"));
			ed_address.setText(""+json_profile.get("address"));
			tv_email.setText(""+json_profile.get("email"));
			String path=ConnectServer.ADDRESS_LOCALHOST+""+json_profile.get("avarta");
			imageLoader.DisplayImage(path,img_profile);
			id=""+json_profile.get("id");
		}catch(JSONException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.matching, menu);
		return true;
	}
	
	public void on_save_profile(View v){
		/*Log.i("save","profile");
		String user_name=ed_name.getText().toString();
		String address=ed_address.getText().toString();
		
		String path=ConnectServer.ADDRESS_LOCALHOST+"/users/updateUser?id="+id+"&user_name="+user_name+"&address="+address;
		Log.i("path",""+path);
		new RequestHTTPGet(){
			protected void onPostExecute(String result) {
				Log.i("result",""+result);
				if(Integer.parseInt(result)==1){
					Toast.makeText(getApplicationContext(), "saved",Toast.LENGTH_SHORT).show();
				}else
					Toast.makeText(getApplicationContext(), "not save",Toast.LENGTH_SHORT).show();
			};
		}.execute(path);*/
messageText.setText("Uploading file path :- '/mnt/sdcard/Pictures"+uploadFileName+"'");
        
        /************* Php script path ****************/
    	upLoadServerUri = "http://www.androidexample.com/media/UploadToServer.php";
                dialog = ProgressDialog.show(getApplicationContext(), "", "Uploading file...", true);
                new Thread(new Runnable() {
                        public void run() {
                             runOnUiThread(new Runnable() {
                                    public void run() {
                                    	
                                    	messageText.setText("uploading started.....");
                                    	
                                    }
                                });                      
                          
                             //uploadFile(uploadFilePath + "" + uploadFileName);
                                                     
                        }
                      }).start();        
                
           
	}
	
	public void on_image_edit_profile(View v){
		Log.i("image","onclick");
		Intent i = new Intent(Intent.ACTION_PICK,
	               android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i,SELECT_PHOTO); 
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

	    switch(requestCode) {
	    case SELECT_PHOTO:
	        if(resultCode == RESULT_OK){  
	            Uri selectedImage = data.getData();
	            
	            Log.i("select image",""+data.getData().getPath());
				try {
					imageStream = getContentResolver().openInputStream(selectedImage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
	            
	            img_profile.setImageBitmap(yourSelectedImage);
	            
	            final Uri contentUri = data.getData();
                final String[] proj = { MediaStore.Images.Media.DATA};
                final Cursor cursor = managedQuery(contentUri, proj, null, null, null);
                final int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToLast();
                final String path = cursor.getString(column_index);
                Log.i("path",""+path);
                
                
	        }
	    }
	}
	//function upload
	public int uploadFile(String sourceFileUri) {
        
  	  
  	  String fileName = sourceFileUri;

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
      	  
	           dialog.dismiss(); 
	           
	           Log.e("uploadFile", "Source File not exist :"
	        		               +uploadFilePath + "" + uploadFileName);
	           
	           runOnUiThread(new Runnable() {
	               public void run() {
	            	   messageText.setText("Source File not exist :"
	            			   +uploadFilePath + "" + uploadFileName);
	               }
	           }); 
	           
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
	               dos.writeBytes("Content-Disposition: form-data; name=uploaded_file;filename="+ fileName + " " + lineEnd);
	               
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
	                   runOnUiThread(new Runnable(){
	                        public void run() {
	                        	
	                        	String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
	                        		          +" http://www.androidexample.com/media/uploads/"
	                        		          +uploadFileName;
	                        	
	                        	messageText.setText(msg);
	                            Toast.makeText(EditProfileActivity.this, "File Upload Complete.", 
	                            		     Toast.LENGTH_SHORT).show();
	                        }
	                    } );    
	                   
	               }    
	               
	               //close the streams //
	               fileInputStream.close();
	               dos.flush();
	               dos.close();
	                
	          }catch(MalformedURLException ex) {
	        	  
	              dialog.dismiss();  
	              ex.printStackTrace();
	              
	              runOnUiThread(new Runnable() {
	                  public void run() {
	                	  messageText.setText("MalformedURLException Exception : check script url.");
	                      Toast.makeText(EditProfileActivity.this, "MalformedURLException", 
                                                            Toast.LENGTH_SHORT).show();
	                  }
	              });
	              
	              Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
	          } catch (Exception e) {
	        	  
	              dialog.dismiss();  
	              e.printStackTrace();
	              
	              runOnUiThread(new Runnable() {
	                  public void run() {
	                	  messageText.setText("Got Exception : see logcat ");
	                      Toast.makeText(EditProfileActivity.this, "Got Exception : see logcat ", 
	                    		  Toast.LENGTH_SHORT).show();
	                  }
	              });
	              Log.e("Upload file to server Exception", "Exception : " 
	            		                           + e.getMessage(), e);  
	          }
	          dialog.dismiss();       
	          return serverResponseCode; 
	          
         } // End else block 
       
}
	
}
