package com.example.roomessenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

   EditText username, email, password;
   Button registerButton;

   FirebaseAuth auth;
   DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        registerButton = findViewById(R.id.registerButton);

        auth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userText = username.getText().toString();
                String emailText = email.getText().toString();
                String passText = password.getText().toString();

                if (TextUtils.isEmpty(userText) || TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passText)){
                    Toast.makeText(RegisterActivity.this, "You must enter all fields!", Toast.LENGTH_SHORT).show();
                } else if (passText.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters long!", Toast.LENGTH_SHORT).show();
                } else{
                    register(userText, emailText, passText);
                }
            }
        });

    }

    private void register(final String username, String email, String password){

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser fbUser = auth.getCurrentUser();
                            assert fbUser != null;
                            String userID = fbUser.getUid();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);

                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put("id",userID);
                            hashMap.put("username",username);
                            hashMap.put("imageURL","default");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else{
                            Toast.makeText(RegisterActivity.this, "You can't register with that email and password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
