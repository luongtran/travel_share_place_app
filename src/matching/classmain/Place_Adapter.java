package matching.classmain;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import matching.functioninterface.ConnectServer;
import matching.loadimage.ImageLoader;

import com.example.matching.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Place_Adapter extends ArrayAdapter<Places>{

	TextView tv_placeName;
    TextView tv_placeAddress;
	ImageView img_avarta;
	 Places item;
	 ImageLoader imageLoader;
	
	/**
	 * @param args
	 */
	
	private Activity context;
	ArrayList<Places> data=null;
	
	public Place_Adapter(Activity context, int resource,ArrayList<Places> objects) {
		super(context, resource, objects);
		this.context=context;
		this.data=objects;
		imageLoader = new ImageLoader(context.getApplicationContext());
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return initView(position,convertView,parent);
	}
	
	 @Override
	public View getDropDownView(int position, View convertView, ViewGroup parent)
	{   // This view starts when we click the spinner.
	    return initView(position, convertView, parent);
	}
	private View initView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.place_adapter, parent, false);
        }
        Places item = data.get(position);
        //String test = item.getPlace_name();
       // Log.d("test ", test);
        if(item != null)
        {   
            tv_placeName = (TextView) row.findViewById(R.id.tv_name_place);
            tv_placeAddress=(TextView)row.findViewById(R.id.tv_address_place);
            img_avarta=(ImageView)row.findViewById(R.id.img_avarta_place);
          //  if(tv_placeName != null && tv_placeAddress!=null){
            	String path=ConnectServer.ADDRESS_LOCALHOST+item.getAvarta();
            	tv_placeName.setText(item.getPlace_name());
            	tv_placeAddress.setText(item.getAddress());
            	//Log.i("url","");
            	//img_avarta.setImageDrawable(loadImageFromUrl(path));
            	imageLoader.DisplayImage(path, img_avarta);
            	
            	/*
            	new FetchImageTask(){
            		protected void onPostExecute(Bitmap result) {
            			if(result!=null){
            				//Log.i("image","ok");
            				
            				img_avarta.setImageBitmap(result);
            			}
            		};
            	}.execute(path);*/
            	
            	//Log.i("avarta_place",""+item.getAvarta());
             //Log.d("find me ", cityname.toString());
            }

       // }

        return row;
    }
   
}
