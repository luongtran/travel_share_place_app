package matching.detailplace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import matching.classmain.RequestHTTPGet;
import matching.functioninterface.ConnectServer;
import matching.gallery.GalleryImageAdapter;
import matching.loadimage.ImageLoader;

import com.example.matching.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImagePlaceFragment extends Fragment{
	String json,id_place;
	ImageView image_gallery;
	Gallery gallery_place;
	String images_place[];
	View rootView;
	GalleryImageAdapter galleryImageAdapter;
	public void transfer_data(String json,String id_place) {
		// TODO Auto-generated constructor stub
		this.json=json;
		this.id_place=id_place;
		
	}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_images_place, container, false);
        
        return rootView;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	if(savedInstanceState!=null){
    		json=savedInstanceState.getString("json");
    		id_place=savedInstanceState.getString("id_place");
    		
    	}
    	galleryImageAdapter=new GalleryImageAdapter(getActivity().getApplicationContext());
        request_getImagesPlace();
    }
    
    private void request_getImagesPlace(){
    	String path=ConnectServer.ADDRESS_LOCALHOST+"/images/getImagesByIDPlace?place_id="+id_place;
    	new RequestHTTPGet(){
    		@Override
    		protected void onPostExecute(String result) {
    			// TODO Auto-generated method stub
    			super.onPostExecute(result);
    			if(result.equals("0")){
    				//Log.i("no",""+result);
    				images_place=new String[0];
    			}else{
    				
    				JSONArray jsonArray;
					try {
						jsonArray = new JSONArray(result);
						images_place=new String[jsonArray.length()];
						//Log.i("image length",""+images_place.length);
	    				for(int i=0;i<jsonArray.length();i++){
	    					JSONObject jsonObject=jsonArray.getJSONObject(i);
	    					String src=ConnectServer.ADDRESS_LOCALHOST+""+jsonObject.getString("src");
	    					//Log.i("src: ",""+src);
	    					images_place[i]=src;
	    				}
	    				galleryImageAdapter.loadListImage(images_place);
    			        image_gallery=((ImageView)rootView.findViewById(R.id.gallery_item_place));
    			        gallery_place=((Gallery)rootView.findViewById(R.id.gallery_place));
    			        gallery_place.setSpacing(2);
    			        gallery_place.setAdapter(galleryImageAdapter);
    			        //gallery_place.setSelection(2);
    			        gallery_place.setOnItemClickListener(new OnItemClickListener() {
    						@Override
    						public void onItemClick(AdapterView<?> arg0, View arg1, int position,
    								long arg3) {
    							// TODO Auto-generated method stub
    							//image_gallery.setBackgroundResource(galleryImageAdapter.mImageIds[position]);
    							ImageLoader imageLoader=new ImageLoader(getActivity().getApplicationContext());
    							imageLoader.DisplayImage(galleryImageAdapter.images_place[position], image_gallery);
    							
    						}
    			        	
    					});
					} catch (JSONException e) {
						e.printStackTrace();
					}
    			}
    		}
    	}.execute(path);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
    	// TODO Auto-generated method stub
    	super.onSaveInstanceState(outState);
    	outState.putString("json",json);
    	outState.putString("id_place",id_place);
    }
}
