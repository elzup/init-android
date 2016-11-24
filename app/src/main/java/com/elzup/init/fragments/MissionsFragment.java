package com.elzup.init.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elzup.init.MainActivity;
import com.elzup.init.R;
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
    private RecyclerView recyclerView;

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
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_missions, container, false);
        recyclerView.setAdapter(adapter);
        initData();
        return recyclerView;
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
        MainActivity activity = (MainActivity) getActivity();
        activity.setTitle("ミッション一覧");
        activity.getFabCheck().setVisibility(View.INVISIBLE);
        activity.getFabPlus().setVisibility(View.VISIBLE);
        activity.getFabPlus().setOnClickListener(view -> Snackbar.make(view, "Add action.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    private void initData() {
        if (!missionEntities.isEmpty()) {
            return;
        }
        initService.getMissions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<MissionEntity>>() {
                    @Override
                    public void call(List<MissionEntity> items) {
                        missionEntities.addAll(items);
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
