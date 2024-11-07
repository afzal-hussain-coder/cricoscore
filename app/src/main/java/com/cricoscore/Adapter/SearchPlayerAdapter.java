package com.cricoscore.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cricoscore.R;

import java.util.ArrayList;
import java.util.List;

public class SearchPlayerAdapter extends RecyclerView.Adapter<SearchPlayerAdapter.ViewHolder> {
    private List<String> userList;
    private List<String> userListFull;

    public SearchPlayerAdapter(List<String> userList) {
        this.userList = userList;
        userListFull = new ArrayList<>(userList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewUsername.setText(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public boolean filter(String text) {
        userList.clear();
        if (text.isEmpty()) {
            userList.addAll(userListFull);
        } else {
            text = text.toLowerCase();
            for (String username : userListFull) {
                if (username.toLowerCase().contains(text)) {
                    userList.add(username);
                }
            }
        }
        notifyDataSetChanged();
        return false;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUsername;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
        }
    }
}



