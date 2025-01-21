package com.cricoscore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.model.BattingLeaderboatrdModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BattingLeaderboardAdapter extends RecyclerView.Adapter<BattingLeaderboardAdapter.ViewHolder> {
    private final List<BattingLeaderboatrdModel> itemList;
    Context mContext;

    public BattingLeaderboardAdapter(Context mContext, List<BattingLeaderboatrdModel> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.batting_leaderboard_child, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BattingLeaderboatrdModel item = itemList.get(position);

        holder.tvPoints.setText(position + 1 + "");

        holder.tvPlayerName.setText(Global.capitalizeFirstLatterOfString(item.getName()));
        holder.tvInningCount.setText(item.getTotal_inning() + "");
        holder.tvSrCount.setText(item.getStrike_rate() + "");
        holder.tvRunsCount.setText(item.getTotal_runs() + "");
        holder.tvSixCount.setText(item.getTotal_sixes() + "");
        holder.tv4sCount.setText(item.getTotal_fours() + "");
        holder.tvAvgCount.setText(item.getAvg_run() + "");
        holder.tv100sCount.setText(item.getTotal_100s() + "");
        holder.tv50sCount.setText(item.getTotal_50s() + "");
        holder.tvHsCount.setText(item.getHighest_score()+"");
        Glide.with(mContext).load(Global.BASE_URL + itemList.get(position).getAvatar()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPlayerName;
        private TextView tvInningCount;
        private TextView tvSrCount;
        private TextView tvHsCount;
        private TextView tvRunsCount;
        private TextView tvAvgCount;
        private TextView tvNOCount;
        private TextView tvSixCount;
        private TextView tv4sCount;
        private TextView tv50sCount;
        private TextView tv100sCount;
        private TextView tvPoints;
        CircleImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPlayerName = itemView.findViewById(R.id.tvPlayerName);
            tvInningCount = itemView.findViewById(R.id.tvInningCount);
            tvSrCount = itemView.findViewById(R.id.tvSrCount);
            tvHsCount = itemView.findViewById(R.id.tvHsCount);
            tvRunsCount = itemView.findViewById(R.id.tvRunsCount);
            tvAvgCount = itemView.findViewById(R.id.tvAvgCount);
            tvNOCount = itemView.findViewById(R.id.tvNOCount);
            tvSixCount = itemView.findViewById(R.id.tvSixCount);
            tv4sCount = itemView.findViewById(R.id.tv4sCount);
            tv50sCount = itemView.findViewById(R.id.tv50sCount);
            tv100sCount = itemView.findViewById(R.id.tv100sCount);
            image = itemView.findViewById(R.id.image);
            tvPoints = itemView.findViewById(R.id.tvPoints);
        }

        public void bind(BattingLeaderboatrdModel item) {


        }
    }
}

