package com.elzup.init.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.elzup.init.MainActivity;
import com.elzup.init.R;
import com.elzup.init.databinding.FragmentMissionsBinding;
import com.elzup.init.managers.SessionStore;
import com.elzup.init.models.MissionEntity;
import com.elzup.init.models.SessionEntity;
import com.elzup.init.network.InitService;
import com.elzup.init.network.InitServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MissionsFragment extends Fragment implements OnRecyclerListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private InitService initService;
    private static final String TAG = MissionsFragment.class.getSimpleName();
    private List<MissionEntity> missionEntities;
    private MissionsCellAdapter adapter;
    private RelativeLayout relativeLayout;
    private RecyclerView recyclerView;
    private MainActivity activity;
    private FragmentMissionsBinding binding;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MissionsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MissionsFragment newInstance(int columnCount) {
        MissionsFragment fragment = new MissionsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        missionEntities = new ArrayList<>();
        adapter = new MissionsCellAdapter(missionEntities, this);
        SessionEntity session = SessionStore.getSession();
        initService = InitServiceGenerator.createService(session.getAccessToken());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fragment_missions, container, false);
        recyclerView = (RecyclerView) relativeLayout.findViewById(R.id.list);
        recyclerView.setAdapter(adapter);

        // HACK: 再描画時は差分のみ表示したい
        initData();
        return relativeLayout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onRecyclerClicked(View v, int position) {
        MissionEntity mission = missionEntities.get(position);
        getActivity().getSupportFragmentManager().beginTransaction().replace(
                R.id.content_main,
                MissionDetailFragment.newInstance(mission.getId())
        ).addToBackStack(TAG).commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity) getActivity();
        activity.setTitle("ミッション一覧");
        binding = FragmentMissionsBinding.bind(getView());
        binding.setFragment(this);
    }

    public void onCreateButtonClick(View view) {
        Log.d(TAG, "onCreateButtonClick: Clicked");
        getActivity().getSupportFragmentManager().beginTransaction().replace(
                R.id.content_main,
                MissionCreateFragment.newInstance()
        ).addToBackStack(TAG).commit();
    }

    private void initData() {
        missionEntities.clear();
        initService.getMissions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<MissionEntity>>() {
                    @Override
                    public void call(List<MissionEntity> items) {
                        missionEntities.addAll(items);
                        adapter.notifyDataSetChanged();
                    }
                }, throwable -> {
                    Log.e(TAG, "initData: ", throwable);
                });
    }
}
