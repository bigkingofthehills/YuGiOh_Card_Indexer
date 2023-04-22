package com.example.yugiohcardindexer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yugiohcardindexer.db.AppDatabase;
import com.example.yugiohcardindexer.db.CardListDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "com.example.yugiohcardindexer.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.yugiohcardindexer.PREFERENCES_KEY";
    private TextView mMainDisplay;

    private TextView mAdmin;

    private Button mAdminButton;

    private EditText mCardName;
    private EditText mCardAttack;
    private EditText mCardDefense;

    private Button mSubmitButton;

    private CardListDAO mCardListDAO;

    private List<CardList> mCardLists;

    private int mUserId = -1;

    private SharedPreferences mPreferences = null;
    private User mUser;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDatabase();

        checkForUser();

        loginUser(mUserId);

        mMainDisplay = findViewById(R.id.mainYuGiOhCardDisplay);
        mMainDisplay.setMovementMethod(new ScrollingMovementMethod());

        mCardName = findViewById(R.id.mainCardNameEditText);
        mCardAttack = findViewById(R.id.mainCardAttackEditText);
        mCardDefense = findViewById(R.id.mainCardDefenseText);

        mSubmitButton = findViewById(R.id.mainSubmitButton);

        mAdmin = findViewById(R.id.textView_admin);

        mAdminButton = findViewById(R.id.adminButton);

        refreshDisplay();


        mAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminPage.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardList list = getValuesFromDisplay();

                mCardListDAO.insert(list);

                refreshDisplay();
            }
        });


        if(mUser != null && mUser.getUserName().equals("admin2")){
            mAdminButton.setVisibility(View.VISIBLE);
            mAdmin.setVisibility(View.VISIBLE);
        }else{
            mAdminButton.setVisibility(View.GONE);
            mAdmin.setVisibility(View.GONE);
        }
    }

    private void getDatabase() {
        mCardListDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getCardListDAO();
    }

    private void loginUser(int userID) {
        mUser = mCardListDAO.getUserByUserId(userID);
        addUserToPreference(userID);
        invalidateOptionsMenu();
    }

    @Override
    public  boolean onPrepareOptionsMenu(Menu menu) {
        if (mUser != null) {
            MenuItem item = menu.findItem(R.id.logout_button);
            item.setTitle(mUser.getUserName());
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void addUserToPreference(int userId) {
        if (mPreferences == null) {
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }
    private void checkForUser() {

        mUserId = getIntent().getIntExtra(USER_ID_KEY, -1);

        if(mUserId != -1){
            return;
        }

        if (mPreferences == null) {
            getPrefs();
        }

        mUserId = mPreferences.getInt(USER_ID_KEY, -1);

        if(mUserId != -1){
            return;
        }

        List<User> users = mCardListDAO.getAllUsers();
        if(users.size() <= 0){
            User defaultUser = new User("daclink", "dac123");
            User altUser = new User("Armondo", "dac123");
            User testUser = new User("testuser1", "testuser1");
            User adminUser = new User("admin2", "admin2");
            mCardListDAO.insert(defaultUser, altUser);
            mCardListDAO.insert(testUser, adminUser);
        }

        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    private void logoutUser() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clearUserFromIntent();
                clearUserFromPref();
                mUserId = -1;
                checkForUser();
            }
        });
        alertBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Do nothing
            }
        });

        alertBuilder.create().show();
    }

    private void clearUserFromIntent() {
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    private void clearUserFromPref() {
        addUserToPreference(-1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_button:
                logoutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private CardList getValuesFromDisplay(){
        String name = "No record found";
        int attack = 0;
        int defense = 0;

        name = mCardName.getText().toString();

        try{
            attack = Integer.parseInt(mCardAttack.getText().toString());
        } catch (NumberFormatException e){
            Log.d("CARDLIST", "Couldn't convert attack");
        }

        try{
            defense = Integer.parseInt(mCardDefense.getText().toString());
        } catch (NumberFormatException e){
            Log.d("CARDLIST", "Couldn't convert defense");
        }

        CardList list = new CardList(name, attack, defense, mUserId);

        return list;
    }

    private void refreshDisplay(){
        mCardLists = mCardListDAO.getCardListsByUserId(mUserId);

        if(mCardLists.size() <= 0){
            mMainDisplay.setText(R.string.noLogsMessage);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for(CardList list : mCardLists){
            sb.append(list);
            sb.append("\n");
            sb.append("=-=-=-=-=-=-=-=-=-=");
            sb.append("\n");
        }
        mMainDisplay.setText(sb.toString());
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);

        return intent;
    }
}