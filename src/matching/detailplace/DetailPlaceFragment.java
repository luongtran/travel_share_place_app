package matching.detailplace;

import java.util.ArrayList;

import matching.classmain.CommentPlace_Addapter;
import matching.classmain.CommentsPlace;
import matching.classmain.RequestHTTPGet;
import matching.functioninterface.ConnectServer;
import matching.loadimage.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import project.matching.MainActivity;

import com.example.matching.R;

import android.R.integer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class DetailPlaceFragment extends Fragment implements OnClickListener,OnRatingBarChangeListener{
	String json;
	String result_request;
	int id_place;
	ImageView img_place;
	TextView tv_address;
	TextView tv_mobile;
	ImageButton btn_favorite;
	ImageButton btn_like;
	TextView tv_name;
	RatingBar ratingBar;
	TextView tv_numLike;
	ListView lv_comments;
	private int numLoadMore=2;
	private Button btn_loadMore;
	private CommentsPlace commentsPlace;
	
	public void transfer_data(String json,String id_place) {
		// TODO Auto-generated constructor stub
		this.json=json;
		this.id_place=Integer.parseInt(id_place);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_place, container, false);
        img_place=((ImageView)rootView.findViewById(R.id.detail_place_avarta));
        tv_name=((TextView)rootView.findViewById(R.id.detail_place_name_id));
        tv_address=((TextView)rootView.findViewById(R.id.detail_place_address));
        tv_mobile=((TextView)rootView.findViewById(R.id.detail_place_mobile));
        //favorite place
        btn_favorite=((ImageButton)rootView.findViewById(R.id.btn_favorite_place));
        btn_favorite.setOnClickListener(this);
        if(checkLogin()){
        	requestCheckFavorite();
        }
        // btn like
        btn_like=((ImageButton)rootView.findViewById(R.id.btn_like_place));
        btn_like.setOnClickListener(this);
        tv_numLike=(TextView)rootView.findViewById(R.id.tv_numlike_place);
        
        
        lv_comments=(ListView)rootView.findViewById(R.id.lv_comments);
        btn_loadMore=new Button(getActivity());
        btn_loadMore.setId(-1);
        btn_loadMore.setText("Load More");
        btn_loadMore.setOnClickListener(this);
    	ratingBar=(RatingBar)rootView.findViewById(R.id.detail_place_rating);
    	ratingBar.setOnRatingBarChangeListener(this);
    	
    	lv_comments.addFooterView(btn_loadMore);
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	if(savedInstanceState!=null){
    		json=savedInstanceState.getString("json");
    		id_place=savedInstanceState.getInt("id_place");
    	}
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
					tv_numLike.setText(jsonObject.getString("p_like"));
					imageLoader.DisplayImage(url_img,img_place);
					break;
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        load_star_rating(id_place);
        load_list_comment();
        
    }
    private void load_star_rating(int place_id){
    	String path=ConnectServer.ADDRESS_LOCALHOST+"/places/getPlaceFromID?place_id="+place_id;
    	new RequestHTTPGet(){
    		protected void onPostExecute(String result) {
    			try{
    				JSONObject jsonObject=new JSONObject(result);
    				ratingBar.setRating((float)(Float.parseFloat(jsonObject.getString("rate"))));
    			}catch(JSONException e){
    				e.printStackTrace();
    			}
    		};
    	}.execute(path);
    	
    }
    //
    public void requestCheckFavorite(){
    	String path=ConnectServer.ADDRESS_LOCALHOST+"/placefavorites/checkExistsFavorite?user_id="+MainActivity.id_user+"&place_id="+id_place;
    	new RequestHTTPGet(){
    		protected void onPostExecute(String result) {
    			if(result.equals("1")){
    				btn_favorite.setBackgroundResource(R.drawable.ic_favorite);
    			}else
    			{
    				btn_favorite.setBackgroundResource(R.drawable.ic_add_favorite);
    			}
    		};
    	}.execute(path);
    }
    //
    public void load_list_comment(){
        //listview comment
        String path=ConnectServer.ADDRESS_LOCALHOST+"/PlaceComments/getCommentByIdPlace?id_place="+id_place+"&limit="+numLoadMore;
    	//Log.i("path",""+path);
    	new RequestHTTPGet(){
    		protected void onPostExecute(String result) {
    			//Log.i("comments",""+result);
    			if(!result.equals("2")){
    			result_request=result;
    			//load_list_comment(listComment(result,numLoadMore));
    			CommentPlace_Addapter adapter=new CommentPlace_Addapter(getActivity(),android.R.layout.simple_list_item_1, listComment(result));
    	    	//ArrayList<String> list1=new ArrayList<String>();
    	    	//list1.add("comment1");
    	    	//list1.add("comment2");
    	    	//ArrayAdapter<String> adapter_string=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list1);
    	    	
    	    	lv_comments.setAdapter(adapter);
    	    	Log.i("get count",""+adapter.getCount());
    	    	if(adapter.getCount() > 2){
    	            View item = adapter.getView(0, null, lv_comments);
    	            item.measure(0, 0);         
    	            
//    	            Log.i("get heigh",""+item.getMeasuredHeight());
    	           // ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, (int) (5.5 * item.getMeasuredHeight()));
    	            
    	    		LayoutParams lp = (LayoutParams) lv_comments.getLayoutParams();
    	    	    lp.height = adapter.getCount()*80;
    	    	    Log.i("height",""+item.getMeasuredHeight());
    	    	    lv_comments.setLayoutParams(lp);
    	            // lv_comments.setLayoutParams(params);
    	    }
    			}else
    			{
    				btn_loadMore.setVisibility(View.INVISIBLE);
    			}
    		};
    	}.execute(path);
    	
    }
    private ArrayList<CommentsPlace> listComment(String result){
    	ArrayList<CommentsPlace> list=new ArrayList<CommentsPlace>();
    	try {
			JSONArray jsonArray=new JSONArray(result);
			
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				int id=Integer.parseInt(jsonObject.getString("id"));
				int place_id=Integer.parseInt(jsonObject.getString("place_id"));
				int user_id=Integer.parseInt(jsonObject.getString("user_id"));
				String message=jsonObject.getString("message");
				String comment_time=jsonObject.getString("comment_time");
				String email=jsonObject.getString("email");
				String avarta=jsonObject.getString("avarta");
				commentsPlace=new CommentsPlace(id, place_id, user_id, message, comment_time,email,avarta);
				list.add(commentsPlace);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return list;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
    	// TODO Auto-generated method stub
    	super.onSaveInstanceState(outState);
    	outState.putString("json",json);
    	outState.putInt("id_place",id_place);
    }
    
	@Override
	public void onClick(View v) {
		// btn favorite
		if(v.getId()==R.id.btn_favorite_place){
			if(checkLogin()){
				requestFavorite();
			}else
				getToast("you need to login");
			
		}
		if(v.getId()==-1){
			numLoadMore++;
			load_list_comment();
		}
		if(v.getId()==R.id.btn_like_place){
			Log.i("like","place");
			if(checkLogin()){
				requestLikePlace();
			}else{
				getToast("You need to login");
			}
		}
		
	}
	// request like place
	public void requestLikePlace(){
		
		String path=ConnectServer.ADDRESS_LOCALHOST+"/likeplaces/storeLikePlace?user_id="+MainActivity.id_user+"&place_id="+id_place;
		new RequestHTTPGet(){
			protected void onPostExecute(String result) {
				tv_numLike.setText(""+result);
			};
		}.execute(path);
	}
	//check login user
	private boolean checkLogin(){
		if(MainActivity.id_user==0)
			return false;
		return true;
	}
	
	//request favorite
	private void requestFavorite(){
		String path=ConnectServer.ADDRESS_LOCALHOST+"/placefavorites/storeFavorite?user_id="+MainActivity.id_user+"&place_id="+id_place;
		new RequestHTTPGet(){
			protected void onPostExecute(String result) {
				if(result.equals("1")){
					btn_favorite.setBackgroundResource(R.drawable.ic_favorite); 
				}
				if(result.equals("2")){
					btn_favorite.setBackgroundResource(R.drawable.ic_add_favorite);
				}
				
			};
		}.execute(path);
	}
	
	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating,
			boolean fromUser) {
		// TODO Auto-generated method stub
		//Log.i("on rating","ok");
		// not logged
		if(MainActivity.id_user==0){
			getToast("not logged");
		}else{
			String path=ConnectServer.ADDRESS_LOCALHOST+"/rates/rating?user_id="+MainActivity.id_user+"&num_rate="+rating+"&place_id="+id_place;
			new RequestHTTPGet(){
			}.execute(path);
			
		}
		
		
	}
	private void getToast(String message){
		Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
	}
}
