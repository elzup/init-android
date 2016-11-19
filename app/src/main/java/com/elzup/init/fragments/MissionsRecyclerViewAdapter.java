package com.elzup.init.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.elzup.init.BR;
import com.elzup.init.R;
import com.elzup.init.models.MissionEntity;

import java.util.List;


public class MissionsRecyclerViewAdapter extends RecyclerView.Adapter<MissionsRecyclerViewAdapter.ViewHolder> implements MissionsFragment.OnListFragmentInteractionListener {

    private static final String TAG = MissionsRecyclerViewAdapter.class.getSimpleName();
    private List<MissionEntity> items;

    public MissionsRecyclerViewAdapter(List<MissionEntity> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // DataBinding
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_missions, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MissionEntity item = items.get(position);
        holder.getBinding().setVariable(BR.mission, item);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onListFragmentInteraction(MissionEntity item) {
        Log.i(TAG, "tapped " + item.getTitle());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public ViewHolder(View v) {
            super(v);
            this.binding = DataBindingUtil.bind(v);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }
    }
}
