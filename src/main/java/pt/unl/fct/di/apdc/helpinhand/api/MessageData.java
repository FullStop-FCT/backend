package pt.unl.fct.di.apdc.helpinhand.api;

public class MessageData {
	
	
	private String author;
	private String message;
	private String receiver;
	private String date;
	private String activityID;
	private String image;
	private long msgID;
	
	
	public MessageData() {
		
	}
	
	public MessageData(String message, String image) {
//		this.author=author;
		this.message=message;
//		this.date=date;
		this.setImage(image);
	}
	
	public MessageData(String author, String message, String image, String receiver) {
		this.author=author;
		this.message=message;
		this.date = date;
		this.receiver=receiver;
		this.setImage("");
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getActivityID() {
		return activityID;
	}

	public void setActivityID(String activityID) {
		this.activityID = activityID;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public long getMsgID() {
		return msgID;
	}

	public void setMsgID(long msgID) {
		this.msgID = msgID;
	}
	
}
