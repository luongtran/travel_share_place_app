package matching.classmain;

public class District {

	/**
	 * @param args
	 */
	private int id,city_id;
	private String district_name;
	public District(String city_name) {
		// TODO Auto-generated constructor stub
		this.district_name=city_name;
	}
	public District(int id,String city_name, int city_id) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.district_name=city_name;
	}
	public District(int id,String city_name) {
		// TODO Auto-generated constructor stub
		this.id=id;
		this.district_name=city_name;
	}
	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}
	public void setDistrict_name(String district_name) {
		this.district_name = district_name;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCity_id() {
		return city_id;
	}
	public String getDistrict_name() {
		return district_name;
	}
	public int getId() {
		return id;
	}

}
