package com.example.lab1_android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Button;

public class WorkWithOrientation {
    Activity activity;
    static final String ORIENTATION_PORTRAIT = "Портретна орієнтація";
    static final String ORIENTATION_LANDSCAPE = "Альбомна орієнтація";
    Button button;
    boolean mState = false;

    WorkWithOrientation(Activity activity, Button button)
    {
        this.activity = activity;
        this.button = button;
    }

    public String getScreenOrientation()
    {
        if(activity.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
            return ORIENTATION_PORTRAIT;
        else    if    (activity.getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
            return ORIENTATION_LANDSCAPE;
        else
            return "";
    }
    @SuppressLint("SourceLockedOrientationActivity")
    public void setOrientation() {

        if (!mState) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            button.setText(ORIENTATION_LANDSCAPE);
            mState =true;
        }
        else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            button.setText(ORIENTATION_PORTRAIT);
            mState = !mState;
        }

    }

}
