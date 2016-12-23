package com.elzup.init.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.elzup.init.MainActivity;
import com.elzup.init.R;
import com.elzup.init.databinding.FragmentMissionDetailBinding;
import com.elzup.init.managers.SessionStore;
import com.elzup.init.models.MissionEntity;
import com.elzup.init.models.SessionEntity;
import com.elzup.init.models.UserEntity;
import com.elzup.init.network.InitService;
import com.elzup.init.network.InitServiceGenerator;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MissionDetailFragment extends Fragment {
    public static final String TAG = MissionDetailFragment.class.getSimpleName();
    private static final String MISSION_ID = "missionId";
    private FragmentMissionDetailBinding binding;
    private MainActivity activity;
    private MissionEntity mission;
    private InitService initService;
    private UserEntity loginUser;

    public MissionDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param missionId Mission id
     * @return A new instance of fragment MissionItemDetailsFragment.
     */
    public static MissionDetailFragment newInstance(int missionId) {
        MissionDetailFragment fragment = new MissionDetailFragment();
        Bundle args = new Bundle();
        args.putInt(MISSION_ID, missionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginUser = SessionStore.getUser();
        SessionEntity session = SessionStore.getSession();
        initService = InitServiceGenerator.createService(session.getAccessToken());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_mission_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Inflate the layout for this fragment
        binding = FragmentMissionDetailBinding.bind(getView());
        this.mission = new MissionEntity(99, "ゆるゆり", "This is Description !!!!!", new UserEntity(99, "a@mail.com"), false);
        binding.setMission(this.mission);
        binding.setFragment(this);
        activity = (MainActivity) getActivity();
        activity.setTitle("ミッション詳細");

        FloatingActionButton favicon = (FloatingActionButton) getActivity().findViewById(R.id.indicator);

        favicon.startAnimation(AnimationUtils.loadAnimation(this.getActivity(), R.anim.rotate_forward));

        SessionEntity session = SessionStore.getSession();
        InitService initService = InitServiceGenerator.createService(session.getAccessToken());
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
    }

    private MenuItem menuItem;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mission_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!this.loginUser.equals(this.mission.getAuthor())) {
            String message = "作成者にしか出来ません。";
            Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
            return false;
        }
        switch (item.getItemId()) {
            case R.id.action_edit:
                getActivity().getSupportFragmentManager().beginTransaction().replace(
                        R.id.content_main,
                        MissionEditFragment.newInstance(mission.getId())
                ).addToBackStack(TAG).commit();
                break;
            case R.id.action_delete:
                initService.deleteMission(mission.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(missionEntity -> {

                        }, throwable -> {
                            Log.e(TAG, "onOptionsItemSelected: ", throwable);
                        });
                break;
        }
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onCompleteButtonClick(View view) {
        if (mission.isSync()) {
            return;
        }
        mission.setSync(true);
        initService.postMissionComplete(mission.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completeEntity -> {
                    this.mission.setCompleted(true);
                    this.mission.setSync(false);
                    // binding.setMission(mission);
                }, throwable -> {
                    Log.e(TAG, "onCompleteButtonClick: ", throwable);
                });
    }

    public void onUncompleteButtonClick(View view) {
        if (mission.isSync()) {
            return;
        }
        mission.setSync(true);
        initService.postMissionUncomplete(mission.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completeEntity -> {
                    this.mission.setCompleted(false);

                    this.mission.setSync(false);
                    // binding.setMission(mission);
                }, throwable -> {
                    Log.e(TAG, "onUncompleteButtonClick: ", throwable);
                });
    }
}
