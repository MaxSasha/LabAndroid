package com.example.lab1_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import yuku.ambilwarna.AmbilWarnaDialog;

public class SecondActivity extends AppCompatActivity {

    int DefaultColor;
    ConstraintLayout secondLayout;
    ConstraintLayout mainLayout;
    WorkAlterDialog workAlterDialog;
    WorkWithOrientation workWithOrientation;
    static final String ORIENTATION_PORTRAIT = "Портретный режим";
    Button btn_rotate;
    static final String ORIENTATION_LANDSCAPE = "Альбомный режим";
    boolean mState = false;
    TextView view;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        setContentView(R.layout.activity_second);
        secondLayout = (ConstraintLayout) findViewById(R.id.secondLayout);
        view = (TextView) findViewById(R.id.txtView_1);
        btn_rotate = findViewById(R.id.btn_rotate);
        mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
        workWithOrientation = new WorkWithOrientation(SecondActivity.this,btn_rotate);
        setTitle("Про додаток");
        showData(intent);
        getOrientation();
        workAlterDialog = new WorkAlterDialog(SecondActivity.this, SecondActivity.this,btn_rotate);
        DefaultColor = ContextCompat.getColor(this, R.color.colorPrimary);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainpage_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        MainActivity mainActivity = new MainActivity();
        switch(id){
            case R.id.thirdPage:
                GoThird();
                return true;
            case R.id.showInfo:
                Toast.makeText(this, "You on Second Page",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.changeColor:
                setBackgroundColorRedirect();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showData(Intent intent)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy");
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Date date = new Date();
        date.getTime();
        view.setText(message+"\n"+dateFormat.format(date));
    }
    public void setBackgroundColor(View view)
    {
        setBackgroundColorRedirect();
    }
    public void setBackgroundColorRedirect()
    {
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, DefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(SecondActivity.this,"Close", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                DefaultColor = color;
                secondLayout.setBackgroundColor(color);
            }
        });
        ambilWarnaDialog.show();
    }
    public void showMenu(View view)
    {
        showMenuRedirect(view);
    }
    public void showMenuRedirect(final View view)
    {
        PopupMenu popupMenu = new PopupMenu(SecondActivity.this, view);
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
    public void GoThird()
    {
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.putExtra("", "");
        startActivity(intent);
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
    @SuppressLint("SourceLockedOrientationActivity")
    public void SetOrientation(View view) { workWithOrientation.setOrientation(); }
    public void getOrientation()
    {
        btn_rotate.setText(workWithOrientation.getScreenOrientation());
    }

}
