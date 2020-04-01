package com.example.lab1_android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Timer;
import java.util.TimerTask;

public class WorkAlterDialog
{
    Activity activity;
    AlertDialog.Builder builder;
    Context context;
    Button btn;
    WorkWithOrientation workWithOrientation;
    WorkAlterDialog(Activity activity, Context context, Button btn)
    {
        this.activity = activity;
        this.context = context;
        this.btn = btn;
        workWithOrientation = new WorkWithOrientation(activity,btn);
    }

    public void oneButton()
    {
        builder = new AlertDialog.Builder(context);

        builder.setTitle("Різновидність діалогових вікон")
                .setMessage("З однією кнопкою")
                .setIcon(R.drawable.button_default)
                .setCancelable(false)
                .setNegativeButton("Одна кнопка",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        builder.show();
    }
    public void twoButton()
    {
        String title = "Різновидність діалогових вікон";
        String message = "Дві кнопки";
        String button1String = "Ok";
        String button2String = "Nope";
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(button1String, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Ok", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(button2String, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Nope",Toast.LENGTH_LONG).show();
                    }
                });


        builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(context,"Canceled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
    public void inMethodOnCreate()
    {
        String title = "Різновидність діалогових вікон";
        String message = "В методі OnCreate";
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Ok", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("Maybe", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context,"Maybe",Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Nope", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Nope",Toast.LENGTH_SHORT).show();
                    }
                });

        builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(context,"Canceled", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
    public void List()
    {
        builder = new AlertDialog.Builder(context);
        final String[] itemList ={"first", "second", "third"};

        builder.setTitle("Різновидність діалогових вікон");
        builder.setItems(itemList, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(context,"Your Choice:" + itemList[which],Toast.LENGTH_SHORT).show();
        }
         });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void withSwitch()
    {
        builder = new AlertDialog.Builder(context);
        final String[] itemList ={"first", "second", "third"};
        builder.setTitle("Різновидність діалогових вікон")
                .setIcon(R.drawable.button_default)
                .setCancelable(false)
                .setNeutralButton("Back",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        builder.setSingleChoiceItems(itemList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context,"You chose:"+itemList[which],Toast.LENGTH_SHORT);
            }
        });
        builder.show();
    }
    public void withCheckboxes()
    {
        builder = new AlertDialog.Builder(context);
        String data[] = { "one", "two", "three", "four" };
        DialogInterface.OnMultiChoiceClickListener myItemsMultiClickListener = new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                Log.d("myLogs", "which = " + which + ", isChecked = " + isChecked);
            }
        };
        boolean itemsBool[] = { false, false, true, false };
        builder.setTitle("Різновидність діалогових вікон")
                .setIcon(R.drawable.button_default)
                .setCancelable(false)
                .setNeutralButton("Back",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        builder.setMultiChoiceItems(data,itemsBool, myItemsMultiClickListener);

        builder.show();
    }
    public void automaticСlose()
    {
        builder = new AlertDialog.Builder(context);
        builder.setTitle("Автоматичне закриття вікна")
        .setMessage("Через   пять   секунд це   вікно   закриється автоматично!")
        .setCancelable(true);
        final AlertDialog dialog = builder.create();
        dialog.show();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.dismiss();
                timer.cancel();
            }
        },5000);
    }
    @SuppressLint("SourceLockedOrientationActivity")
    public void showRating()
    {
        workWithOrientation.setOrientation();
        builder = new AlertDialog.Builder(context);
        final RatingBar rating = new RatingBar(context);
        builder.setIcon(android.R.drawable.btn_star_big_on)
        .setTitle("Voting")
        .setView(rating);
        rating.setNumStars(5);
        rating.setStepSize(1);
        builder.setPositiveButton("Ready", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                workWithOrientation.setOrientation();
                Toast.makeText(context, "You rated on: "+ (rating.getRating() + 1),Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                workWithOrientation.setOrientation();
                dialog.cancel();
            }
        });
        builder.create();
        builder.show();
    }
}
