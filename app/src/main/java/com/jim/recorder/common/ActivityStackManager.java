package com.jim.recorder.common;

import android.app.Activity;
import android.os.Process;
import android.util.Log;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class ActivityStackManager {

    private static final String TAG = "ActivityStackManager";
    private static ActivityStackManager instance;
    private Stack<Activity> activities;

    public static synchronized ActivityStackManager getInstance(){
        if(instance == null){
            instance = new ActivityStackManager();
        }
        return instance;
    }

    private ActivityStackManager(){}

    public void addActivity(Activity activity){
        if(activities == null){
            activities = new Stack<Activity>();
        }
        if(activities.search(activity) == -1) {
            activities.push(activity);
        }
        publishActivityStack();
    }

    public void finishActivity(Activity activity){
        if(activities != null && activities.size()>0){
            activities.remove(activity);
//            activity.finish();
        }
    }

    public void finishActivity(Class<?> clazz){
        if(activities != null && activities.size()>0){
            Activity activity;
            Iterator<Activity> it = activities.iterator();
            while(it.hasNext()){
                activity = it.next();
                if(activity != null && activity.getClass().equals(clazz)){
                    activities.removeElement(activity);
                    activity.finish();
                }
            }
        }
    }

    public void setTopActivity(Activity activity){
        if(activities != null && activities.size()>0){
//            if(activities.search(activity) != 1){
//                activities.setElementAt(activity, 0);
//            }
            if(activities.search(activity) == -1){
                activities.push(activity);
                return;
            }

            int location = activities.search(activity);
            if(location != 1){
                activities.remove(activity);
                activities.push(activity);
            }
        }
    }

    public Activity getTopActivity(){
        if(activities != null && activities.size()>0){
            return activities.peek();
        }
        return null;
    }

    public boolean isTopActivity(Activity activity){
        return activity.equals(activities.peek());
    }

    public void finishTopActivity(){
        if(activities != null && activities.size()>0){
            Activity activity = activities.pop();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    public void finishOtherActivites(Activity newActivity) {
        if(activities != null && activities.size()>0){
            while(!activities.empty()){
                Activity activity = activities.pop();
                if (activity != null) {
                    if (!activity.equals(newActivity)) {
                        activity.finish();
                    }
                }
            }
            activities.clear();
            activities.push(newActivity);
        }
    }

    public void finishAllActivity(){
        if(activities != null && activities.size()>0){
            while(!activities.empty()){
                Activity activity = activities.pop();
                if (activity != null) {
                    activity.finish();
                }
            }
            activities.clear();
            activities = null;
        }
    }

    public void appExit(){
        finishAllActivity();
        Process.killProcess(Process.myPid());
    }

    public int getActivityPosition(Activity acitivty){
        return activities.search(new SoftReference<Activity>(acitivty));
    }

    public void publishActivityStack(){
        Iterator<Activity> it = activities.iterator();
        StringBuilder builder = new StringBuilder();
        builder.append("ActivityStack:\n");
        while(it.hasNext()){
            Activity activity = it.next();
            if (activity != null) {
                builder.append(activity.getClass().getName() + "\n");
            }
        }
        Log.i(TAG, builder.toString());
    }

    public Stack<Activity> getActivityStack() {
        return activities;
    }

    public void backToHomeActivity() {
        removeAllFinishActivity();
        if(activities != null && activities.size()>0){
            while(activities.size() > 1) {
                Activity activity = activities.pop();
                if (activity != null) {
                    activity.finish();
                }
            }
        }
    }

    public void removeAllFinishActivity(){
        if(activities != null && activities.size()>0){
            ArrayList<Activity> acts = new ArrayList<>(activities);
            for(Activity act:acts){
                if(act.isFinishing()){
                    activities.remove(act);
                }
            }
        }
    }
}
