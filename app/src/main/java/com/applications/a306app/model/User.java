package com.applications.a306app.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User {
    private String login;
    private String password;
    private String name;
    private String surname;
    private String group;

    private List<Integer> conversationsIds =new ArrayList<>();

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.name=new String();
        this.surname=new String();
        this.group =new String();
    }

    public User() {
        this.login = new String();
        this.password = new String();
        this.name=new String();
        this.surname=new String();
        this.group =new String();
    }

    public User(String login, String password, String name, String surname) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.group =new String();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        return password != null ? password.equals(user.password) : user.password == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<Integer> getConversationsIds() {
        return conversationsIds;
    }

    public void setConversationsIds(List<Integer> conversationsIds) {
        this.conversationsIds = conversationsIds;
    }

    public void addConversation(Integer idConversation)
    {
        conversationsIds.add(idConversation);
    }

    public boolean chechIfUserIsInCoversation(Integer toCheck)
    {
        for(Integer buff:conversationsIds)
        {
            if(buff.equals(toCheck))
            {
                return true;
            }
        }
        return false;
    }

}
