package com.example.yugiohcardindexer;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.yugiohcardindexer.db.AppDatabase;

import java.util.Date;

@Entity(tableName = AppDatabase.CARDLIST_TABLE)
public class CardList {

    @PrimaryKey(autoGenerate = true)
    public int mListId;

    private String mCardName;
    private int mCardAttack;
    private int mCardDefense;

    public Date mDate;

    private int mUserId;

    public CardList(String cardName, int cardAttack, int cardDefense, int userId) {
        mCardName = cardName;
        mCardAttack = cardAttack;
        mCardDefense = cardDefense;

        mDate = new Date();

        mUserId = userId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public int getListId() {
        return mListId;
    }

    public void setListId(int mListId) {
        this.mListId = mListId;
    }

    public String getCardName() {
        return mCardName;
    }

    public void setCardName(String mCardName) {
        this.mCardName = mCardName;
    }

    public int getCardAttack() {
        return mCardAttack;
    }

    public void setCardAttack(int mCardAttack) {
        this.mCardAttack = mCardAttack;
    }

    public int getCardDefense() {
        return mCardDefense;
    }

    public void setCardDefense(int mCardDefense) {
        this.mCardDefense = mCardDefense;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    @Override
    public String toString() {
        String output;

        output = mCardName + " : " + mCardAttack + " : " + mCardDefense;
        output += "\n";
        output += getDate();

        return output;
    }
}
