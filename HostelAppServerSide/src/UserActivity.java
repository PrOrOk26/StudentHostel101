import java.util.Date;

public class UserActivity {

	    private String date;
	    private String performerLogin;
	    private String performerName;
	    private String performerSurname;

	    private final int TYPE;

	    public UserActivity(String date, String performerLogin, String performerName, String performerSurname, int TYPE) {
	        this.date = date;
	        this.performerLogin = performerLogin;
	        this.performerName = performerName;
	        this.performerSurname = performerSurname;
	        this.TYPE = TYPE;
	    }

	    public UserActivity(int TYPE) {
	        this.date = new String();
	        this.performerLogin = new String();
	        this.performerName = new String();
	        this.performerSurname = new String();
	        this.TYPE = TYPE;
	    }

	    public String getDate() {
	        return date;
	    }

	    public void setDate(String date) {
	        this.date = date;
	    }

	    public String getPerformerLogin() {
	        return performerLogin;
	    }

	    public void setPerformerLogin(String performerLogin) {
	        this.performerLogin = performerLogin;
	    }

	    public String getPerformerName() {
	        return performerName;
	    }

	    public void setPerformerName(String performerName) {
	        this.performerName = performerName;
	    }

	    public String getPerformerSurname() {
	        return performerSurname;
	    }

	    public void setPerformerSurname(String performerSurname) {
	        this.performerSurname = performerSurname;
	    }

	    public int getTYPE() {
	        return TYPE;
	    }
}
