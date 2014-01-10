package matching.detailplace;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import project.matching.DetailPlaceActivity;
import project.matching.MainActivity;

import matching.classmain.CommentPlace_Addapter;
import matching.classmain.CommentsPlace;
import matching.classmain.RequestHTTPGet;
import matching.functioninterface.ConnectServer;

import com.example.matching.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CommentPlaceFragment extends Fragment{
	String json,id_place;
	ListView lv_comment;
	TextView tv_comment;
	CommentsPlace commentsPlace;
	View rootView;
	public void transfer_data(String json,String id_place) {
		// TODO Auto-generated constructor stub
		this.json=json;
		this.id_place=id_place;
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	//int i=getArguments().getInt(ARG_SECTION_NUMBER);
    	//Log.i("i2=",""+json+id_place);
    	//Log.i("comments",""+json);
        rootView = inflater.inflate(R.layout.fragment_comments_place, container, false);
        
        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onActivityCreated(savedInstanceState);
    	lv_comment=((ListView)rootView.findViewById(R.id.lv_comments_place));
    	if(savedInstanceState!=null){
    		json=savedInstanceState.getString("json");
    		id_place=savedInstanceState.getString("id_place");
    	}
    	
    	String path=ConnectServer.ADDRESS_LOCALHOST+"/PlaceComments/getCommentByIdPlace?id_place="+id_place;
    	//Log.i("path",""+path);
    	new RequestHTTPGet(){
    		protected void onPostExecute(String result) {
    			//Log.i("comments",""+result);
    			load_list_comment(listComment(result));
    			
    		};
    	}.execute(path);
    	 
    	
    }
    
    private void load_list_comment(ArrayList<CommentsPlace> list){
    	CommentPlace_Addapter adapter=new CommentPlace_Addapter(getActivity(),android.R.layout.simple_list_item_1, list);
    	//ArrayList<String> list1=new ArrayList<String>();
    	//list1.add("comment1");
    	//list1.add("comment2");
    	//ArrayAdapter<String> adapter_string=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, list1);
    	lv_comment.setAdapter(adapter);
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
    	outState.putString("id_place", id_place);
    }
    public void refresh(){
    	Log.i("refesh","abc");
    	//getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_comment_container, new CommentPlaceFragment()).commit();
    	
    	
    }
    
}
