package com.example.rabinkarpalgorithmapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rabinkarpalgorithmapp.Adapter.ResultAdapter;
import com.example.rabinkarpalgorithmapp.Entity.ResultData;
import com.example.rabinkarpalgorithmapp.R;
import com.example.rabinkarpalgorithmapp.Utility.CalculateNoOfColumns;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private int constant = 10;

    private TextInputLayout text_edit_text_layout;
    private TextInputEditText text_edit_text;
    private TextInputLayout pattern_edit_text_layout;
    private TextInputEditText pattern_edit_text;
    private TextView counter_text_view;
    private ImageView image_view;
    private RecyclerView result_recycler_view;
    private Button algorithm_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_edit_text_layout = findViewById(R.id.text_edit_text_layout);
        text_edit_text = findViewById(R.id.text_edit_text);
        pattern_edit_text_layout = findViewById(R.id.pattern_edit_text_layout);
        pattern_edit_text = findViewById(R.id.pattern_edit_text);
        Button search_button = findViewById(R.id.search_button);
        counter_text_view = findViewById(R.id.counter_text_view);
        image_view = findViewById(R.id.image_view);
        result_recycler_view = findViewById(R.id.result_recycler_view);
        algorithm_button = findViewById(R.id.algorithm_button);

        //gone
        counter_text_view.setVisibility(View.GONE);
        result_recycler_view.setVisibility(View.GONE);
        algorithm_button.setVisibility(View.GONE);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                search();
            }
        });

        algorithm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AlgorithmActivity.class);
                startActivity(intent);
            }
        });
    }

    //search to text
    private void search(){

        String text;
        String pattern;

        text = text_edit_text.getText().toString();
        pattern = pattern_edit_text.getText().toString();

        if(!text.equals("") && !pattern.equals("")){

            if(text_edit_text_layout.isHelperTextEnabled()){
                text_edit_text_layout.setHelperText("");
            }

            if(pattern_edit_text_layout.isHelperTextEnabled()){
                pattern_edit_text_layout.setHelperText("");
            }

            ArrayList<ResultData> resultDataArrayList = new ArrayList<>();

            int sizeOfText = text.length();
            int sizeOfPattern = pattern.length();
            int hashValue = 0;
            int hashValueOfPattern = 0;
            boolean isResult = false;

            //check size of pattern
            if(sizeOfPattern > sizeOfText){
                Toast.makeText(this, getResources().getString(R.string.mismatched_size), Toast.LENGTH_LONG).show();

            }else{

                image_view.setVisibility(View.GONE);
                counter_text_view.setVisibility(View.VISIBLE);
                result_recycler_view.setVisibility(View.VISIBLE);
                algorithm_button.setVisibility(View.VISIBLE);

                long getStartTime = System.currentTimeMillis();//take start time

                int size = sizeOfPattern - 1;
                for(int i = 0; i < sizeOfPattern; i++){

                    hashValue += (Math.pow(constant, size) * text.charAt(i));//calculate hash value for first part in text
                    hashValueOfPattern += (Math.pow(constant, size) * pattern.charAt(i));//calculate hash value for pattern

                    size--;
                }

                for(int i = 0; i <= sizeOfText - sizeOfPattern; i++){

                    if(hashValue == hashValueOfPattern){

                        int j;
                        for(j = 0; j < sizeOfPattern; j++){

                            if(text.charAt(i + j) != pattern.charAt(j)){
                                break;
                            }
                        }

                        if(j == sizeOfPattern){
                            isResult = true;
                            Toast.makeText(this, "Pattern found at index: " + i, Toast.LENGTH_SHORT).show();
                        }
                    }

                    //Calculate hash value for next part of text:
                    int temp = (int) Math.pow(constant, (sizeOfPattern - 1));
                    if(i < (sizeOfText - sizeOfPattern)){

                        hashValue = ((constant * (hashValue - (text.charAt(i) * temp))) + text.charAt(i + sizeOfPattern));
                    }

                    //create result arraylist
                    ResultData resultData = new ResultData(String.valueOf(text.charAt(i)), i, isResult);
                    resultDataArrayList.add(resultData);

                    isResult = false;
                }

                //for last value in text
                for(int i = sizeOfText - sizeOfPattern + 1; i < sizeOfText; i++){
                    ResultData resultData = new ResultData(String.valueOf(text.charAt(i)), i, false);
                    resultDataArrayList.add(resultData);
                }

                setAdapterForResult(resultDataArrayList);

                long getEndTime = System.currentTimeMillis();//take finish time
                long getEstimatedTime = getEndTime - getStartTime;
                double getTotalTime = (double) getEstimatedTime / 1000;//saniyeye çevirmek için 1000'e bölüyoruz.

                String str = getResources().getString(R.string.time) + " " + String.valueOf(getTotalTime) + " seconds";
                counter_text_view.setText(str);
            }
        }else{

            if(text.equals("")){
                text_edit_text_layout.setHelperText(getResources().getString(R.string.empty_text));
            }

            if(pattern.equals("")){
                pattern_edit_text_layout.setHelperText(getResources().getString(R.string.empty_pattern));
            }
        }
    }

    private void setAdapterForResult(ArrayList<ResultData> resultData){
        ResultAdapter adapter = new ResultAdapter(resultData);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, CalculateNoOfColumns.calculateNoOfColumns(this, 50), GridLayoutManager.VERTICAL, false);
        result_recycler_view.setLayoutManager(gridLayoutManager);
        result_recycler_view.setHasFixedSize(true);
        result_recycler_view.setAdapter(adapter);
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public int getConstant() {
        return constant;
    }

    public void setConstant(int constant) {
        this.constant = constant;
    }
}