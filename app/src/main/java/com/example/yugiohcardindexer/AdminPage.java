package com.example.yugiohcardindexer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminPage extends AppCompatActivity {

    private Button mCreateUserButton;

    private Button mDeleteUserButton;

    private Button mBackButton;

    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        mCreateUserButton = findViewById(R.id.createUserButton);

        mDeleteUserButton = findViewById(R.id.deleteUserButton);

        mBackButton = findViewById(R.id.backButton);

        mCreateUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CreateAccount.intentFactory(getApplicationContext());

                startActivity(intent);
            }
        });

        mDeleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = DeleteAccounts.intentFactory(getApplicationContext());

                startActivity(intent);
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.intentFactory(getApplicationContext(), mUser.getUserId());
                startActivity(intent);
            }
        });
    }





    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, AdminPage.class);

        return intent;
    }
}