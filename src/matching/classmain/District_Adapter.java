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

public class District_Adapter extends ArrayAdapter<District>{

	/**
	 * @param args
	 */
	private Activity context;
	ArrayList<District> data=null;
	
	public District_Adapter(Activity context, int resource,ArrayList<District> objects) {
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
        District item = data.get(position);
        String test = item.getDistrict_name();
        //Log.d("test ", test);

        if(item != null)
        {   
            TextView districName = (TextView) row.findViewById(R.id.item_value);
            if(districName != null){
            	districName.setText(item.getDistrict_name());
             //Log.d("find me ", districName.toString());
            }

        }

        return row;
    }
	
   
}
