package com.example.lab1_android;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    String str = "";

    Button btn;

    boolean mState = false;

    int DefaultColor;

    WorkAlterDialog workAlterDialog;
    WorkWithOrientation workWithOrientation;

    ConstraintLayout secondLayout;
    ConstraintLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn_rotate);
        setTitle("Main Activity");
        workAlterDialog = new WorkAlterDialog(MainActivity.this,MainActivity.this,btn);
        workWithOrientation = new WorkWithOrientation(MainActivity.this,btn);
        mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
        DefaultColor = ContextCompat.getColor(this, R.color.colorPrimary);
        setDataTime();
        getOrientation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainpage_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.thirdPage:
                GoThird();
                return true;
            case R.id.showInfo:
                ShowInfo("all");
                return true;
            case R.id.changeColor:
                setBackgroundColor();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void ShowInfo(String items)
    {
        TextView label = findViewById(R.id.mainLabel);
        EditText editText ;
        switch (items)
        {
            case "all":
                editText = findViewById(R.id.FIO);
                str=editText.getText()+"\n";
                editText = findViewById(R.id.group_info);
                str+=editText.getText()+"\n";
                editText = findViewById(R.id.specialty);
                str+=editText.getText();
                if(str.trim().equals(""))
                { break;}
                else
                label.setText(str);
                break;
            case "group_info":
                editText = findViewById(R.id.group_info);
                str=editText.getText()+"\n";
                if(str.trim().equals(""))
                {break;}
                else
                    label.setText(str);
                break;
            case "fio_info":
                editText = findViewById(R.id.FIO);
                str=editText.getText()+"\n";
                if(str.trim().equals(""))
                {break;}
                else
                    label.setText(str);
                break;
            case "speciality_info":
                editText = findViewById(R.id.specialty);
                str = editText.getText()+"";
                if(str.trim().equals(""))
                {break;}
                else
                    label.setText(str);
                break;

        }

    }
    public void Click(View view) { ShowInfo("all"); }
    public void Next()
    {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(EXTRA_MESSAGE, str);
        startActivity(intent);
    }
    public void setDataTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy");
        TextView label = findViewById(R.id.mainLabel2);
        Date date = new Date();
        date.getTime();
        label.setText(dateFormat.format(date));
    }
    public void NextActivity(View view) { Next(); }
    public void setBackgroundColor()
    {
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, DefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                Toast.makeText(MainActivity.this,"Close", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                DefaultColor = color;
                mainLayout.setBackgroundColor(color);
            }
        });
        ambilWarnaDialog.show();
    }
    public void showToast(View view) { showMenu(view); }
    public void showMenu(View v)
    {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, v);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nextPage:
                        Next();
                        return true;
                    case R.id.Show_allInfo_submenu:
                        ShowInfo("all");
                        return true;
                    case R.id.show_FIO_submenu:
                        ShowInfo("fio_info");
                        return true;
                    case R.id.show_group_submenu:
                        ShowInfo("group_info");
                        return true;
                    case R.id.show_speciality_submenu:
                        ShowInfo("speciality_info");
                        return true;
                    case R.id.changeColor:
                        setBackgroundColor();
                        return true;
                    case R.id.showToast:
                        Toast toast = Toast.makeText(getApplicationContext(),R.string.app_name,Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);
                        LinearLayout linearLayout = (LinearLayout) toast.getView();
                        ImageView imageView = new ImageView(getApplicationContext());
                        imageView.setImageResource(R.drawable.toast_default);
                        linearLayout.addView(imageView,0);
                        toast.show();
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
        intent.putExtra(EXTRA_MESSAGE, str);
        startActivity(intent);
    }
    public void showMenu2(View view) { showMenuRedirect(view); }
    public void showMenuRedirect(final View view)
    {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
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

    public void getOrientation() { btn.setText(workWithOrientation.getScreenOrientation()); }
    @SuppressLint("SourceLockedOrientationActivity")
    public void SetOrientation(View view) { workWithOrientation.setOrientation(); }
    
}
