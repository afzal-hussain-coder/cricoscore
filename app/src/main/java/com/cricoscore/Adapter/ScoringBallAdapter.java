package com.cricoscore.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricoscore.R;
import com.cricoscore.model.BallModel;

import java.util.ArrayList;


public class ScoringBallAdapter extends RecyclerView.Adapter<ScoringBallAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<BallModel>list;
    public ScoringBallAdapter(Context mContext,ArrayList<BallModel>list){
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.child_ball,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BallModel ballModel = list.get(position);
        holder.tv_ball_child.setText(ballModel.getBall().toString());
       // holder.tv_run_child.setText(String.valueOf(ballAndRunModel.getRun()));

    }

//    public void modifyItem(final int position, final BallAndRunModel model) {
//        list.set(position, model);
//        notifyItemChanged(position);
//    }
//    public void addNextItem(ArrayList<BallAndRunModel>newList) {
//        list.addAll(newList);
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_ball_child;
        //TextView tv_run_child;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_ball_child = itemView.findViewById(R.id.tv_ball_child);
            //this.tv_run_child = itemView.findViewById(R.id.tv_run_child);
        }
    }
}
