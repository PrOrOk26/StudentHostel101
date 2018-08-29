package com.applications.a306app.model;

import java.util.Iterator;
import java.util.List;

public class UserValidation {
    private static String validationMessage;

    public UserValidation(List<String> list) {
        Iterator<String> iterator = list.iterator();
        validationMessage = iterator.next();
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        UserValidation.validationMessage = validationMessage;
    }

    public int getValidationMessageCode() {
        switch (validationMessage) {
            case HandleServer.HandleServerResponseConstants.TAGS_LOGIN_VALIDATION_RESPONCE_ALLOWED:
                return HandleServer.HandleServerResponseConstants.LOGIN_SUCCESS_CODE;
            case HandleServer.HandleServerResponseConstants.TAGS_LOGIN_VALIDATION_RESPONCE_FAILED:
                return HandleServer.HandleServerResponseConstants.LOGIN_FAIL_CODE;
        }
        return -1;
    }
}