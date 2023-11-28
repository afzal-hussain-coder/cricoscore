package com.cricoscore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricoscore.Activity.TeamPlayerActivity;
import com.cricoscore.Activity.YourTeamListActivity;
import com.cricoscore.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeamPlayerListAdapter extends RecyclerView.Adapter<TeamPlayerListAdapter.MyViewHolder>{

    Context mContext;
    //itemClickListener itemClickListener;
    int posClick=0;
    int size;
    List<TeamPlayerActivity.Player> tList = new ArrayList<>();
    public TeamPlayerListAdapter(Context mContext,int size,List<TeamPlayerActivity.Player> tList){
        this.mContext = mContext;
        //this.itemClickListener = itemClickListener;
        this.size = size;
        this.tList = tList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.team_player_list_child, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TeamPlayerActivity.Player tournament = tList.get(position);
        holder.tvTName.setText(tournament.getName());
        holder.tvtLocation.setText(tournament.getPhone());
        holder.tvtRole.setText(tournament.getRole());

        holder.image.setBorderColor(tournament.getLogo());

    }

    @Override
    public int getItemCount() {
        return tList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTName;
        private TextView tvtLocation;
        private TextView tvtRole;

        private CircleImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTName = itemView.findViewById(R.id.tvTName);
            tvtLocation = itemView.findViewById(R.id.tvtLocation);
            image = itemView.findViewById(R.id.image);
            tvtRole = itemView.findViewById(R.id.tvtRole);

        }
    }

//    public interface itemClickListener{
//        public void checkedItem(int pos);
//    }
}
