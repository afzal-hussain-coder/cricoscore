package com.cricoscore.Fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cricoscore.Adapter.TournamentAdapter;
import com.cricoscore.R;

import java.util.ArrayList;
import java.util.List;


public class TournamentFragment extends Fragment {

    RecyclerView rv_home;

    public TournamentFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tournament, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_home = view.findViewById(R.id.rv_home);
        rv_home.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_home.setHasFixedSize(true);

        rv_home.setAdapter(new TournamentAdapter(getActivity(),getTournamentList()));
    }


    public List<Tournament> getTournamentList(){
        List<Tournament> tList = new ArrayList<>();
        tList.add(new Tournament(Color.parseColor("#33FFD7"),
                Color.parseColor("#33FFD7"),"Wino Crew Gaming","Harbax Stadium, Delhi Cantt, Delhi - 110010",
                "30-Mar-23 to 31-Sep-23"));
        tList.add(new Tournament(Color.parseColor("#33E9FF"),
                Color.parseColor("#33E9FF"),"Master Blasters","Villa No. 231, Saphire Greens,",
                "01-Apr-23 to 22-july-23"));
        tList.add(new Tournament(Color.parseColor("#C4F0F7"),
                Color.parseColor("#C4F0F7"),"Bat Ball Doomers League","Vidhan Sabha Road, Ammaseoni",
                "9-Jan-23 to 31-Mar-23"));
        tList.add(new Tournament(Color.parseColor("#BBC9F9"),
                Color.parseColor("#BBC9F9"),"North Sea Pro Series","Raipur, Chhatisgarh - 493111 (INDIA)",
                "5-Feb-23 to 23-May-23"));
        tList.add(new Tournament(Color.parseColor("#87A0F5"),
                Color.parseColor("#87A0F5"),"Huddle Buddies Tournament","D-6/10," +
                "Vasant Vihar," +
                "New Delhi - 110 057 (INDIA)","16-Nov-23 to 27-Dec-23"
                ));
        return tList;
    }
    public class Tournament{
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


        public Tournament(int banner, int logo, String name, String address,String date) {
            this.banner = banner;
            this.logo = logo;
            this.name = name;
            this.address = address;
            this.date = date;
        }
    }

}

