package com.applications.a306app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UsersDB    //the first user of this DB is the host user,so we need to get some methods to work with his data
{
    private static final int DEFAULT_LP=5;
    private static ArrayList<User> usersData=new ArrayList<>();
    private static User hostUser;

    public static synchronized void setLoginPasswordForHostUser(String login, String password)
    {
        if(hostUser==null)
            hostUser=new User(login,password);
        else {
            hostUser.setLogin(login);
            hostUser.setPassword(password);
        }
    }

    public static synchronized void setNameSurnameForHostUser(String name, String surname)
    {
        if(hostUser==null)
        {
            hostUser=new User();
        }
        hostUser.setName(name);
        hostUser.setName(surname);
    }

    public static synchronized String getHostUserGroup()
    {
        if(hostUser==null)
        {
            hostUser=new User();
        }
        return hostUser.getGroup();
    }

    public static synchronized void setGroupForHostUser(String group)
    {
        if(hostUser==null)
        {
            hostUser=new User();
        }
        hostUser.setGroup(group);
    }

    public static synchronized User getHostUser()
    {
        if(hostUser==null)
        {
            hostUser=new User();
        }
        return hostUser;
    }

    public static synchronized void addUser(String name,String surname,String login, String password)
    {
        usersData.add(new User(login,password,name,surname));
    }

    public static synchronized void addUsers(List<User> users)
    {
        Iterator<User> iterator = users.iterator();

        while(iterator.hasNext())
        {
            usersData.add(iterator.next());
        }
    }

    public static synchronized void setUsers(List<User> users)
    {
        usersData=new ArrayList<>();

        Iterator<User> iterator = users.iterator();

        while(iterator.hasNext())
        {
            usersData.add(iterator.next());
        }
    }



    public static ArrayList<User> getUsersData() {
        return usersData;
    }

    public static synchronized void deleteLastUser()
    {
        usersData.remove(usersData.size()-1);
    }

    public static void truncate() {
        usersData=new ArrayList<>();
    }

    public static void truncateHost() {
        hostUser=new User();
    }
}

