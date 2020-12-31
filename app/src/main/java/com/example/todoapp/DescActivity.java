package com.example.todoapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todoapp.models.TaskToDo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DescActivity extends AppCompatActivity {

    TextView btn_edit, btn_cancel, btn_delete_task, btn_update_task, tv_task_date, tv_list_title;
    EditText et_task_title, et_task_description;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference listRef;
    private DatabaseReference taskRef;
    private String uid;

    private TaskToDo task = new TaskToDo("loading","loading","loading",false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc);

        btn_edit = findViewById(R.id.edit);
        btn_cancel = findViewById(R.id.btn_cancel);
        btn_delete_task = findViewById(R.id.delete_task);
        btn_update_task = findViewById(R.id.update_task);
        et_task_title = findViewById(R.id.a_task_title);
        et_task_description = findViewById(R.id.task_description);
        tv_task_date = findViewById(R.id.task_date);
        tv_list_title = findViewById(R.id.a_task_title);

        updateUI(task);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(DescActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        uid = currentUser.getUid();

        String listId = getIntent().getStringExtra("listId");
        String taskId = getIntent().getStringExtra("taskId");
        listRef = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("lists").child(listId);
        taskRef = listRef.child("tasks").child(taskId);


        listRef.child("title").get().addOnSuccessListener(dataSnapshot -> tv_list_title.setText((String) dataSnapshot.getValue()));
        taskRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                task = dataSnapshot.getValue(TaskToDo.class);
                if(task != null) updateUI(task);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        ImageButton btn_back = findViewById(R.id.back);
        btn_back.setOnClickListener(view -> {
            onBackPressed();
        });

        cancelEdit();

        btn_edit.setOnClickListener(view -> {
            openEdit();
        });
        btn_cancel.setOnClickListener(view -> {
            cancelEdit();
        });
        btn_delete_task.setOnClickListener(view -> {
            taskRef.removeValue();
            onBackPressed();
            Toast.makeText(DescActivity.this, "deleted successfully", Toast.LENGTH_SHORT).show();
        });
        btn_update_task.setOnClickListener(view -> {
            updateData();
            cancelEdit();
            Toast.makeText(DescActivity.this, "updated successfully", Toast.LENGTH_SHORT).show();
        });

    }

    private void updateUI(TaskToDo task) {
        et_task_title.setText(task.getTitle());
        et_task_description.setText(task.getDescription());
        tv_task_date.setText(task.getDate());
    }

    public void openEdit() {
        btn_edit.setVisibility(View.GONE);
        btn_cancel.setVisibility(View.VISIBLE);
        btn_delete_task.setVisibility(View.GONE);
        btn_update_task.setVisibility(View.VISIBLE);
        et_task_title.setEnabled(true);
        et_task_description.setEnabled(true);
        et_task_title.setBackgroundResource(R.drawable.desc_background);
        et_task_description.setBackgroundResource(R.drawable.desc_background);
        tv_task_date.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_edit_24, 0);
    }

    public void cancelEdit() {
        btn_edit.setVisibility(View.VISIBLE);
        btn_cancel.setVisibility(View.GONE);
        btn_delete_task.setVisibility(View.VISIBLE);
        btn_update_task.setVisibility(View.GONE);
        et_task_title.setEnabled(false);
        et_task_description.setEnabled(false);
        et_task_title.setBackgroundColor(Color.WHITE);
        et_task_description.setBackgroundColor(Color.WHITE);
        tv_task_date.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        updateUI(task);
    }

    private void updateData() {
        String title = et_task_title.getText().toString().trim();
        String description = et_task_description.getText().toString().trim();
        String date = tv_task_date.getText().toString().trim();
        if (title.isEmpty()) {
            et_task_title.setError("Please enter title");
            return;
        }
        if (description.isEmpty()) {
            et_task_description.setError("Please enter description");
            return;
        }
        if (date.isEmpty()) {
            tv_task_date.setError("Please enter password");
            return;
        }
        task.setTitle(title);
        task.setDescription(description);
        task.setDate(date);
        taskRef.setValue(task);
    }

}