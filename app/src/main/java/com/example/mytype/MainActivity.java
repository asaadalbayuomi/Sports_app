package com.example.mytype;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private ExerciseAdapter adapter;
    private final List<Exercise> list = new ArrayList<>();

    private EditText etName, etDuration, etGroup, etSearch;
    private CheckBox cbCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        etName = findViewById(R.id.etname);
        etDuration = findViewById(R.id.etduration);
        etGroup = findViewById(R.id.etgroub);
        etSearch = findViewById(R.id.etsearch);
        cbCompleted = findViewById(R.id.cbcompleted);
        RecyclerView rv = findViewById(R.id.recyclview);

        adapter = new ExerciseAdapter(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        findViewById(R.id.btadd).setOnClickListener(v -> addExercise());

        // بحث فوري أثناء الكتابة
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override public void onTextChanged(CharSequence s, int a, int b, int c) {
                load(s.toString().trim());
            }
        });

        load("");
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    private void addExercise() {
        String name = etName.getText().toString().trim();
        String durS = etDuration.getText().toString().trim();

        if (name.isEmpty()) { etName.setError(getString(R.string.msg_name_required)); return; }
        if (durS.isEmpty()) { etDuration.setError(getString(R.string.msg_duration_required)); return; }

        int dur;
        try {
            dur = Integer.parseInt(durS);
        } catch (NumberFormatException e) {
            etDuration.setError(getString(R.string.msg_duration_invalid));
            return;
        }
        if (dur <= 0) { etDuration.setError(getString(R.string.msg_duration_positive)); return; }

        long id = dbHelper.insert(name, dur, cbCompleted.isChecked(),
                etGroup.getText().toString().trim());

        if (id > 0) {
            Toast.makeText(this, R.string.msg_added, Toast.LENGTH_SHORT).show();
            etName.setText("");
            etDuration.setText("");
            etGroup.setText("");
            cbCompleted.setChecked(false);
            load(etSearch.getText().toString().trim());
        } else {
            Toast.makeText(this, R.string.msg_add_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private void load(String keyword) {
        list.clear();
        list.addAll(keyword.isEmpty() ? dbHelper.getAllOrdered() : dbHelper.search(keyword));
        adapter.updateData(list);
    }

    /** يُستدعى من الـ Adapter عند تغيير الـ CheckBox */
    public void onCheckedChanged(Exercise ex, boolean checked) {
        dbHelper.updateCompleted(ex.getId(), checked);
        ex.setCompleted(checked);
    }

    /** ضغط مطوّل على عنصر → تأكيد → حذف */
    public void onLongPress(Exercise ex) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.msg_confirm_delete)
                .setMessage(getString(R.string.msg_delete_question, ex.getName()))
                .setPositiveButton(R.string.bt_delete, (d, w) -> {
                    dbHelper.delete(ex.getId());
                    load(etSearch.getText().toString().trim());
                    Toast.makeText(this, R.string.msg_deleted, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(R.string.bt_cancel, null)
                .show();
    }
}
