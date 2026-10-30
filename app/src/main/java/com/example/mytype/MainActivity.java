package com.example.mytype;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.List;
import java.util.ArrayList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private Exercise adapter;
    private List<Exercise> list = new ArrayList<>();
    private EditText etName, etDuration, etGroup, etSearch;
    private CheckBox cbCompleted;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper =new DBHelper(this);
        etName =findViewById(R.id.etname);
        etDuration =findViewById(R.id.etduration);
        etGroup =findViewById(R.id.etgroub);
        etSearch =findViewById(R.id.etsearch);
        cbCompleted =findViewById(R.id.cbcompleted);
        RecyclerView rv=findViewById(R.id.recyclview);

                adapter=new Exercise(list,this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        findViewById(R.id.btadd).setOnClickListener(v -> addExercise());

        // بحث فوري
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int a, int b, int c) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override public void onTextChanged(CharSequence s, int a, int b, int c) {
                load(s.toString().trim());
            }
        });

        load("");
    }

    private void addExercise() {
        String name = etName.getText().toString().trim();
        String durS = etDuration.getText().toString().trim();

        if (name.isEmpty()) { etName.setError("الاسم إجباري"); return; }
        if (durS.isEmpty()) { etDuration.setError("المدة مطلوبة"); return; }

        int dur;
        try { dur = Integer.parseInt(durS); }
        catch (NumberFormatException e) { etDuration.setError("رقم غير صالح"); return; }
        if (dur <= 0) { etDuration.setError("يجب > 0"); return; }

        long id = dbHelper.insert(name, dur, cbCompleted.isChecked(),
                etGroup.getText().toString().trim());

        if (id > 0) {
            Toast.makeText(this, "تمت الإضافة", Toast.LENGTH_SHORT).show();
            etName.setText(""); etDuration.setText("");
            etGroup.setText(""); cbCompleted.setChecked(false);
            load(etSearch.getText().toString().trim());
        }
    }

    private void load(String keyword) {
        list = keyword.isEmpty() ? dbHelper.getAllOrdered() : dbHelper.search(keyword);
        adapter.updateData(list);
    }

    // يُستدعى من الـ Adapter عند تغيير الـ CheckBox
    public void onCheckedChanged(Exercise ex, boolean checked) {
        dbHelper.updateCompleted(ex.getId(), checked);
        ex.setCompleted(checked);
    }

    // ضغط مطوّل → تأكيد → حذف
    public void onLongPress(Exercise ex) {
        new AlertDialog.Builder(this)
                .setTitle("تأكيد الحذف")
                .setMessage("هل تريد حذف \"" + ex.getName() + "\"؟")
                .setPositiveButton("حذف", (d, w) -> {
                    dbHelper.delete(ex.getId());
                    load(etSearch.getText().toString().trim());
                    Toast.makeText(this, "تم الحذف", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("إلغاء", null)
                .show();
    }





    }
