package com.example.mytype;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.VH> {

    private final List<Exercise> list = new ArrayList<>();
    private final MainActivity activity;

    public ExerciseAdapter(MainActivity activity) {
        this.activity = activity;
    }

    public void updateData(List<Exercise> newList) {
        list.clear();
        if (newList != null) {
            list.addAll(newList);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Exercise ex = list.get(position);

        h.tvName.setText(ex.getName());
        h.tvDuration.setText(h.itemView.getContext()
                .getString(R.string.label_duration, ex.getDuration()));

        String group = ex.getMuscleGroup();
        h.tvGroup.setText(group == null || group.trim().isEmpty()
                ? h.itemView.getContext().getString(R.string.label_empty_group)
                : h.itemView.getContext().getString(R.string.label_group, group));

        // إزالة المستمع قبل setChecked لتجنّب التداخل عند إعادة استخدام العنصر
        h.cb.setOnCheckedChangeListener(null);
        h.cb.setChecked(ex.isCompleted());
        h.cb.setOnCheckedChangeListener((b, checked) -> {
            int p = h.getBindingAdapterPosition();
            if (p != RecyclerView.NO_POSITION) {
                activity.onCheckedChanged(list.get(p), checked);
            }
        });

        h.itemView.setOnLongClickListener(v -> {
            int p = h.getBindingAdapterPosition();
            if (p != RecyclerView.NO_POSITION) {
                activity.onLongPress(list.get(p));
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final TextView tvName, tvDuration, tvGroup;
        final CheckBox cb;

        VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            tvDuration = v.findViewById(R.id.tvDuration);
            tvGroup = v.findViewById(R.id.tvgroub);
            cb = v.findViewById(R.id.chitem);
        }
    }
}
