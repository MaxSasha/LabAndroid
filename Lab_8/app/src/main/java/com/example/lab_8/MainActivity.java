package com.example.lab_8;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    Stack<NoteObj> returnStack;
    Stack<NoteObj> redoStack;
    Intent intent;
    int listItemId=0;
    NoteObj state;
    boolean editN=false;
    ListView listView;
    String note="";
    EditText titleText;
    EditText Text;

    private static  final int REQUEST_ACCESS_TYPE=1;
    ArrayList<String> notes = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titleText=findViewById(R.id.title);
        Text =findViewById(R.id.text);
        listView = (ListView) findViewById(R.id.NotesList);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                Toast.makeText(getApplicationContext(), ((TextView) itemClicked).getText(),
                        Toast.LENGTH_SHORT).show();
                listItemId=position;
            }
        });
        returnStack = new Stack<>();
        state = new NoteObj("","",notes,false,0);
        returnStack.push(state);
        /*System.out.println("push|size:"+returnStack.size());
        back(state);
        System.out.println("//////////////////BEGIN//////////////////////");
        for (int i=0;i<returnStack.size();i++) {
            NoteObj state= returnStack.pop();
            System.out.println("Title: "+state.title+" text:"+state.text+"notes"+state.notes.size());
        }
        System.out.println("//////////////////END//////////////////////");
        back("on Start");*/
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

        }
        return super.onOptionsItemSelected(item);
    }
    public void addData()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy,h:mm:s");
        Date date = new Date();
        date.getTime();
        notes.add(titleText.getText().toString()+" \n"+Text.getText().toString() + " \nСтворено:" + dateFormat.format(date));
        /*adapter.add("Заголовок:"+titleText.getText().toString()+"\n"+Text.getText().toString() + "\nСтворено:" + dateFormat.format(date));*/
        adapter.notifyDataSetChanged();
        titleText.setText("");
        Text.setText("");
    }
    public void editNote(View view)
    {
        if(notes.size()>0) {
            editN = true;
            String txt;
            String[] text = notes.get(listItemId).split(":");
            String[] title = text[0].split("\n");
            if (notes.get(listItemId).indexOf("Редаговано") == -1) {
                txt = text[0].substring(title[0].length() + 1, text[0].length() - 9);
            } else {
                txt = text[0].substring(title[0].length() + 1, text[0].length() - 11);
            }

            titleText.setText(title[0]);
            Text.setText(txt);
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Список пустий", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
    public void removeNote(View view)
    {
        if(notes.size()>0) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Видалити?");
            adb.setMessage("Ти дійсно хочеш видалити елемент: " + notes.get(listItemId).split(" ")[0]);
            final int positionToRemove = listItemId;
            adb.setNegativeButton("Cancel", null);
            adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    notes.remove(listItemId);
                    adapter.notifyDataSetChanged();
                }
            });
            adb.show();
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Список пустий", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void saveNote(View view)
    {
        if(editN==false) {
            if (!isEmpty(titleText) && !isEmpty(Text)) {
                addData();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Заповніть всі поля!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else
        {
            AlertDialog.Builder adb=new AlertDialog.Builder(this);
            adb.setTitle("Зберегти?");
            adb.setMessage("Ти дійсно хочеш зберегти елемент: " + notes.get(listItemId).split(" ")[0]);
            adb.setNegativeButton("Cancel", null);
            adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy,h:mm:s");
                    Date date = new Date();
                    date.getTime();
                    String changeStr = (titleText.getText().toString()+" \n"+Text.getText().toString() + " \nРедаговано:" + dateFormat.format(date));
                    notes.remove(listItemId);
                    notes.add(listItemId,changeStr);
                    adapter.notifyDataSetChanged();
                    editN=false;
                    listItemId=0;
                    titleText.setText("");
                    Text.setText("");


                    /*back("on SaveNote");*/

                }});
            adb.show();

        }

    }
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private void back(NoteObj state)
    {
        System.out.println("///"+"Title: "+state.title+" text:"+state.text+"notes"+state.notes.size()+"///////////////////////////////////////");
    }
}
