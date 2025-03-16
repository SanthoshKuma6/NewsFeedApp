package com.task.newsfeedapp.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.newsfeedapp.model.RoomModel
import com.task.newsfeedapp.network.RoomInterface

@Database(entities = [RoomModel::class], version = 1, exportSchema = false)
@TypeConverters
abstract class RoomDao : RoomDatabase() {
    abstract fun roomInterface(): RoomInterface

    companion object {
        private var INSTANCE: RoomDao? = null
        fun getDatabase(context: Context): RoomDao {
            return INSTANCE ?: synchronized(this) {
                val instant = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDao::class.java,
                    "articles_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instant
                instant
            }
        }
    }
}