package com.elzup.init.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elzup.init.BR;
import com.elzup.init.R;
import com.elzup.init.databinding.FragmentDetailsItemMissionBinding;
import com.elzup.init.managers.SessionStore;
import com.elzup.init.models.MissionEntity;
import com.elzup.init.models.SessionEntity;
import com.elzup.init.network.InitService;
import com.elzup.init.network.InitServiceGenerator;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MissionItemDetailsFragment extends Fragment {
    public static final String TAG = MissionItemDetailsFragment.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MISSION_ID = "missionId";
    private FragmentDetailsItemMissionBinding binding;

    public MissionItemDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param missionId Mission id
     * @return A new instance of fragment MissionItemDetailsFragment.
     */
    public static MissionItemDetailsFragment newInstance(int missionId) {
        MissionItemDetailsFragment fragment = new MissionItemDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(MISSION_ID, missionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        return inflater.inflate(R.layout.fragment_details_item_mission, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Inflate the layout for this fragment
        binding = FragmentDetailsItemMissionBinding.bind(getView());
        binding.setMission(new MissionEntity(99, "ゆるゆり", "This is Description !!!!!", 10, false));

        getActivity().setTitle("ミッション詳細");
        SessionEntity session = SessionStore.getSession();
        InitService initService = InitServiceGenerator.createService(session.getAccessToken());
        int missionId = getArguments().getInt(MISSION_ID);
        initService.getMission(missionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MissionEntity>() {
                    @Override
                    public void call(MissionEntity missionEntity) {
                        Log.d(TAG, missionEntity.getTitle());
//                        binding.setMission(missionEntity);
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
