package com.example.eapple.tripdatacollection;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

@Database(entities = {Attraction.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase INSTANCE;
    private static final Object sLock = new Object();

    public abstract AttractionsDAO attractionsDAO();

    /*
    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .build();

                }
            }
        }
        return INSTANCE;
    }
    */
/*
    /**
     * Migrate from:
     * version 1 - using the SQLiteDatabase API
     * to
     * version 2 - using Room
     */
/*
    @VisibleForTesting
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
           // Create the new table
            database.execSQL(
                    "CREATE TABLE attrs_new (aid INT, name TEXT, logitude DOUBLE, latitude DOUBLE, classification TEXT, description TEXT, imgTitleInTitle TEXT, accessibility TEXT, type TEXT, PRIMARY KEY(aid))");
            // Copy the data
            database.execSQL(
                    "INSERT INTO attrs_new (aid, name, description) SELECT aid, name, details FROM Attraction");
            // Remove the old table
            database.execSQL("DROP TABLE Attraction");
            // Change the table name to the correct one
            database.execSQL("ALTER TABLE attrs_new RENAME TO Attraction");
        }
    };

    public static AppDatabase getinstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "Sample.db")
                        .addMigrations(MIGRATION_1_2)
                        .build();
            }
            return INSTANCE;
        }
    }
    */
}
