package com.mayburger.football.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DatabaseInitializer(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "evelinesy.db", null, 4) {
    companion object {
        private var instance: DatabaseInitializer? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseInitializer {
            if (instance == null) {
                instance = DatabaseInitializer(ctx.applicationContext)
            }
            return instance as DatabaseInitializer
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(EventFavorites.TABLE_EVENTS, true,
                EventFavorites.EVENT_ID to TEXT + PRIMARY_KEY)
        db.createTable(TeamFavorites.TABLE_TEAMS, true,
                TeamFavorites.TEAM_ID to TEXT + PRIMARY_KEY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(EventFavorites.TABLE_EVENTS, true)
        db.dropTable(TeamFavorites.TABLE_TEAMS, true)
    }
}

// Access property for Context
val Context.database: DatabaseInitializer
    get() = DatabaseInitializer.getInstance(applicationContext)