import java.util.Date;

public class Message extends EntityDao{

	    private String message;
	    private User sender;
	    private String date;


	    public Message(String message, User sender,String date) {
	        this.message = message;
	        this.sender = sender;
	        this.date=date;
	    }
	    
	    public Message() {
	        this.message = new String();
	        this.sender = new User();
	        this.date=new String();
	    }
	    
	    public void setMessage(String message) {
			this.message = message;
		}

		public void setSender(User sender) {
			this.sender = sender;
		}

	    public String getMessage() {
	        return message;
	    }

	    public User getSender() {
	        return sender;
	    }

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

}
