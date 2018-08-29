import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class User extends EntityDao{
	
	private String login;
    private String password;
    private String name;
    private String surname;
    private String group;
    
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public User(String name, String surname) {
		
		this.name = name;
		this.surname = surname;
	}

	public User(String login, String password, String name, String surname,String group) {
		
		this.login = login;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.group = group;
	}
	
	public User() {
		
		this.login =new String();
		this.password = new String();
		this.name = new String();
		this.surname = new String();
		this.group=new String();
	}
	
	public User(List<String> input)
	{
		Iterator<String> iterator=input.iterator();		
		this.name = iterator.next();
		this.surname = iterator.next();
		this.login = iterator.next();
		this.password = iterator.next();
		this.group = iterator.next();
	}
	
    public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}


}
