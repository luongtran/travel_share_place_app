package matching.classmain;

public class Places {
	private int id,like,view,tpalce_id,rate,district_id;
	private String place_name,address,longitude,latitude,website,mobile,description,avarta,time;
	/**
	 * @param args
	 */
	// function construct
	public Places(int id,String placeName,String address,String avarta,String website,int tpalce_id,String time) {
		this.id=id;
		this.place_name=placeName;
		this.address=address;
		this.avarta=avarta;
		this.website=website;
		this.tpalce_id=tpalce_id;
		this.time=time;
	}
	
	public Places(int id,String placeName,String address,String avarta) {
		this.place_name=placeName;
		this.address=address;
		this.id=id;
		this.avarta=avarta;
		
	}
	//functions set
	public void setId(int id) {
		this.id = id;
	}
	public void setLike(int like) {
		this.like = like;
	}
	public void setView(int view) {
		this.view = view;
	}
	public void setTpalce_id(int tpalce_id) {
		this.tpalce_id = tpalce_id;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public void setDistrict_id(int district_id) {
		this.district_id = district_id;
	}
	public void setPlace_name(String place_name) {
		this.place_name = place_name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setAvarta(String avarta) {
		this.avarta = avarta;
	}
	public void setTime(String time) {
		this.time = time;
	}
	//functions get
	public int getId() {
		return id;
	}
	public int getLike() {
		return like;
	}
	public int getView() {
		return view;
	}
	public int getTpalce_id() {
		return tpalce_id;
	}
	public int getRate() {
		return rate;
	}
	public int getDistrict_id() {
		return district_id;
	}
	public String getPlace_name() {
		return place_name;
	}
	public String getAddress() {
		return address;
	}
	public String getLongitude() {
		return longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public String getWebsite() {
		return website;
	}
	public String getMobile() {
		return mobile;
	}
	public String getDescription() {
		return description;
	}
	public String getAvarta() {
		return avarta;
	}
	public String getTime() {
		return time;
	}
	
	/*private int id,like,view,tpalce_id,rate,district_id;
	private String place_name,address,longitude,latitude,website,mobile,description,avarta,time;*/
}








