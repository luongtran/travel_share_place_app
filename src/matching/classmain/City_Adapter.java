package matching.classmain;

import java.util.ArrayList;
import java.util.List;

import com.example.matching.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class City_Adapter extends ArrayAdapter<City>{

	

	/**
	 * @param args
	 */
	
	private Activity context;
	ArrayList<City> data=null;
	public City_Adapter(Activity context, int resource,ArrayList<City> objects) {
		super(context, resource, objects);
		this.context=context;
		this.data=objects;
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
            row = inflater.inflate(R.layout.spinners, parent, false);
        }
        City item = data.get(position);
        String test = item.getCity_name();
       // Log.d("test ", test);

        if(item != null)
        {   
            TextView cityname = (TextView) row.findViewById(R.id.item_value);
            if(cityname != null){
             cityname.setText(item.getCity_name());
             //Log.d("find me ", cityname.toString());
            }

        }

        return row;
    }
	
   
}
