package com.cricoscore.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricoscore.R;
import com.cricoscore.model.RunModel;

import java.util.ArrayList;


public class ScoringRunAdapter extends RecyclerView.Adapter<ScoringRunAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<RunModel>list;
    public ScoringRunAdapter(Context mContext,ArrayList<RunModel>list){
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.child_run,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RunModel runModel = list.get(position);
        holder.tv_run_child.setText(runModel.getRun());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
        public void modifyItem(final int position, final RunModel model) {
        list.set(position, model);
        notifyItemChanged(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_run_child;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_run_child = itemView.findViewById(R.id.tv_run_childd);
        }
    }
}
