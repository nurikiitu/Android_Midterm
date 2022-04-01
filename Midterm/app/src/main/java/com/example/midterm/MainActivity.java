package com.example.midterm;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private Button logout_btn;
    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private TextView textView;
    private String email;
    private EditText midterm;
    private EditText endterm;
    private EditText my_final;
    private TextView result;
    private Double final_result, res_1, res_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        midterm = findViewById(R.id.midterm);
        endterm = findViewById(R.id.endterm);
        my_final = findViewById(R.id.final1);
        result = findViewById(R.id.result);

        logout_btn = findViewById(R.id.logout_btn);
        mAuth = FirebaseAuth.getInstance();
        textView = findViewById(R.id.textView);

        ref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                email = snapshot.child("email").getValue().toString();
                textView.setText(email);
            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }



    public void show(View view) {
        int mid = Integer.parseInt(midterm.getText().toString());
        int end = Integer.parseInt(endterm.getText().toString());
        int fin = Integer.parseInt(my_final.getText().toString());
        res_1 = ((mid + end) / 2) * 0.6;
        res_2 = fin * 0.4;
        final_result = res_1 + res_2;
        result.setText(final_result.toString());
    }
}