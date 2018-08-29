
public class XMLConstants {
	
	 public static final int RETURN=-1;
	
	 public static final int SETGARBAGEMESSAGE=0;
	 
     public static final int SETBREADMESSAGE=1;
     
     public static final int SETVALIDATIONMESSAGE=2;
     
     public static final int SETREGISTRATIONMESSAGE=3;
     
     public static final int SETGROUPVALIDATIONMESSAGE=4;
     
     public static final int SETGROUPCREATIONMESSAGE=5;
     
     public static final int SETGROUPINVITATIONMESSAGE=6;
     
     public static final int SETINSERTGARBAGEMESSAGE=8;
     
     public static final int SETINSERTBREADMESSAGE=9;
     
     public static final int SETGETBREADHISTORYMESSAGEALL=10;
     
     public static final int SETGETGARBAGEHISTORYMESSAGEALL=11;
     
     public static final int SETGROUPUSERQUITMESSAGE = 12;
     
     public static final int SETSENDCHATMESSAGE=13;

     public static final int SETRETRIEVEALLCHATMESSAGE=14;
     
     public static final int SETRETRIEVERESENTCHATMESSAGE=15;
     
     public static final int SETGETBREADHISTORYMESSAGERECENT=16;
     
     public static final int SETGETGARBAGEHISTORYMESSAGERECENT=17;    

     public static final int SETGETALLCONVERSATIONMESSAGES=18;

     public static final int SETGETRECENTCONVERSATIONMESSAGES=19;

     public static final int SETINSERTCONVERSATIONMESSAGE=20;
     
     public static final int SETGETALLUSERSINFO=7;
     
     public static final String TAGS_GARBAGE="garbage";

     public static final String TAGS_USER="user";

     public static final String TAGS_NAME="name";

     public static final String TAGS_SURNAME="surname";

     public static final String TAGS_DATE="date";

     public static final String TAGS_BREAD="bread";
     
     public static final String TAGS_LOGIN="login";
     
     public static final String TAGS_PASSWORD="password";
     
     public static final String TAGS_GROUP="group";
     
     public static final String TAGS_LOGIN_VALIDATION="validation";
     
     public static final String TAGS_USER_REGISTRATION="registration";
     
     public static final String TAGS_LOGIN_VALIDATION_RESPONCE_ALLOWED="login successfull!";
     
     public static final String TAGS_LOGIN_VALIDATION_RESPONCE_FAILED="login failed!";
     
     public static final String TAGS_USER_REGISTRATION_SUCCESS="registration successful!";
     
     public static final String TAGS_USER_REGISTRATION_FAILED="registration failed!";
     
     public static final String TAGS_GROUP_VALIDATION="groupvalidation";
     
     public static final String TAGS_GROUP_VALIDATION_FAIL="You don't participate in any group yet.Try to make one or get invitation!";
     
     public static final String TAGS_GROUP_CREATION="groupcreation";
     
     public static final String TAGS_GROUP_CREATION_FAIL="failed to create a group!";
     
     public static final String TAGS_GROUP_CREATION_ALREADY_EXISTS="group name is already in use!";       
     
     public static final String TAGS_GROUP_ADD_USER="groupinsert";

     public static final String TAGS_GROUP_ADD_USER_SUCCESS="User was successfully added!";

     public static final String TAGS_GROUP_ADD_USER_FAIL="User addition fail!";

     public static final String TAGS_GROUP_ADD_USER_ALREADY_IN_GROUP="User is already in group!";

     public static final String TAGS_GROUP_ADD_USER_ALREADY_IN_ANOTHER_GROUP="User is already in another group!";
     
     public static final String TAGS_GROUP_USER_QUIT="groupquit";

     public static final String TAGS_GROUP_USER_QUIT_SUCCESS="You successfully left the group!";

     public static final String TAGS_GROUP_USER_QUIT_FAILURE="Quit failure!";
     
     public static final String TAGS_SEND_MAIN_CHAT_MESSAGE="sendchatmessage";

     public static final String TAGS_RECEIVE_MAIN_CHAT_MESSAGE="receivechatmessage";

     public static final String TAGS_SEND_MAIN_CHAT_SUCCESS="message successfully delivered";

     public static final String TAGS_TEXT = "text";

	 public static final String TAGS_SEND_MAIN_CHAT_FAIL = "message deliver fail";
	 
	 public static final String TAGS_RETRIEVE_ALL_CHAT="retrieveallchatmessages";
	    
	 public static final String TAGS_RETRIEVE_RESENT_CHAT_MESSAGES="retrieveresentchatmessages";
	 
	 public static final String TAGS_REQUEST_GARBAGE_HISTORY="retrievegarbagehistory";
	    
	 public static final String TAGS_REQUEST_BREAD_HISTORY="retrievebreadhistory";
	    
     public static final int BREAD_GARBAGE_REQUEST_TYPE_RECENT=1;

     public static final int BREAD_GARBAGE_REQUEST_TYPE_ALL=2;
	 
	 public static final String TAGS_INSERT_BREAD="insertbread";
	    
	 public static final String TAGS_INSERT_GARBAGE="insertgarbage";
	 
	 public static final String TAGS_REQUEST_TYPE="requesttype";
	 
	 public static final int ACTIVITY_BREAD_TYPE=1;

	 public static final int ACTIVITY_GARBAGE_TYPE=2;
	 
	 public static final String INSERT_BREAD_GARBAGE_SUCCESS="insertion successfully performed!";
	 
	 public static final String TAGS_GET_ALL_USERS_INFO="getallusersinfo";

	 public static final String TAGS_CONVERSATION="conversation";
	 
	 public static final String TAGS_GET_CONVERSATION_MESSAGES="getallconversationmessages";

	 public static final String TAGS_INSERT_MESSAGE_TO_CONVERSATION="insertmessagetoconversation";

	 public static final String TAGS_GET_RECENT_CONVERSATION_MESSAGES="getrecentconversationmessages";
	
                         
}
