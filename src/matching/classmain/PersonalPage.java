package matching.classmain;

import android.Manifest.permission;
import android.widget.LinearLayout;

public class PersonalPage {
	private String content,email,user_name,time,avarta,image_cover;
	private int status_id,user_id,num_like,num_comment;
	public PersonalPage() {
		// TODO Auto-generated constructor stub
	}
	public PersonalPage(int status_id,int user_id,String user_name,String time,String avarta,String content,String image_cover,int num_like,int num_comment) {
		this.status_id=status_id;
		this.user_id=user_id;
		this.content=content;
		this.user_name=user_name;
		this.time=time;
		this.avarta=avarta;
		this.image_cover=image_cover;
		this.num_comment=num_comment;
		this.num_like=num_like;
		// TODO Auto-generated constructor stub
	}
	public PersonalPage(int id_user,String user_name,String avarta,String image_cover){
		this.user_name=user_name;
		this.avarta=avarta;
		this.image_cover=image_cover;
		this.user_id=id_user;
	}
	public int getNum_comment() {
		return num_comment;
	}
	public int getNum_like() {
		return num_like;
	}
	public String getImage_cover() {
		return image_cover;
	}
	
	public String getAvarta() {
		return avarta;
	}
	public String getContent() {
		return content;
	}
	public String getEmail() {
		return email;
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
	public void setContent(String content) {
		this.content = content;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public void setImage_cover(String image_cover) {
		this.image_cover = image_cover;
	}
	public void setNum_comment(int num_comment) {
		this.num_comment = num_comment;
	}
	public void setNum_like(int num_like) {
		this.num_like = num_like;
	}
}
