package com.example.mytype;
  import android.view.LayoutInflater;
  import android.view.View;
  import android.view.ViewGroup;
  import android.widget.*;

  import androidx.annotation.NonNull;
  import androidx.recyclerview.widget.RecyclerView;
  import java.util.List;



public class ExerciseAdapter
        extends RecyclerView.Adapter<ExerciseAdapter.VH> {


    private List<Exercise> list;
    private final MainActivity activity;

    public ExerciseAdapter(List<Exercise> list, MainActivity activity) {
        this.list = list; this.activity = activity;
    }
    public void updateData(List<Exercise> newList) {
        this.list = newList; notifyDataSetChanged();
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int t) {
        View v = LayoutInflater.from(p.getContext())
                .inflate(R.layout.activity_item, p, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        Exercise ex = list.get(pos);
        h.tvName.setText(ex.getName());
        h.tvDuration.setText("المدة: " + ex.getDuration() + " دقيقة");
        h.tvGroup.setText("العضلة: " + ex.getMuscleGroup());

        // مهم: إزالة المستمع قبل setChecked لتجنّب التداخل عند إعادة الاستخدام
        h.cb.setOnCheckedChangeListener(null);
        h.cb.setChecked(ex.isCompleted());
        h.cb.setOnCheckedChangeListener((b, checked) -> {
            int p = h.getAdapterPosition();
            if (p != RecyclerView.NO_POSITION)
                activity.onCheckedChanged(list.get(p), checked);
        });

        h.itemView.setOnLongClickListener(v -> {
            int p = h.getAdapterPosition();
            if (p != RecyclerView.NO_POSITION) activity.onLongPress(list.get(p));
            return true;
        });
    }

    @Override public int getItemCount() { return list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvDuration, tvGroup;
        CheckBox cb;

        VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            tvDuration = v.findViewById(R.id.tvDuration);
            tvGroup = v.findViewById(R.id.tvgroub);
            cb = v.findViewById(R.id.cbcompleted);
        }
    }}