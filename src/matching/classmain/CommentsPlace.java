package matching.classmain;

public class CommentsPlace {
	private int id,place_id,user_id,c_status;
	private String message,comment_time,email,avarta;
	
	//function construct
	public CommentsPlace() {
		// TODO Auto-generated constructor stub
		
	}
	public CommentsPlace(int id,int place_id,int user_id,String message,String comment_time,String email,String avarta){
		this.id=id;
		this.place_id=place_id;
		this.user_id=user_id;
		this.message=message;
		this.comment_time=comment_time;
		this.email=email;
		this.avarta=avarta;
	}
	//set get
	public int getC_status() {
		return c_status;
	}
	public String getComment_time() {
		return comment_time;
	}
	public int getId() {
		return id;
	}
	public String getMessage() {
		return message;
	}
	public int getPlace_id() {
		return place_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public String getEmail() {
		return email;
	}
	public String getAvarta() {
		return avarta;
	}
	//set
	public void setC_status(int c_status) {
		this.c_status = c_status;
	}
	public void setComment_time(String comment_time) {
		this.comment_time = comment_time;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setPlace_id(int place_id) {
		this.place_id = place_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAvarta(String avarta) {
		this.avarta = avarta;
	}
}








