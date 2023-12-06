package com.cricoscore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cricoscore.Activity.MatchDetailsActivity;
import com.cricoscore.Activity.TeamListActivity;
import com.cricoscore.Activity.TournamentDetailsActivity;
import com.cricoscore.Activity.YourTeamListActivity;
import com.cricoscore.Fragment.TournamentFragment;
import com.cricoscore.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import de.hdodenhof.circleimageview.CircleImageView;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.MyHolder>{

    Context mContext;
    int value;
    List<TournamentFragment.Tournament> tList;
    getImageCallListener getImageCallListener;
    public TournamentAdapter(Context mContext,List<TournamentFragment.Tournament> tList,getImageCallListener getImageCallListener){
        this.mContext = mContext;
        this.tList = tList;
        this.getImageCallListener = getImageCallListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tournament_child,null,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        TournamentFragment.Tournament tournament = tList.get(position);
        holder.tvTName.setText(tournament.getName());
        holder.tvtLocation.setText(tournament.getAddress());


        holder.img_banner.setBackgroundColor(tournament.getBanner());
        holder.image.setBorderColor(tournament.getLogo());
        holder.tvdate.setText(tournament.getDate());

        holder.mb_add_team.setOnClickListener(v -> {
            getImageCallListener.addTeamLogo();
        });
        holder.li_teamCount.setOnClickListener(v -> {
            if(value<=5){
                mContext.startActivity(new Intent(mContext, TeamListActivity.class).putExtra("Count",value));
            }else{
                mContext.startActivity(new Intent(mContext, TeamListActivity.class).putExtra("Count",5));
            }

        });


        holder.itemView.setOnClickListener(v -> {
            mContext.startActivity(new Intent(mContext, TournamentDetailsActivity.class).putExtra("color"
                            ,tournament.getBanner())
                    .putExtra("Name",tournament.getName()).putExtra("Address",tournament.getAddress())
                    .putExtra("Date",tournament.getDate()));
        });

    }

    @Override
    public int getItemCount() {
        return tList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        private MaterialButton mb_add_team;
        private TextView tvtNumberOfTeam;
        private LinearLayout li_teamCount;

        private TextView tvTName;
        private TextView tvtLocation;
        private TextView tvdate;

        private RoundedImageView img_banner;
        private CircleImageView image;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mb_add_team = itemView.findViewById(R.id.mb_add_team);
            tvtNumberOfTeam = itemView.findViewById(R.id.tvtNumberOfTeam);
            li_teamCount = itemView.findViewById(R.id.li_teamCount);
            tvTName = itemView.findViewById(R.id.tvTName);
            tvtLocation = itemView.findViewById(R.id.tvtLocation);
            tvdate = itemView.findViewById(R.id.tvdate);

            img_banner = itemView.findViewById(R.id.img_banner);
            image = itemView.findViewById(R.id.image);
        }
    }

    public interface getImageCallListener{
        void addTeamLogo();
    }
}
