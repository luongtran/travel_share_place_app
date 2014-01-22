package matching.classmain;

public class ActivityUser {
	private int id,user_id,event_id,category_id,activity_id,numLike,numComment;
	private String cate_name,event_name,activity_time,content,user_name,avarta;
	
	public ActivityUser(int id,int user_id,int category_id,int activity_id,String cate_name,
			String event_name,String activity_time,String content,String user_name,String avarta,int numLike,int numComment) {
		this.id=id;
		this.user_id=user_id;
		this.category_id=category_id;
		this.activity_id=activity_id;
		this.cate_name=cate_name;
		this.activity_time=activity_time;
		this.content=content;
		this.user_name=user_name;
		this.avarta=avarta;
		this.numLike=numLike;
		this.numComment=numComment;
	}
	public int getNumComment() {
		return numComment;
	}
	public int getNumLike() {
		return numLike;
	}
	
	public int getId() {
		return id;
	}
	public int getUser_id() {
		return user_id;
	}
	public int getActivity_id() {
		return activity_id;
	}
	public String getActivity_time() {
		return activity_time;
	}
	public String getAvarta() {
		return avarta;
	}
	public String getCate_name() {
		return cate_name;
	}
	public int getCategory_id() {
		return category_id;
	}
	public String getContent() {
		return content;
	}
	public int getEvent_id() {
		return event_id;
	}
	public String getEvent_name() {
		return event_name;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setActivity_id(int activity_id) {
		this.activity_id = activity_id;
	}
	public void setActivity_time(String activity_time) {
		this.activity_time = activity_time;
	}
	public void setAvarta(String avarta) {
		this.avarta = avarta;
	}
	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}
	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public void setNumComment(int numComment) {
		this.numComment = numComment;
	}
	public void setNumLike(int numLike) {
		this.numLike = numLike;
	}
}
