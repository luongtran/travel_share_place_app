package matching.detailplace;

import com.example.matching.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapPlaceFragment extends Fragment{
	String json,id_place;
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
    	
        View rootView = inflater.inflate(R.layout.fragment_map_place, container, false);
        
        return rootView;
    }
}
