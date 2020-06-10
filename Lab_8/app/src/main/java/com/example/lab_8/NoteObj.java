package com.example.lab_8;

import android.widget.EditText;

import java.util.ArrayList;

public class NoteObj {

    String title="";
    String text="";
    boolean editN=false;
    int listItemId;
    ArrayList<String> notes = new ArrayList<>();
    NoteObj(){}
    NoteObj(String title, String text, ArrayList<String> notes, boolean editN,int listItemId)
    {
        this.title = title;
        this.listItemId=listItemId;
        this.text = text;
        this.notes = notes;
        this.editN = editN;
    }
    NoteObj(String title)
    {
        this.title=title;
    }
    NoteObj(int listItemId)
    {
        this.listItemId=listItemId;
    }
    NoteObj(String text, int i)
    {
        this.text=text;
    }
    NoteObj(ArrayList<String> notes)
    {
        this.notes = notes;
    }
    public NoteObj clone()
    {
        return new NoteObj(this.title,this.text,this.notes,this.editN,this.listItemId);
    }
}
