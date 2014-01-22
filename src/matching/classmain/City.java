package matching.classmain;

import com.example.matching.R;

import project.matching.LoginActivity;
import android.webkit.WebView.FindListener;
import android.widget.TextView;

public class City {

	private int id;
	private String city_name;
	public City(String city_name) {
		// TODO Auto-generated constructor stub
		this.city_name=city_name;
	}
	public City(int id,String city_name) {
		// TODO Auto-generated constructor stub
		//tvTextView=(TextView)login.findViewById(R.id.tv_address_place);
		this.id=id;
		this.city_name=city_name;
	}
	//set get
	public void setId(int id) {
		this.id = id;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	//get
	public int getId() {
		return id;
	}
	public String getCity_name() {
		return city_name;
	}
	

}
