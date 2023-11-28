package com.cricoscore.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricoscore.Adapter.MatchAdapter;
import com.cricoscore.R;

import java.util.ArrayList;
import java.util.List;


public class MatchFragment extends Fragment {

    RecyclerView rv_home;
    public MatchFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_home = view.findViewById(R.id.rv_home);
        rv_home.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_home.setHasFixedSize(true);

        rv_home.setAdapter(new MatchAdapter(getActivity(),getMatchList()));
    }

    public List<Match> getMatchList(){
        List<Match> tList = new ArrayList<>();
        tList.add(new Match(Color.parseColor("#c8f5ae"),
                Color.parseColor("#c8f5ae"),"First-class Match","Inderjit Singh Bindra Stadium",
                "30-Mar-23 to 31-Sep-23"));
        tList.add(new Match(Color.parseColor("#d7e09b"),
                Color.parseColor("#d7e09b"),"One day Match","Dr. Y. S. Rajasekhara Reddy International Cricket Stadium",
                "01-Apr-23 to 22-july-23"));
        tList.add(new Match(Color.parseColor("#F1DB9C"),
                Color.parseColor("#F1DB9C"),"Twenty 20 (T20)","Rajiv Gandhi International Cricket Stadium",
                "9-Jan-23 to 31-Mar-23"));
        tList.add(new Match(Color.parseColor("#F4CEC8"),
                Color.parseColor("#F4CEC8"),"One Day Internationals","Vidarbha Cricket Association Stadium",
                "5-Feb-23 to 23-May-23"));
        tList.add(new Match(Color.parseColor("#E6C2EF"),
                Color.parseColor("#E6C2EF"),"Twenty 20 Internationals","Arun Jaitley Cricket Stadium","16-Nov-23 to 27-Dec-23"
        ));
        return tList;
    }
    public class Match{
        public int getBanner() {
            return banner;
        }

        public void setBanner(int banner) {
            this.banner = banner;
        }

        public int getLogo() {
            return logo;
        }

        public void setLogo(int logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        int banner;
        int logo;
        String name="";
        String address="";

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        String date="";


        public Match(int banner, int logo, String name, String address,String date) {
            this.banner = banner;
            this.logo = logo;
            this.name = name;
            this.address = address;
            this.date = date;
        }
    }

}