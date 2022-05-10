package com.example.shopx.userManage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopx.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class Customer_profile_up extends AppCompatActivity {
    EditText name, email, address, phone, password;
    Button delete;
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile_up);
        getSupportActionBar().hide();
        name = findViewById(R.id.nameupdate);
        email = findViewById(R.id.emailupdate);
        address = findViewById(R.id.phoneup);
        phone = findViewById(R.id.addressup);
        password = findViewById(R.id.passup);
        delete = findViewById(R.id.bt_profileup_delete);

        String namex,emailx,addressx,phonex,passwordx;

        namex =getIntent().getStringExtra("username");
        emailx =getIntent().getStringExtra("email");
        phonex =getIntent().getStringExtra("phoneNo");
        addressx =getIntent().getStringExtra("address");
        passwordx =getIntent().getStringExtra("password");

        name.setText(namex);
        email.setText(emailx);
        address.setText(addressx);
        phone.setText(phonex);
        password.setText(passwordx);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(name.getContext());
                builder.setTitle("Are you sure Delete this?");
                builder.setMessage("Delete data can not be recovered");

                builder.setPositiveButton("delete", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        rootNode = FirebaseDatabase.getInstance();
                        reference = rootNode.getReference("users");

                        String username = name.getText().toString().trim();

                        reference.child(username).setValue(null);
                        Toast.makeText(com.example.shopx.userManage.Customer_profile_up.this, "Account Deleted ", Toast.LENGTH_LONG).show();
                        name.setText("");
                        email.setText("");
                        address.setText("");
                        phone.setText("");
                        password.setText("");

                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    }


                });

                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(name.getContext(), "cancelled.", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.show();
            }
        });
    }

    public void UpdatePro(View view) {
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        String username = name.getText().toString();
        String useremail = email.getText().toString();
        String useraddress = address.getText().toString();
        String userphone = phone.getText().toString();
        String userpass = password.getText().toString();

        User user = new User(username, useremail, useraddress, userphone, userpass);

        reference.child(username).setValue(user);
        Toast.makeText(com.example.shopx.userManage.Customer_profile_up.this, "User Details Updated Sucessfully", Toast.LENGTH_LONG).show();
    }

    public void DeleteAccount(View view) {

    }
}