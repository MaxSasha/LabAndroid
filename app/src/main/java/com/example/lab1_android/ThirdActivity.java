package com.example.lab1_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.service.autofill.FillEventHistory;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    ConstraintLayout mainLayout;
    static final String ORIENTATION_PORTRAIT = "Портретный режим";
    static final String ORIENTATION_LANDSCAPE = "Альбомный режим";
    boolean mState = false;
    ConstraintLayout thirdLayout;
    WorkAlterDialog workAlterDialog;
    WorkWithOrientation workWithOrientation;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        setTitle("Third Activity");
        btn = findViewById(R.id.btn_rotate);
        mainLayout = findViewById(R.id.mainLayout);
        workWithOrientation = new WorkWithOrientation(ThirdActivity.this,btn);
        workAlterDialog = new WorkAlterDialog(ThirdActivity.this,ThirdActivity.this,btn);
        getOrientation();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Toast.makeText(this, "Альбомна", Toast.LENGTH_SHORT).show();
        }
        else if (newConfig.orientation== Configuration.ORIENTATION_PORTRAIT)
        {Toast.makeText(this, "Портретна", Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressLint("SourceLockedOrientationActivity")
    public void SetOrientation(View view) { workWithOrientation.setOrientation(); }
    public void getOrientation() { workWithOrientation.getScreenOrientation(); }
    public void showMenu(View view)
    {
        showMenuRedirect(view);
    }
    public void showMenuRedirect(final View view)
    {
        PopupMenu popupMenu = new PopupMenu(ThirdActivity.this, view);
        popupMenu.inflate(R.menu.alterdialog_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.oneButton:
                        workAlterDialog.oneButton();
                        return true;
                    case R.id.twoButton:
                        workAlterDialog.twoButton();
                        return true;
                    case R.id.inMethodOnCreate:
                        onCreateDialog(0);
                        return true;
                    case R.id.List:
                        workAlterDialog.List();
                        return true;
                    case R.id.withSwitch:
                        workAlterDialog.withSwitch();
                        return true;
                    case R.id.withCheckboxes:
                        workAlterDialog.withCheckboxes();
                        return true;
                    case R.id.automaticСlose:
                        workAlterDialog.automaticСlose();
                        return true;
                    case R.id.rating:
                        workAlterDialog.showRating();
                        return true;
                    default:
                        return false;

                }
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {

            }
        });
        popupMenu.show();
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id)
        {
            case 0:
                workAlterDialog.inMethodOnCreate();
                break;
        }
        return super.onCreateDialog(id);
    }
}
