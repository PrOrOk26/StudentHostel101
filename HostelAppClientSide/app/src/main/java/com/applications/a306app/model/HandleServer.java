package com.applications.a306app.model;

import android.os.Handler;

public class HandleServer extends Handler {

    public class HandleServerResponseConstants
    {
        public static final int SETGARBAGEMESSAGE=0;

        public static final int SETBREADMESSAGE=1;

        public static final int SETVALIDATIONMESSAGE=2;

        public static final int SETREGISTRATIONMESSAGE=3;

        public static final int SETGROUPVALIDATIONMESSAGE=4;

        public static final int SETGROUPCREATIONMESSAGE=5;

        public static final int SETGROUPINVITATIONMESSAGE=6;

        public static final int SETINSERTGARBAGEMESSAGE=8;

        public static final int SETINSERTBREADMESSAGE=9;

        public static final int SETGETBREADHISTORYMESSAGE=10;

        public static final int SETGETGARBAGEHISTORYMESSAGE=11;

        public static final int SETGROUPUSERQUITMESSAGE = 12;

        public static final int SETSENDCHATMESSAGE=13;

        public static final int SETRETRIEVEALLCHATMESSAGE=14;

        public static final int SETRETRIEVERESENTCHATMESSAGE=15;

        public static final int LOGIN_SUCCESS_CODE=99;

        public static final int LOGIN_FAIL_CODE=98;

        public static final int GROUP_EXISTS_CODE=97;

        public static final String TAGS_LOGIN_VALIDATION_RESPONCE_ALLOWED="login successfull!";

        public static final String TAGS_LOGIN_VALIDATION_RESPONCE_FAILED="login failed!";

        public static final String TAGS_USER_REGISTRATION_SUCCESS="registration successful!";

        public static final String TAGS_USER_REGISTRATION_FAILED="registration failed!";

        public static final int BREAD_GARBAGE_REQUEST_TYPE_RECENT=1;

        public static final int BREAD_GARBAGE_REQUEST_TYPE_ALL=2;

        public static final int DPORT=4467;

        public static final int SETGETALLUSERSINFO = 7;

        public static final int SETGETALLCONVERSATIONMESSAGES=16;

        public static final int SETGETRECENTCONVERSATIONMESSAGES=17;

        public static final int SETINSERTCONVERSATIONMESSAGE=18;

        public static final String IP_ADDRESS="192.168.0.102";
    }


}
