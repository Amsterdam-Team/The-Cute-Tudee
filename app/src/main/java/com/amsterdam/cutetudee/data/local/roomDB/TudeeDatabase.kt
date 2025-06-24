package com.amsterdam.cutetudee.data.local.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amsterdam.cutetudee.data.local.dao.CategoryDao
import com.amsterdam.cutetudee.data.local.dao.TaskDao
import com.amsterdam.cutetudee.data.local.entity.CategoryEntity
import com.amsterdam.cutetudee.data.local.entity.TaskEntity

@Database(entities = [CategoryEntity::class, TaskEntity::class], version = 1)
abstract class TudeeDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun taskDao(): TaskDao

    companion object {
        private const val DATABASE_NAME = "TudeeDatabase"

        @Volatile
        private var instance: TudeeDatabase? = null

        fun getInstance(context: Context): TudeeDatabase {
            return instance ?: synchronized(this) {
                buildDatabase(context).also {
                    instance = it
                }
            }
        }

        fun buildDatabase(context: Context): TudeeDatabase {
            return Room.databaseBuilder(context, TudeeDatabase::class.java, DATABASE_NAME)
                .createFromAsset("database/$DATABASE_NAME.db")
                .build()
        }
    }
}