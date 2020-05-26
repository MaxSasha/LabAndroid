package com.example.lab_7_calculator;

public class CalculateObj {
    CalculateObj(){}
    CalculateObj(String numberField, String resultField, Double operand,String operationField,String lastOperation)
    {
        this.numberField=numberField;
        this.resultField=resultField;
        this.operand=operand;
        this.operationField = operationField;
        this.lastOperation = lastOperation;

    }
    CalculateObj(String resultField)
    {
        this.resultField=resultField;

    }
    CalculateObj(String numberField, int number)
    {
        this.numberField=numberField;

    }
    CalculateObj(int operandS, Double operand)
    {
        this.operand=operand;

    }
    CalculateObj(int operationF,  String operationField)
    {
        this.operationField=operationField;

    }
    CalculateObj(double lastOperationF, String lastOperation)
    {
        this.lastOperation=lastOperation;

    }
    String numberField="";
    String resultField="";
    Double operand=null;
    String operationField="";
    String lastOperation="";
    public CalculateObj clone()
    {
        return new CalculateObj(this.numberField,this.resultField, this.operand,this.operationField,this.lastOperation);
    }
}
