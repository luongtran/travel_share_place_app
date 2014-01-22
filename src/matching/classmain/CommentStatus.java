package matching.classmain;

public class CommentStatus {

	int status_id,user_id,id;
	String avarta,user_name,message,time;
	public CommentStatus() {
		// TODO Auto-generated constructor stub
	}
	public CommentStatus(int id,int status_id,int user_id,String avarta,String user_name,String message,String time){
		this.status_id=status_id;
		this.user_id=user_id;
		this.avarta=avarta;
		this.user_name=user_name;
		this.message=message;
		this.time=time;
		this.id=id;
	}
	public int getId() {
		return id;
	}
	public String getAvarta() {
		return avarta;
	}
	public String getMessage() {
		return message;
	}
	public int getStatus_id() {
		return status_id;
	}
	public String getTime() {
		return time;
	}
	public int getUser_id() {
		return user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setAvarta(String avarta) {
		this.avarta = avarta;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public void setId(int id) {
		this.id = id;
	}
}
