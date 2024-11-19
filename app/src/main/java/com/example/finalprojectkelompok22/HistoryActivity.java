package com.example.finalprojectkelompok22;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalprojectkelompok22.Adapter.HistoryAdapter;
import com.example.finalprojectkelompok22.Model.Attendance;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView historyRecyclerView;
    private HistoryAdapter historyAdapter;
    private List<Attendance> attendancesList= new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
            currentUserId = currentUser.getUid();
        } else {
            Toast.makeText(this, "Pengguna belum login", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new HistoryAdapter(attendancesList);
        historyRecyclerView.setAdapter(historyAdapter);

        loadAttendanceHistory();
    }

    private void loadAttendanceHistory() {
        db.collection("Attendance")
                .whereEqualTo("userId",currentUserId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        attendancesList.clear();
                        for(QueryDocumentSnapshot document : task.getResult()) {
                            try {
                                Attendance attendance = document.toObject(Attendance.class);
                                Log.d("HistoryActivity", "Attendance data: " + attendance );
                                attendancesList.add(attendance);
                            } catch (Exception e) {
                                Log.e("HistoryActivity", "Error parsing document to Attendance object", e);
                            }
                        }
                        historyAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("HistoryActivity", "Error getting documents:", task.getException());
                        Toast.makeText(this,"Gagal memuat data absensi", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}