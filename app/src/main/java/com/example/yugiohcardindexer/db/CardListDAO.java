package com.example.yugiohcardindexer.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.yugiohcardindexer.CardList;
import com.example.yugiohcardindexer.User;

import java.util.List;

@Dao
public interface CardListDAO {

    @Insert
    void insert(CardList... cardLists);

    @Update
    void update(CardList... cardLists);

    @Delete
    void delete(CardList cardList);

    @Query("SELECT * FROM " + AppDatabase.CARDLIST_TABLE + " ORDER BY  mDate DESC")
    List<CardList> getAllCardLists();

    @Query("SELECT * FROM " + AppDatabase.CARDLIST_TABLE + " WHERE mListId = :listId")
    List<CardList> getCardListsById(int listId);

    @Query("SELECT * FROM " + AppDatabase.CARDLIST_TABLE + " WHERE mUserId = :userId  ORDER BY  mDate DESC")
    List<CardList> getCardListsByUserId(int userId);

    @Insert
    void insert(User...users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + "  WHERE mUserName = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + "  WHERE mUserId = :userId")
    User getUserByUserId(int userId);

}
