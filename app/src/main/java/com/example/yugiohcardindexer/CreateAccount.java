package com.example.yugiohcardindexer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yugiohcardindexer.db.CardListDAO;

/*
I'm not really sure why this activity doesn't work the way
it is supposed to. If I could get some advice as to how to
make this work in the feedback section, that would be great!
 */
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