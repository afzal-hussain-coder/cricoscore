package com.cricoscore.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cricoscore.R;
import com.cricoscore.databinding.ShortAreaSubcategoryChildBinding;
import com.cricoscore.model.ShortAreaSubCategoryModel;

import java.util.ArrayList;

public class BowlingStyleAdapter extends RecyclerView.Adapter<BowlingStyleAdapter.MyViewHolder>{
    private final Context mContext;
    coachItemClickListener coachItemClickListener;
    ArrayList<String> midWicketList;
    int selectedPosition=-1;


    public BowlingStyleAdapter(Context mContext, ArrayList<String> midWicketList , coachItemClickListener coachItemClickListener){
        this.mContext=mContext;
        this.midWicketList = midWicketList;
        this.coachItemClickListener = coachItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ShortAreaSubcategoryChildBinding.inflate(LayoutInflater.from(mContext), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String s = midWicketList.get(position);
        holder.setItem(s);
    }

    @Override
    public int getItemCount() {
        return midWicketList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        ShortAreaSubcategoryChildBinding shortAreaSubcategoryChildBinding;

        public MyViewHolder(@NonNull ShortAreaSubcategoryChildBinding binding) {
            super(binding.getRoot());
            shortAreaSubcategoryChildBinding = binding;
            shortAreaSubcategoryChildBinding.mcv.setOnClickListener(v -> {
                coachItemClickListener.itemClick(midWicketList.get(getAdapterPosition()));
                selectedPosition=getAdapterPosition();
                notifyDataSetChanged();
            });

        }

        private void setItem(String item) {
            if(selectedPosition== getAdapterPosition()) {
                shortAreaSubcategoryChildBinding.mcv.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                shortAreaSubcategoryChildBinding.ivImage.setColorFilter(ContextCompat.getColor(mContext, R.color.lemon_color));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    shortAreaSubcategoryChildBinding.tvSubCategory.setTextColor(mContext.getColor(R.color.lemon_color));
                    shortAreaSubcategoryChildBinding.tvSubCategory.setTextSize(16);
                }
            }
            else {
                shortAreaSubcategoryChildBinding.mcv.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                shortAreaSubcategoryChildBinding.ivImage.setColorFilter(ContextCompat.getColor(mContext, R.color.dark_grey));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    shortAreaSubcategoryChildBinding.tvSubCategory.setTextColor(mContext.getColor(R.color.dark_grey));
                    shortAreaSubcategoryChildBinding.tvSubCategory.setTextSize(12);
                }
            }

            shortAreaSubcategoryChildBinding.tvSubCategory.setText(item);
        }
    }

    public interface coachItemClickListener{
        void itemClick(String value);

    }
}
