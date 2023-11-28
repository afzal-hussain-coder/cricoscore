package com.cricoscore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricoscore.Activity.TeamListActivity;
import com.cricoscore.Activity.YourTeamListActivity;
import com.cricoscore.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class YourTeamListAdapter extends RecyclerView.Adapter<YourTeamListAdapter.MyViewHolder>{

    Context mContext;
    itemClickListener itemClickListener;
    int posClick=0;
    List<YourTeamListActivity.Team> tList;
    public YourTeamListAdapter(Context mContext,List<YourTeamListActivity.Team> tList,itemClickListener itemClickListener){
        this.mContext = mContext;
        this.tList = tList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.your_team_list_child, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        YourTeamListActivity.Team tournament = tList.get(position);
        holder.tvTName.setText(tournament.getName());
        holder.tvtLocation.setText(tournament.getAddress());

        holder.image.setBorderColor(tournament.getLogo());
        holder.cb.setVisibility(View.VISIBLE);

        holder.cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked==true){
                posClick++;
                itemClickListener.checkedItem(posClick,tournament.getName());
            }else{
                posClick--;
                itemClickListener.checkedItem(posClick,tournament.getName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return tList.size();
    }

    public void clearData(){
        posClick =0;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox cb;
        private TextView tvTName;
        private TextView tvtLocation;

        private CircleImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cb = itemView.findViewById(R.id.cb);

            tvTName = itemView.findViewById(R.id.tvTName);
            tvtLocation = itemView.findViewById(R.id.tvtLocation);
            image = itemView.findViewById(R.id.image);
        }
    }

    public interface itemClickListener{
         void checkedItem(int pos,String teamName);
    }
}
