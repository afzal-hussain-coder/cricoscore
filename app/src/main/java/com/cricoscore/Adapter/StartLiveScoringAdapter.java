package com.cricoscore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricoscore.Activity.SubmitCricketDetailsActivity;
import com.cricoscore.Activity.TossActivity;
import com.cricoscore.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class StartLiveScoringAdapter extends RecyclerView.Adapter<StartLiveScoringAdapter.MyViewHolder>{

    Context mContext;
    ItemClickListener itemClickListener;

    public StartLiveScoringAdapter(Context mContext,ItemClickListener itemClickListener){
        this.mContext = mContext;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.start_live_score_child,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.itemView.setOnClickListener(v -> {
            itemClickListener.itemClick(position);
        });
        holder.mb_view.setOnClickListener(v -> {
            itemClickListener.itemView(position);
        });

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvTime;
        TextView tvTName;
        TextView tvTeamName1;
        TextView tvTeamName2;
        CircleImageView image1;
        CircleImageView image2;
        MaterialButton mb_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvDate = itemView.findViewById(R.id.tvDate);
            this.tvTime = itemView.findViewById(R.id.tvTime);
            this.tvTName = itemView.findViewById(R.id.tvTName);
            this.tvTeamName1 = itemView.findViewById(R.id.tvTeamName1);
            this.tvTeamName2 = itemView.findViewById(R.id.tvTeamName2);
            this.image1 = itemView.findViewById(R.id.image1);
            this.image2 = itemView.findViewById(R.id.image2);

            this.mb_view = itemView.findViewById(R.id.mb_view);
        }
    }

    public interface ItemClickListener{
        void itemClick(int pos);
        void itemView(int pos);
    }


}
