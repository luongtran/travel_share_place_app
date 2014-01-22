package matching.classmain;

public class User {
	private int id,relationship_id,education_id,district_id,sex_id,decentralization_id;
	private String email,password,preference,job,address,motto,workplace,birth,user_name,date_register,avarta;
	
	public User() {
		// TODO Auto-generated constructor stub
	}
	public User(int id,String user_name,String email,int district_id,int education_id,
			String birth,String date_register,String avarta,String address,int sex_id){
		this.id=id;
		this.user_name=user_name;
		this.email=email;
		this.district_id=district_id;
		this.education_id=education_id;
		this.birth=birth;
		this.date_register=date_register;
		this.avarta=avarta;
		this.address=address;
		this.sex_id=sex_id;
	}
	public User(int id,String user_name,String email,String avarta,String address){
		this.address=address;
		this.id=id;
		this.user_name=user_name;
		this.avarta=avarta;
		this.email=email;
	}
	//get
	public String getAddress() {
		return address;
	}
	public String getAvarta() {
		return avarta;
	}
	public String getBirth() {
		return birth;
	}
	public String getDate_register() {
		return date_register;
	}
	public int getDecentralization_id() {
		return decentralization_id;
	}
	public int getDistrict_id() {
		return district_id;
	}
	public int getEducation_id() {
		return education_id;
	}
	public String getEmail() {
		return email;
	}
	public int getId() {
		return id;
	}
	public String getJob() {
		return job;
	}
	public String getMotto() {
		return motto;
	}
	public String getPassword() {
		return password;
	}
	public String getPreference() {
		return preference;
	}
	public int getRelationship_id() {
		return relationship_id;
	}
	public int getSex_id() {
		return sex_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public String getWorkplace() {
		return workplace;
	}
	//set
	public void setAddress(String address) {
		this.address = address;
	}
	public void setAvarta(String avarta) {
		this.avarta = avarta;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public void setDate_register(String date_register) {
		this.date_register = date_register;
	}
	public void setDecentralization_id(int decentralization_id) {
		this.decentralization_id = decentralization_id;
	}
	public void setDistrict_id(int district_id) {
		this.district_id = district_id;
	}
	public void setEducation_id(int education_id) {
		this.education_id = education_id;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public void setMotto(String motto) {
		this.motto = motto;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPreference(String preference) {
		this.preference = preference;
	}
	public void setRelationship_id(int relationship_id) {
		this.relationship_id = relationship_id;
	}
	public void setSex_id(int sex_id) {
		this.sex_id = sex_id;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}
	
}
