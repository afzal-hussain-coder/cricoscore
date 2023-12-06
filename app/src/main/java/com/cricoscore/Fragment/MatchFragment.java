package com.cricoscore.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cricoscore.Activity.YourMatchTeamListActivity;
import com.cricoscore.Activity.YourTeamListActivity;
import com.cricoscore.Adapter.MatchAdapter;
import com.cricoscore.CustomeCamera.CustomeCameraActivity;
import com.cricoscore.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MatchFragment extends Fragment {

    RecyclerView rv_home;
    CircleImageView iv_team_logo;
    public static Uri image_uri=null;

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

        rv_home.setAdapter(new MatchAdapter(getActivity(), getMatchList(), new MatchAdapter.getImageCallListener() {
            @Override
            public void addTeamLogo() {
                showBottomSheetDialog(null);
            }
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(image_uri!=null){
            iv_team_logo.setImageURI(image_uri);
            image_uri = null;
        }
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

    public void showBottomSheetDialog(Uri image_uri) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.add_team_bottom_dialog);

        RelativeLayout rl_team_logo = bottomSheetDialog.findViewById(R.id.rl_team_logo);
        TextView tv_select_team_from_list = bottomSheetDialog.findViewById(R.id.tv_select_team_from_list);
        iv_team_logo = bottomSheetDialog.findViewById(R.id.iv_team_logo);
        TextInputEditText edit_text_teamName = bottomSheetDialog.findViewById(R.id.edit_text_teamName);
        TextInputEditText edit_text_city = bottomSheetDialog.findViewById(R.id.edit_text_city);
        MaterialButton mb_submit = bottomSheetDialog.findViewById(R.id.mb_submit);

        rl_team_logo.setOnClickListener(view -> {
            getActivity().startActivity(new Intent(getActivity(), CustomeCameraActivity.class)
                    .putExtra("FROM","MatchFragment"));
        });


        tv_select_team_from_list.setOnClickListener(v -> {
            getActivity().startActivity(new Intent(getActivity(), YourMatchTeamListActivity.class));
            bottomSheetDialog.dismiss();
        });

        mb_submit.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();

    }

}