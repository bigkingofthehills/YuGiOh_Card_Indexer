package com.example.yugiohcardindexer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeleteAccounts extends AppCompatActivity {

    private Button mBackButton;

    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_accounts);

        mBackButton = findViewById(R.id.backButton);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.intentFactory(getApplicationContext(), mUser.getUserId());
                startActivity(intent);
            }
        });
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, DeleteAccounts.class);

        return intent;
    }
}