package com.example.shopx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopx.userManage.Customer_profile;
import com.example.shopx.userManage.Customer_profile_up;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileLogin<Public> extends AppCompatActivity {

    EditText adminUname,adminUpassword;
    Button adminLoginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_login);

        adminUname = findViewById(R.id.et_cuslogin_username);
        adminUpassword = findViewById(R.id.et_cuslogin_Password);

        adminLoginbtn = findViewById(R.id.bt_cuslogin_login);





        adminLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String adminUsername = adminUname.getText().toString().trim();
                final String adminPasswored = adminUpassword.getText().toString().trim();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                Query checkUser = reference.orderByChild("username").equalTo(adminUsername);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            adminUname.setError(null);

                            String passwordFromDB = dataSnapshot.child(adminUsername).child("password").getValue(String.class);
                            if (passwordFromDB.equals(adminPasswored)) {
                                adminUname.setError(null);

                                String nameFromDB = dataSnapshot.child(adminUsername).child("username").getValue(String.class);
                                String usernameFromDB = dataSnapshot.child(adminUsername).child("email").getValue(String.class);
                                String phoneNoFromDB = dataSnapshot.child(adminUsername).child("address").getValue(String.class);
                                String emailFromDB = dataSnapshot.child(adminUsername).child("phoneNo").getValue(String.class);
                                Intent intent = new Intent(getApplicationContext(), Customer_profile_up.class);
                                intent.putExtra("username", nameFromDB);
                                intent.putExtra("email", usernameFromDB);
                                intent.putExtra("address", emailFromDB);
                                intent.putExtra("phoneNo", phoneNoFromDB);
                                intent.putExtra("password", passwordFromDB);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Successfully User Data Loaded", Toast.LENGTH_SHORT).show();
                            } else {

                                adminUpassword.setError("Wrong Password");
                                adminUpassword.requestFocus();
                            }
                        } else {

                            adminUpassword.setError("No such User exist");
                            adminUname.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

    }




    public void getSignUp(View view) {
        Intent intent = new Intent(com.example.shopx.ProfileLogin.this, Customer_profile.class);
        startActivity(intent);
    }
}
