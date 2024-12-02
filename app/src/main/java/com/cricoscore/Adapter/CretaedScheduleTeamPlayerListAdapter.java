package com.cricoscore.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.model.PlayerModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CretaedScheduleTeamPlayerListAdapter extends RecyclerView.Adapter<CretaedScheduleTeamPlayerListAdapter.MyViewHolder>{

    Context mContext;
    itemClickListener itemClickListener;
    int posClick=0;
    List<PlayerModel> tList;
    ArrayList<PlayerModel>addList;
    ArrayList<PlayerModel>updatePlayer;
    public CretaedScheduleTeamPlayerListAdapter(Context mContext, List<PlayerModel> tList, ArrayList<PlayerModel>selectedPayer, itemClickListener itemClickListener){
        this.mContext = mContext;
        this.tList = tList;
        this.itemClickListener = itemClickListener;
        addList = new ArrayList<>();
        this.updatePlayer = selectedPayer;

        Log.d("SizeList",updatePlayer.size()+"");
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.your_player_list_child, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PlayerModel tournament = tList.get(position);
        holder.tvTName.setText(tournament.getName());

        if(tournament.getAvatar().isEmpty()){
            Glide.with(mContext).load(R.drawable.placeholder_user).into(holder.image);
        }else{
            Glide.with(mContext).load(Global.BASE_URL+"/"+tournament.getAvatar()).into(holder.image);
        }


      //  holder.cb.setChecked(false);




//        for(int i =0;i<updatePlayer.size();i++){
//           // Toaster.customToast("Selected Payer Size: " + updatePlayer.get(i).getPlayer_id());
//            if(updatePlayer.get(i).getPlayer_id()== tournament.getPlayer_id()){
//                holder.cb.setChecked(true);
//
//                addList.add(tList.get(position));
//                itemClickListener.checkedItem(addList);
//                break;
//            }
//        }

        if (updatePlayer != null && tList != null && updatePlayer.size() > 0) {
            for (int i = 0; i < updatePlayer.size(); i++) {
                // Check if the player ID matches

               // Log.d("PlaerId",updatePlayer.get(i)+"?"+tournament.getPlayer_id());
                if (updatePlayer.get(i).getPlayer_id() == tournament.getPlayer_id()) {
                    holder.cb.setChecked(true);
                    holder.rl.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_grey));
                    // Add to the selected list
                    addList.add(tList.get(position));
                    itemClickListener.checkedItem(addList);
                    break; // Exit the loop once the match is found
                }
            }
        } else {
            holder.rl.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            // Handle the case where updatePlayer or tList is null or empty
            // For example, log or show an error message
            Log.e("Error", "Player list or tournament list is null or empty.");
        }

//        holder.cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if(isChecked==true){
//                addList.add(tList.get(position));
//            }else{
//                addList.remove(tList.get(position));
//
//            }
//
//            itemClickListener.checkedItem(addList);
//        });


        holder.itemView.setOnClickListener(v -> {

            boolean isChecked = holder.cb.isChecked();

            // Toggle the checked state
            holder.cb.setChecked(!isChecked);
            isChecked = !isChecked; // Update the local variable

            if (isChecked) {
                holder.rl.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_grey));
                // Add team ID if not already present
                addList.add(tList.get(position));
            } else {
                holder.rl.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                // Remove team ID if present
                addList.remove(tList.get(position));
            }

            itemClickListener.checkedItem(addList);

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
        private TextView tvTName;
        private TextView tvtLocation;
        private TextView tvtRole;

        private CircleImageView image;
        CheckBox cb;
        RelativeLayout rl;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cb = itemView.findViewById(R.id.cb);
            tvTName = itemView.findViewById(R.id.tvTName);
            tvtLocation = itemView.findViewById(R.id.tvtLocation);
            image = itemView.findViewById(R.id.image);
            tvtRole = itemView.findViewById(R.id.tvtRole);
            rl = itemView.findViewById(R.id.rl);
        }
    }

    public interface itemClickListener{
        public void checkedItem(ArrayList<PlayerModel>playerModelArrayList);
    }
}
