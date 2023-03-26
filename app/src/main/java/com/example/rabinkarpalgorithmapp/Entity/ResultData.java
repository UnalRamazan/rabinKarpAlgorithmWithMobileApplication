package com.example.rabinkarpalgorithmapp.Entity;

public class ResultData {

    private String value;
    private int index;
    private boolean isCheck;

    public ResultData(String value, int index, boolean isCheck) {
        this.value = value;
        this.index = index;
        this.isCheck = isCheck;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}