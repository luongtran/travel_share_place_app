package project.matching;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import matching.classmain.City;
import matching.classmain.City_Adapter;
import matching.classmain.District;
import matching.classmain.District_Adapter;
import matching.classmain.RequestHTTPGet;
import matching.classmain.RequestHTTPPost_Register;
import matching.functioninterface.ConnectServer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.matching.R;

import android.os.Bundle;
import android.os.HandlerThread;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	private EditText ed_email,ed_password,ed_address,ed_birth,ed_name;
	private Spinner sp_city,sp_district,sp_sex;
	private int position_city_id,position_district_id,position_sex_id=0;
	private String path,email,password,address,birth;
	private ArrayList<City> list_city;
	private ArrayList<District> list_district;
	private int year;
	private int month;
	private int day;
 
	static final int DATE_DIALOG_ID = 999;
	// function begin activity
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		//Log.i("a","c");
		path=ConnectServer.ADDRESS_LOCALHOST+"/cities/getallcity";
		new RequestHTTPGet(){
			@Override
			protected void onPostExecute(String result) {
				ArrayList<City> list=new ArrayList<City>();
				init();
				list=getListCity(result);
				list_city=list;
				Log.i("list_city",""+list.size());
				load_spinner_city(list);
				super.onPostExecute(result);
			}
		}.execute(path);
	}
	
	//function initialization type views of activity
	public void init(){
		ed_email=(EditText)findViewById(R.id.edit_email);
		ed_password=(EditText)findViewById(R.id.edit_password);
		ed_address=(EditText)findViewById(R.id.edit_address);
		ed_birth=(EditText)findViewById(R.id.edit_birth);
		sp_city=(Spinner)findViewById(R.id.sp_city);
		ed_name=(EditText)findViewById(R.id.register_name);
		ed_email.setText("");
		ed_address.setText("");
		ed_birth.setText("");
		ed_email.setText("");
		ed_name.setText("");
		
		setCurrentDateOnView();//get date current
		sp_city.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Log.i("position",""+(position+1));
				position_city_id=position;
				request_district(position+1);
				
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		sp_district=(Spinner)findViewById(R.id.sp_district);
		sp_district.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View viewParent,
					int position, long arg3) {
				// TODO Auto-generated method stub
				position_district_id=position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		sp_sex=(Spinner)findViewById(R.id.sp_sex);
		load_spinner_sex();
		sp_sex.setOnItemSelectedListener(new onItemSelectListener_spsex());
		path="";
		list_city=new ArrayList<City>();
		
	}
	private void load_spinner_sex(){
		String arr_sex[]={"male","female"};
		ArrayAdapter<String> sex_Adapter=new ArrayAdapter(this, android.R.layout.simple_spinner_item,arr_sex);
		sex_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_sex.setAdapter(sex_Adapter);
	}
	
	private ArrayList getListCity(String result){
		ArrayList<City> list=new ArrayList<City>();
		City city;
		//Log.i("abc",""+result);
		try{
			JSONArray jsonArray=new JSONArray(result);
    	    //Log.i("result len",""+jsonArray.length());
    	    //array jsonObject
    	    //JSONObject jsonObject2=jsonArray.getJSONObject(1);
    	    
    	    //Log.i("city_name: ","ad");
			
    	    for(int i=0;i<jsonArray.length();i++){
    	    	JSONObject jsonObject=jsonArray.getJSONObject(i);
    	    	city=new City(Integer.parseInt(jsonObject.getString("id")),jsonObject.getString("city_name"));
    	    	list.add(city);
    	    }
    	   // Log.i("city_size: ",""+list.size());
		}catch(Exception e){
			
		}
		return list;
	} 
	private ArrayList getListDistrict(String result){
		ArrayList<District> list=new ArrayList<District>();
		District district;
//		Log.i("abc",""+result);
		try{
			JSONArray jsonArray=new JSONArray(result);
			
    	    for(int i=0;i<jsonArray.length();i++){
    	    	JSONObject jsonObject=jsonArray.getJSONObject(i);
    	    	district=new District(Integer.parseInt(jsonObject.getString("id")),jsonObject.getString("district_name"));
    	    	
    	    	list.add(district);
    	    }
    	   // Log.i("city_size: ",""+list.size());
		}catch(Exception e){
			
		}
		return list;
	} 
	// show menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.matching, menu);
		return true;
	}
	// show messege of toast.
	public void getToast(String messege){
		Toast.makeText(getApplicationContext(),""+messege, Toast.LENGTH_SHORT).show();
	}
	// get string of editview
	public String getStringEditText(EditText editText){
		return editText.getText().toString();
	}
	
	// load city into spinner city
	private void load_spinner_city(ArrayList<City> list){
		City_Adapter cityAdapter=new City_Adapter(this, android.R.layout.simple_spinner_item,list);
		cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_city.setAdapter(cityAdapter);
	}
	
	// load city into spinner district
	private void load_spinner_district(ArrayList<District> list){
		Log.i("list_dis",""+list.size());
		District_Adapter district_Adapter=new District_Adapter(this, android.R.layout.simple_spinner_item,list);
		district_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_district.setAdapter(district_Adapter);
		/*District dis=new District("lien chieu");
		list.add(dis);
		
		District_Adapter cityAdapter=new District_Adapter(this, android.R.layout.simple_spinner_item,list);
		cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_district.setAdapter(cityAdapter);*/
	}
	
	//onclick button register
	public void on_Register(View v){
		//Log.i("id_city",""+list_city.get(position_city_id).getId());
		//Log.i("id_district",""+list_district.get(position_district_id).getId());
		//Log.i("id_sex", ""+position_sex_id);
		//ed_email.getText();
		if(getStringEditText(ed_email)=="" ||getStringEditText(ed_password).equals("")||getStringEditText(ed_address)==""
				||getStringEditText(ed_birth)==""){
			getToast("Enter your fields");
			
		}else{
			//Log.i("result: ","a"+_request(getStringEditText(ed_email),getStringEditText(ed_password),getStringEditText(ed_address),getStringEditText(ed_birth)));
			if(!isEmailValid(getStringEditText(ed_email))){
				getToast("format email not valid");
			}else{
				//getToast("format email ok");
				request_register();
				
			}
			//Log.i("result: ","a"+_request(ed_email.getText().toString(),ed_password.getText().toString()));
		}
	}
	private void request_register(){
		
		path=ConnectServer.ADDRESS_LOCALHOST+"/users/register";
		String arr_user[]={path,getStringEditText(ed_email),getStringEditText(ed_password),getStringEditText(ed_address),
							getStringEditText(ed_birth),""+list_district.get(position_district_id).getId(),""+(position_sex_id+1),
							getStringEditText(ed_name)
		};
		
		new RequestHTTPPost_Register(){
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				Log.i("request_register", ""+result);
				int result_request=Integer.parseInt(result);
				if(result_request==1){
					Intent intent=new Intent(getApplicationContext(),MainActivity.class);
					startActivity(intent);
				}else
				{
					if(result_request==0)
						getToast("Exists Email");
					else
						getToast("Register Fail");
				}
			}
		}.execute(arr_user);
	}
	private boolean isEmailValid(CharSequence email){
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}
	public void on_choosedate(View v){
		//setCurrentDateOnView();
		showDialog(DATE_DIALOG_ID); //999 is ID DIALOG DATE PICKER
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		//return super.onCreateDialog(id);
		switch (id) {
		case 999:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener, 
                         year, month,day);
		}
		return null;
		
	}
	public void setCurrentDateOnView() {
		 
		//tvDisplayDate = (TextView) findViewById(R.id.tvDate);
		//dpResult = (DatePicker) findViewById(R.id.dpResult);
 
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		//month=+1;
		ed_birth.setText(new StringBuilder()
		// Month is 0 based, just add 1
		.append(year).append("-").append(month+1).append("-")
		.append(day).append(" "));
	
	}
 
	private DatePickerDialog.OnDateSetListener datePickerListener 
    					= new DatePickerDialog.OnDateSetListener() {

// when dialog box is closed, below method will be called.
	public void onDateSet(DatePicker view, int selectedYear,
		int selectedMonth, int selectedDay) {
		year = selectedYear;
		month = selectedMonth;
		day = selectedDay;
		//month=+1;
		ed_birth.setText(new StringBuilder()
		// Month is 0 based, just add 1
		.append(year).append("-").append(month+1).append("-")
		.append(day).append(" "));
		// set selected date into textview
		//Log.i("dialog",""+ed_birth.getText().toString());
		// set selected date into datepicker also
		//dpResult.init(year, month, day, null);
	
	}
};

	private void request_district(int city_id){
		
		path=ConnectServer.ADDRESS_LOCALHOST+"/districts/getDistrictofcity?city_id="+city_id;
		//ArrayList<District> list=new ArrayList<District>();
		new RequestHTTPGet(){
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				//Log.i("district",""+result);
				list_district=getListDistrict(result);
				//Log.i("aaa",""+sp_district.getItemIdAtPosition(city_id));
				load_spinner_district(list_district);
				//Log.i("selected: ",""+sp_district.getSelectedItemPosition());
			}
		}.execute(path);
		/*ArrayList<District> list=new ArrayList<District>();
		District dis=new District("lien chieu");
		list.add(dis);
		
		District_Adapter cityAdapter=new District_Adapter(this, android.R.layout.simple_spinner_item,list);
		cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_district.setAdapter(cityAdapter);*/
	}
	
	/*@Override
	public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		if(selectedItemView.getId()==-1){
			Log.i("position",""+selectedItemView.getId());
		
			request_district(position);
		}else
			Log.i("position2",""+selectedItemView.getId());
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parentView) {
		// TODO Auto-generated method stub
		
	}*/
	
	private class onItemSelectListener_spsex implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Log.i("select","sex"+position);
			position_sex_id=position;
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}

