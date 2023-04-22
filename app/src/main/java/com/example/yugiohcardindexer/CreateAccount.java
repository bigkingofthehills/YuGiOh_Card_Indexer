package com.example.yugiohcardindexer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yugiohcardindexer.db.AppDatabase;
import com.example.yugiohcardindexer.db.CardListDAO;

public class CreateAccount extends AppCompatActivity {

    private Button mBackButton;
    private EditText mUsername;
    private EditText mPassword;

    private CardListDAO mCardListDAO;

    private String mUsernameString;

    private String mPasswordString;

    private Button mCreateAccountButton;

    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mBackButton = findViewById(R.id.backButton);
        mUsername = findViewById(R.id.editTextLoginUserName);
        mPassword = findViewById(R.id.editTextLoginPassword);

        mCreateAccountButton = findViewById(R.id.buttonCreateAccount);

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUsernameString = mUsername.getText().toString();
                mPasswordString = mPassword.getText().toString();

                if(checkForUserInDatabase()){
                    if(!validatePassword()){
                        Toast.makeText(CreateAccount.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        User altUser = new User(mUsernameString, mPasswordString);
                        mCardListDAO.insert(altUser);
                        // I'm not entirely sure why this isn't working? But it crashes my emulator.
                        // The problem might be in my computer not being strong enough?
                        Toast.makeText(CreateAccount.this, "Account created!", Toast.LENGTH_SHORT).show();
                    }
                }
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

    private boolean validatePassword(){
        return mUser.getPassword().equals(mPasswordString);
    }

    private boolean checkForUserInDatabase(){
        mUser = mCardListDAO.getUserByUsername(mUsernameString);
        if(mUser == null){
            Toast.makeText(this, "no user " + mUsernameString + " found", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, CreateAccount.class);

        return intent;
    }



}