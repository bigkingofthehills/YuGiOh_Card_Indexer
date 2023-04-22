package com.example.yugiohcardindexer.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.yugiohcardindexer.CardList;
import com.example.yugiohcardindexer.User;
import com.example.yugiohcardindexer.db.typeConverters.DateTypeConverter;

@Database(entities = {CardList.class, User.class}, version = 2)
@TypeConverters(DateTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DB_NAME = "CARDLIST_DATABASE";
    public static final String CARDLIST_TABLE = "CARDLIST_TABLE";
    public static final String USER_TABLE = "USER_TABLE";

    // I want to establish this as the table that tracks admins, but I'm unsure of how to.
    public static final String ADMIN_TABLE = "ADMIN_TABLE";

    public abstract CardListDAO getCardListDAO();
}
