package com.elzup.init.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.elzup.init.MainActivity;
import com.elzup.init.R;
import com.elzup.init.databinding.FragmentMissionEditBinding;
import com.elzup.init.managers.SessionStore;
import com.elzup.init.models.MissionEntity;
import com.elzup.init.models.SessionEntity;
import com.elzup.init.models.UserEntity;
import com.elzup.init.network.InitService;
import com.elzup.init.network.InitServiceGenerator;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MissionEditFragment extends Fragment {
    public static final String TAG = MissionEditFragment.class.getSimpleName();
    private static final String MISSION_ID = "missionId";
    private FragmentMissionEditBinding binding;
    private MissionEntity mission;
    private MainActivity activity;
    private InitService initService;
    public boolean isSync;

    public MissionEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MissionEditFragment.
     */
    public static MissionEditFragment newInstance(int missionId) {
        MissionEditFragment fragment = new MissionEditFragment();
        Bundle args = new Bundle();
        args.putInt(MISSION_ID, missionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionEntity session = SessionStore.getSession();
        initService = InitServiceGenerator.createService(session.getAccessToken());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        return inflater.inflate(R.layout.fragment_mission_edit, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Inflate the layout for this fragment
        activity = (MainActivity) getActivity();
        activity.setTitle("ミッション詳細");

        binding = FragmentMissionEditBinding.bind(getView());
        // TODO: Dummy Factory
        mission = new MissionEntity(99, "...", "...", new UserEntity(99, "..."), false);
        binding.setMission(mission);
        binding.setFragment(this);
        int missionId = getArguments().getInt(MISSION_ID);
        initService.getMission(missionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(missionEntity -> {
                    mission = missionEntity;
                    binding.setMission(mission);
                }, throwable -> {
                    Log.e(TAG, "onActivityCreated: ", throwable);
                });

        SessionEntity session = SessionStore.getSession();
        initService = InitServiceGenerator.createService(session.getAccessToken());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onSubmitButtonClick(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        // TODO: Category 固定
        this.initService.updateMission(mission.getId(), mission.getTitle(), mission.getDescription(), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(missionEntity -> {
                    Toast.makeText(this.getContext(), "更新しました。", Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                }, throwable -> {
                });
    }
}

