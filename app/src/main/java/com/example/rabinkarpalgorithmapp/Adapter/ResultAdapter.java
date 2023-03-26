package com.example.rabinkarpalgorithmapp.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rabinkarpalgorithmapp.Entity.ResultData;
import com.example.rabinkarpalgorithmapp.R;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder>{

    private ArrayList<ResultData> resultDataArrayList;

    public ResultAdapter(ArrayList<ResultData> resultDataArrayList){
        this.resultDataArrayList = resultDataArrayList;
    }

    @Override
    public ResultAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.adapter_result, viewGroup, false);
        ResultAdapter.ViewHolder holder = new ResultAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ResultAdapter.ViewHolder holder, final int position) {

        ResultData resultData = resultDataArrayList.get(position);

        if(resultData.isCheck()){
            holder.result_text_view.setBackgroundColor(Color.parseColor("#422987"));
            holder.result_text_view.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.index_text_view.setBackgroundColor(Color.parseColor("#422987"));
            holder.index_text_view.setTextColor(Color.parseColor("#FFFFFFFF"));
            holder.stroke_linear_layout.setBackgroundColor(Color.parseColor("#896FCA"));

        }else{
            holder.result_text_view.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            holder.result_text_view.setTextColor(Color.parseColor("#FF000000"));
            holder.index_text_view.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            holder.index_text_view.setTextColor(Color.parseColor("#FF000000"));
            holder.stroke_linear_layout.setBackgroundColor(Color.parseColor("#422987"));
        }

        String strValue = "";
        if(resultData.getValue().equals(" ")){
            strValue = "\" \"";
        }else if(resultData.getValue().equals("\"")){
            strValue = "\"";
        }
        else{
            strValue = resultData.getValue();
        }
        holder.result_text_view.setText(strValue);

        holder.index_text_view.setText(String.valueOf(resultData.getIndex()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return resultDataArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout stroke_linear_layout;
        private final TextView result_text_view;
        private final TextView index_text_view;

        public ViewHolder(View v) {
            super(v);

            stroke_linear_layout = v.findViewById(R.id.stroke_linear_layout);
            result_text_view = v.findViewById(R.id.result_text_view);
            index_text_view = v.findViewById(R.id.index_text_view);
        }
    }

    public ArrayList<ResultData> getResultDataArrayList() {
        return resultDataArrayList;
    }

    public void setResultDataArrayList(ArrayList<ResultData> resultDataArrayList) {
        this.resultDataArrayList = resultDataArrayList;
    }
}