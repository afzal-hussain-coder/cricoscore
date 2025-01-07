package com.cricoscore.Fragment;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cricoscore.Activity.AddTeamActivity;
import com.cricoscore.Activity.YourTeamListActivity;
import com.cricoscore.Adapter.TournamentAdapter;
import com.cricoscore.ApiResponse.Tournament;
import com.cricoscore.ApiResponse.TournamentResponse;
import com.cricoscore.CustomeCamera.CustomeCameraActivity;
import com.cricoscore.R;
import com.cricoscore.Utils.Global;
import com.cricoscore.Utils.SessionManager;
import com.cricoscore.Utils.Toaster;
import com.cricoscore.retrofit.ApiRequest;
import com.cricoscore.retrofit.RetrofitRequest;
import com.cricoscore.view_model.AddTeamViewMode;
import com.cricoscore.view_model.AllTournamentViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TournamentFragment extends Fragment {

    RecyclerView rv_home;
    public static Uri image_uri = null;
    TextInputEditText edit_text_teamName;
    TextInputEditText edit_text_city;
    CircleImageView iv_team_logo;

    AllTournamentViewModel allTournamentViewModel;

    AddTeamViewMode addTeamViewMode;


    public TournamentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allTournamentViewModel = new ViewModelProvider(getActivity()).get(AllTournamentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tournament, container, false);

        rv_home = view.findViewById(R.id.rv_home);
        rv_home.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_home.setHasFixedSize(true);

        allTournamentViewModel.getTournamentListProgress().observe(getActivity(), integer -> {
            if (integer == 0) {
                Global.showLoader(getChildFragmentManager());
            } else {
                Global.hideLoder();
            }
        });

        allTournamentViewModel.getTournamentListData().observe(getActivity(), tournaments -> {
            rv_home.setAdapter(new TournamentAdapter(getActivity(), tournaments, (integer) -> {
                startActivity(new Intent(getActivity(), YourTeamListActivity.class).putExtra("Id",integer+""));
            }));
        });




        /**
         * @AddTeam Result
         * @s --> Check result
         */

        addTeamViewMode = new ViewModelProvider(this).get(AddTeamViewMode.class);

        addTeamViewMode.getAddTournamentResult().observe(getActivity(), responseStatus -> {
            if (responseStatus.isStatus()) {

                new Handler().postDelayed(() -> {
                    Toaster.customToast(responseStatus.getMessage());
                }, 100);

            }
        });

        addTeamViewMode.getSubmitProfileProgress().observe(getActivity(), integer -> {
            if (integer == 0) {
                Global.showLoader(getParentFragmentManager());
            } else {
                Global.hideLoder();
            }
        });

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Global.isOnline(getActivity())) {
                allTournamentViewModel.getTournamentListResponse(SessionManager.getToken());
            } else {
                Global.showDialog(getActivity());
            }
        }

        if (image_uri != null) {
            iv_team_logo.setImageURI(image_uri);
            image_uri = null;
        }



    }

    /*public void showBottomSheetDialog(Uri image_uri) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(R.layout.add_team_bottom_dialog);

        RelativeLayout rl_team_logo = bottomSheetDialog.findViewById(R.id.rl_team_logo);
        TextView tv_select_team_from_list = bottomSheetDialog.findViewById(R.id.tv_select_team_from_list);
        iv_team_logo = bottomSheetDialog.findViewById(R.id.iv_team_logo);
        edit_text_teamName = bottomSheetDialog.findViewById(R.id.edit_text_teamName);
        TextInputLayout filledTeamName = bottomSheetDialog.findViewById(R.id.filledTeamName);
        TextInputLayout filledCity = bottomSheetDialog.findViewById(R.id.filledCity);
        edit_text_city = bottomSheetDialog.findViewById(R.id.edit_text_city);
        MaterialButton mb_submit = bottomSheetDialog.findViewById(R.id.mb_submit);

        rl_team_logo.setOnClickListener(view -> {
            getActivity().startActivity(new Intent(getActivity(), CustomeCameraActivity.class)
                    .putExtra("FROM", "TournamentFragment"));
        });

        tv_select_team_from_list.setOnClickListener(v -> {
            getActivity().startActivity(new Intent(getActivity(), YourTeamListActivity.class));
            bottomSheetDialog.dismiss();
        });


        edit_text_teamName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                filledTeamName.setErrorEnabled(false);
                filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    filledTeamName.setErrorEnabled(false);
                    filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                filledTeamName.setErrorEnabled(false);
                filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        edit_text_teamName.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (edit_text_teamName.getText().length() == 0) {
                    filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });


        edit_text_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                filledCity.setErrorEnabled(false);
                filledCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    filledCity.setErrorEnabled(false);
                    filledCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                filledCity.setErrorEnabled(false);
                filledCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            }
        });

        edit_text_city.setOnFocusChangeListener((v, hasFocus) -> {

            if (hasFocus) {
                filledCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
            } else {
                if (edit_text_city.getText().length() == 0) {
                    filledCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                } else {
                    filledCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
                }
            }
        });


        mb_submit.setOnClickListener(view -> {

            if (edit_text_teamName.getText().toString().isEmpty() || edit_text_teamName.getText().toString().length() < 3
                    || edit_text_teamName.getText().toString().length() > 21) {
                filledTeamName.setErrorEnabled(true);
                filledTeamName.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                filledTeamName.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                filledTeamName.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                filledTeamName.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                filledTeamName.setError(getResources().getString(R.string.team_name));
            } else if (edit_text_city.getText().toString().isEmpty() || edit_text_city.getText().toString().length() > 3) {
                filledCity.setErrorEnabled(true);
                filledCity.setStartIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                filledCity.setErrorIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                filledCity.setBoxStrokeErrorColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                filledCity.setErrorTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
                filledCity.setError(getResources().getString(R.string.city_name));
            } else {


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Global.isOnline(getActivity())) {
                        addTeamViewMode.getAddTeamResponse(SessionManager.getToken(),SessionManager.getUserId().toString(),
                                activityAddTeamBinding.editTextCity.getText().toString().trim(),
                                activityAddTeamBinding.editTextTeamName.getText().toString(),
                                "");
                    } else {
                        Global.showDialog(mActivity);
                    }
                }

                bottomSheetDialog.dismiss();
            }


        });

        bottomSheetDialog.show();

    }
*/
}

