package matching.gallery;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import matching.classmain.RequestHTTPGet;
import matching.functioninterface.ConnectServer;
import matching.loadimage.ImageLoader;

import com.example.matching.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
public class GalleryImageAdapter extends BaseAdapter 
{
    private Context mContext;
    private int id_place;
   
    public String[] images_place=new String[0];
    public GalleryImageAdapter(Context context) 
    {
        mContext = context;
    }
    
    public void loadListImage(String[] arr_path){
    	//Log.i("length arr_path",""+arr_path.length);
    	images_place=new String[arr_path.length];
    	//images_place=arr_path;
    	for(int i=0;i<arr_path.length;i++){
    		images_place[i]=arr_path[i];
    	}
    	//Log.i("image",""+images_place[1]);
    	
    }
    public int getCount() {
    	//Log.i("getCount","acs"+images_place.length);
        return images_place.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    // Override this method according to your need
    public View getView(int index, View view, ViewGroup viewGroup) 
    {
        // TODO Auto-generated method stub
        ImageView i = new ImageView(mContext);
        
        //i.setImageResource(mImageIds[index]);
        ImageLoader imageLoader=new ImageLoader(mContext);
        imageLoader.DisplayImage(images_place[index], i);
        
        i.setLayoutParams(new Gallery.LayoutParams(100, 100));
        
        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
    }
}