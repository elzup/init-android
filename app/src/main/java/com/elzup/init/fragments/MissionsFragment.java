package com.elzup.init.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elzup.init.R;
import com.elzup.init.managers.SessionStore;
import com.elzup.init.models.MissionEntity;
import com.elzup.init.models.SessionEntity;
import com.elzup.init.network.InitService;
import com.elzup.init.network.InitServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MissionsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private InitService initService;
    private static final String TAG = MissionsFragment.class.getSimpleName();
    private List<MissionEntity> missionEntities;
    private MissionsRecyclerViewAdapter adapter;
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
        missionEntities.add(new MissionEntity(0, "hoge", "description", 20));
        adapter = new MissionsRecyclerViewAdapter(missionEntities);
        SessionEntity session = SessionStore.getSession();
        initService = InitServiceGenerator.createService(session.getAccessToken());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_missions_list, container, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                try {
                    Response<List<MissionEntity>> response = initService.getMissions().execute();
                    missionEntities.addAll(response.body());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        }.execute();

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(MissionEntity item);
    }
}
