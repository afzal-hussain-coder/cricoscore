package com.cricoscore.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cricoscore.Activity.YourPlayerListActivity;
import com.cricoscore.Activity.YourTeamListActivity;
import com.cricoscore.Adapter.TournamentAdapter;
import com.cricoscore.CustomeCamera.CustomeCameraActivity;
import com.cricoscore.R;
import com.cricoscore.Utils.SelectTournamentType;
import com.cricoscore.Utils.Toaster;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class TournamentFragment extends Fragment {

    RecyclerView rv_home;
    public static Uri image_uri=null;
    TextInputEditText edit_text_teamName;
    TextInputEditText edit_text_city;
    CircleImageView iv_team_logo;



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

        rv_home.setAdapter(new TournamentAdapter(getActivity(), getTournamentList(), new TournamentAdapter.getImageCallListener() {
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


//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        super.onSaveInstanceState(savedInstanceState);
//        savedInstanceState.putString("editTextName", edit_text_teamName.getText().toString());
//        savedInstanceState.putString("editTextCity", edit_text_city.getText().toString());
//
//    }
//
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//
//        if(savedInstanceState!=null){
//            Toaster.customToast(savedInstanceState.getString("editTextName"));
//            edit_text_teamName.setText(savedInstanceState.getString("editTextName"), TextView.BufferType.EDITABLE);
//            edit_text_city.setText(savedInstanceState.getString("editTextCity"), TextView.BufferType.EDITABLE);
//        }
//
//
//    }



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

    public void showBottomSheetDialog(Uri image_uri) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.add_team_bottom_dialog);

        RelativeLayout rl_team_logo = bottomSheetDialog.findViewById(R.id.rl_team_logo);
        TextView tv_select_team_from_list = bottomSheetDialog.findViewById(R.id.tv_select_team_from_list);
        iv_team_logo = bottomSheetDialog.findViewById(R.id.iv_team_logo);
        edit_text_teamName = bottomSheetDialog.findViewById(R.id.edit_text_teamName);
        edit_text_city = bottomSheetDialog.findViewById(R.id.edit_text_city);
        MaterialButton mb_submit = bottomSheetDialog.findViewById(R.id.mb_submit);

        rl_team_logo.setOnClickListener(view -> {
            getActivity().startActivity(new Intent(getActivity(), CustomeCameraActivity.class)
                    .putExtra("FROM","TournamentFragment"));
        });

        tv_select_team_from_list.setOnClickListener(v -> {
            getActivity().startActivity(new Intent(getActivity(), YourTeamListActivity.class));
            bottomSheetDialog.dismiss();
        });

        mb_submit.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();

    }

}

