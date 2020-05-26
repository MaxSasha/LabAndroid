package com.example.lab_7_calculator;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    TextView resultField;
    EditText numberField;
    TextView operationField;
    Double operand = null;
    String lastOperation = "=";
    Stack<CalculateObj> returnStack;
    CalculateObj state;
    Double result=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultField =(TextView) findViewById(R.id.resultField);
        numberField = (EditText) findViewById(R.id.numberField);
        operationField = (TextView) findViewById(R.id.operationField);
        returnStack = new Stack<>();
        state = new CalculateObj("","",null,"","");
        returnStack.push(state);
        
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.returnMenu:
                onReturnClick();
                return true;
            case R.id.showInfo:
                showInfo("Довідка");
                return true;
            case R.id.about:
                showInfo("Про програму\n");
                return true;
            case R.id.userManual:
                showInfo("Інструкція користувача\n");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", lastOperation);
        if(operand!=null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand= savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand.toString());
        operationField.setText(lastOperation);
    }

    public void onNumberClick(View view){

        Button button = (Button)view;
        numberField.append(button.getText());
        state = state.clone();
        state.numberField=numberField.getText().toString();
        returnStack.push(state);
        

        if(lastOperation.equals("=") && operand!=null){
            operand = null;
            state = state.clone();
            state.operand=null;
            returnStack.push(state);
            
        }
    }

    public void onOperationClick(View view){
        Button button = (Button)view;
        String op = button.getText().toString();
        String number = numberField.getText().toString();

        if(number.length()>0){
            number = number.replace(',', '.');
            try{
                performOperation(Double.valueOf(number), op);
            }catch (NumberFormatException ex){
                numberField.setText("");
                state = state.clone();
                state.numberField ="";
                returnStack.push(state);
                
            }
        }
        lastOperation = op;
        operationField.setText(lastOperation);
        state = state.clone();
        state.lastOperation = op;
        state.operationField = lastOperation;
        returnStack.push(state);
        
    }

    private void performOperation(Double number, String operation){


        if(operand ==null){
            operand = number;
            state =state.clone();
            state.operand=number;
            returnStack.push(state);
        }
        else{
            if(lastOperation.equals("=")){
                lastOperation = operation;
                state=state.clone();
                state.lastOperation = operation;
                returnStack.push(state);
                
            }
            switch(lastOperation){
                case "=":
                    operand =number;
                    state = state.clone();
                    state.operand = number;
                    returnStack.push(state);
                    
                    break;
                case "/":
                    if(number==0){
                        operand =0.0;
                        state = state.clone();
                        state.operand = number;
                        returnStack.push(state);
                        
                    }
                    else{
                        operand /=number;
                        state = state.clone();
                        state.operand = number;
                        returnStack.push(state);
                        
                    }
                    break;
                case "*":
                    operand *=number;
                    state = state.clone();
                    state.operand = number;
                    returnStack.push(state);
                    
                    break;
                case "+":
                    operand +=number;
                    state = state.clone();
                    state.operand = number;
                    returnStack.push(state);
                    
                    break;
                case "-":
                    operand -=number;
                    state = state.clone();
                    state.operand = number;
                    returnStack.push(state);
                    break;
            }
        }

        resultField.setText(operand.toString().replace('.', ','));
        result=operand;
        numberField.setText("");
        state = state.clone();
        state.resultField = operand.toString().replace('.', ',');
        state.numberField="";
        returnStack.push(state);

    }

    public void onReturnClick()
    {
    if(returnStack.empty())
    {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Останньої дії немає", Toast.LENGTH_SHORT);
        toast.show();
    }
    else{
        returnStack.pop();
        if(returnStack.empty())
        {
            state = new CalculateObj("","",null,"","");
            returnStack.push(state);
        }
        CalculateObj state = returnStack.pop();
        System.out.println("SIZE POP: "+returnStack.size());
        if(state.numberField!=null)
            numberField.setText(state.numberField);

        if(state.resultField!=null)
            resultField.setText(state.resultField);

        if(state.operationField!=null)
            operationField.setText(state.operationField);

        if(state.operand!=null)
            operand = state.operand;

        if(state.lastOperation!=null)
            lastOperation = state.lastOperation;
    }

    }
    public void onClearAllClick(View view)
    {
        operand = null;
        resultField.setText("");
        numberField.setText("");
        lastOperation = "=";
        operationField.setText("");
        returnStack.clear();
        state = new CalculateObj("","",null,"","");
        returnStack.push(state);
    }
    public void onCEClick(View view)
    {
        numberField.setText("");
        state = state.clone();
        state.numberField="";
        returnStack.push(state);
    }
    public void onBackClick(View view)
    {
        String txt = numberField.getText().toString();
        numberField.setText(txt.substring(0,txt.length()-1));
        state = state.clone();
        state.numberField=txt.substring(0,txt.length()-1);
        returnStack.push(state);
    }
    public void showInfo(String text)
    {
        Toast toast = Toast.makeText(getApplicationContext(),
                text, Toast.LENGTH_SHORT);
        toast.show();
    }
    public void showLastResult(View view)
    {
        showInfo(result.toString());
    }

}
