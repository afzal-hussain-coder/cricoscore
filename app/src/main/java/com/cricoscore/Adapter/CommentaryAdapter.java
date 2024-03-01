package com.cricoscore.Adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cricoscore.R;
import com.cricoscore.databinding.CommentaryListChildBinding;
import com.cricoscore.databinding.ShortAreaSubcategoryChildBinding;
import com.cricoscore.model.BattingStyleModel;

import java.util.ArrayList;

public class CommentaryAdapter extends RecyclerView.Adapter<CommentaryAdapter.MyViewHolder>{
    private final Context mContext;
   // ArrayList<BattingStyleModel> midWicketList;


    public CommentaryAdapter(Context mContext){
        this.mContext=mContext;
       // this.midWicketList = midWicketList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(CommentaryListChildBinding.inflate(LayoutInflater.from(mContext), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        CommentaryListChildBinding commentaryListChildBinding;

        public MyViewHolder(@NonNull CommentaryListChildBinding binding) {
            super(binding.getRoot());
            commentaryListChildBinding = binding;


        }


    }

}
