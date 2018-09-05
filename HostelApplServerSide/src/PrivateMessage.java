import java.util.Calendar;
import java.util.Date;

public class PrivateMessage{
	
	private String message;
    private User sender;
    private String date;
    private int conversationId;

    public PrivateMessage(String message,User sender,String date,int conversationId) {
        this.message = message;
        this.sender = sender;
        this.date= date;
        this.conversationId=conversationId;
    }
    
    public PrivateMessage() {
        this.message = new String();
        this.sender = new User();
        this.date = new String();
        this.conversationId=0;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }
	

}
