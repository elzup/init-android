package com.elzup.init.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elzup.init.BR;
import com.elzup.init.R;
import com.elzup.init.models.MissionEntity;

import java.util.List;


public class MissionsCellAdapter extends RecyclerView.Adapter<MissionsCellAdapter.ViewHolder> {

    private static final String TAG = MissionsCellAdapter.class.getSimpleName();
    private List<MissionEntity> items;
    private OnRecyclerListener listener;

    public MissionsCellAdapter(List<MissionEntity> items, OnRecyclerListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // DataBinding
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_missions_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MissionEntity item = items.get(position);
        holder.getBinding().setVariable(BR.mission, item);
        holder.itemView.setOnClickListener(v -> listener.onRecyclerClicked(v, position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return items.size();
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

    public interface OnMissionClick {
        // TODO: Update argument type and name
        public void onMissionClick(MissionEntity item);
    }
}
