package com.applications.a306app.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class UserActivityDB {

    private static List<UserActivity> activitiesList=new ArrayList<>();

    private static LinkedList<String> breadBuyingOrder=new LinkedList<>();
    private static LinkedList<String> garbageTakingOrder=new LinkedList<>();


    public static int getSize()
    {
        return activitiesList.size();
    }

    public static void makeBreadOrder()
    {
        breadBuyingOrder.clear();

        ListIterator<UserActivity> activityIterator=activitiesList.listIterator(activitiesList.size());

        UserActivity buff;

        while(activityIterator.hasPrevious())
        {
            buff=activityIterator.previous();
            if(!breadBuyingOrder.contains(buff.getPerformerLogin())&&buff.getTYPE()== HandleServer.HandleServerResponseConstants.SETGETBREADHISTORYMESSAGE)
                breadBuyingOrder.addFirst(buff.getPerformerLogin());
        }

        breadBuyingOrder.addLast(breadBuyingOrder.pollFirst());
    }

    public static void makeGarbageOrder()
    {
        garbageTakingOrder.clear();

        ListIterator<UserActivity> activityIterator=activitiesList.listIterator(activitiesList.size());

        UserActivity buff;

        while(activityIterator.hasPrevious())
        {
            buff=activityIterator.previous();
            if(!garbageTakingOrder.contains(buff.getPerformerLogin())&&buff.getTYPE()== HandleServer.HandleServerResponseConstants.SETGETGARBAGEHISTORYMESSAGE)
                garbageTakingOrder.addFirst(buff.getPerformerLogin());
        }

        garbageTakingOrder.addLast(garbageTakingOrder.pollFirst());
    }

    public static void updateBreadOrder()
    {
        breadBuyingOrder.removeFirstOccurrence(UsersDB.getHostUser().getLogin());
        breadBuyingOrder.addFirst(UsersDB.getHostUser().getLogin());
    }

    public static void updateGarbageOrder()
    {
        garbageTakingOrder.removeFirstOccurrence(UsersDB.getHostUser().getLogin());
        garbageTakingOrder.addFirst(UsersDB.getHostUser().getLogin());
        //garbageTakingOrder.addLast(garbageTakingOrder.pollFirst());
    }

    public static boolean isUserNextInBreadQueue(User userToCheck)
    {
         if(userToCheck.getLogin().equals(breadBuyingOrder.peekLast()))
             return true;
         return false;

    }

    public static boolean isUserNextInGarbageQueue(User userToCheck)
    {
        if(userToCheck.getLogin().equals(garbageTakingOrder.peekLast()))
            return true;
        return false;

    }

    public static boolean isUserExistsInBreadQueue(User userToCheck)
    {
        if(breadBuyingOrder.contains(userToCheck.getLogin()))
            return true;
         return false;
    }

    public static boolean isUserExistsInGarbageQueue(User userToCheck)
    {
        if(garbageTakingOrder.contains(userToCheck.getLogin()))
            return true;
        return false;
    }


    public synchronized static void insertGarbage(UserActivity toInsert)
    {
        activitiesList.add(toInsert);

        if(toInsert.getTYPE()!= HandleServer.HandleServerResponseConstants.SETGETGARBAGEHISTORYMESSAGE)
        {
            return;
        }

    }

    public synchronized static void insertBread(UserActivity toInsert)
    {
        activitiesList.add(toInsert);

        if(toInsert.getTYPE()!= HandleServer.HandleServerResponseConstants.SETGETBREADHISTORYMESSAGE)
        {
            return;
        }

    }

    public synchronized static void addActivities(ArrayList<UserActivity> dates)
    {
        activitiesList.addAll(dates);
        Collections.sort(activitiesList,(a,b) -> a.getDate().compareTo(b.getDate()));
    }
    public synchronized static void setActivities(ArrayList<UserActivity> dates)
    {
        activitiesList.clear();

        Collections.sort(dates,(a,b) -> a.getDate().compareTo(b.getDate()));

        activitiesList.addAll(dates);
    }

    public static List<UserActivity> getActivitiesList() {
        return activitiesList;
    }

    public synchronized static void addActivity(UserActivity buffAct) {
        activitiesList.add(buffAct);
        Collections.sort(activitiesList,(a,b)->a.getDate().compareTo(b.getDate()));
    }

    public static void clearDB() {
        activitiesList=new ArrayList<>();
    }


    public static List<UserActivity> getAllActivities(int type)
    {
        List<UserActivity> toReturn=new ArrayList<>();

        Iterator<UserActivity> iterator=activitiesList.iterator();

        while (iterator.hasNext()) {

            UserActivity buff=iterator.next();

            if (type ==buff.getTYPE()) {
                      toReturn.add(buff);
            }
        }
        return toReturn;
    }

    public static List<UserActivity> getNactivities(int N,int type)
    {
        LinkedList<UserActivity> toReturn=new LinkedList<>();

       ListIterator<UserActivity> iterator=activitiesList.listIterator(activitiesList.size());

        int counter=0;

        while (iterator.hasPrevious()&&counter<N) {

            UserActivity buff=iterator.previous();

            if (type ==buff.getTYPE()) {
                toReturn.addFirst(buff);
                counter++;
            }
        }


        return toReturn;
    }




}
