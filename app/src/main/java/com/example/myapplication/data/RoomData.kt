package com.example.myapplication.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Student::class,UserFB::class],version = 2,exportSchema = true)
abstract class RoomData : RoomDatabase() {
    companion object {
        private var INSTANCE: RoomData? = null

        private  val migration2 = object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `user` (`id` text, `image` TEXT, " +
                        "PRIMARY KEY(`id`))")
            }

        }

        private  val migration = object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table student add column number INTEGER")
            }

        }
        private  val migration1 = object :Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table student add column gender text")
            }

        }



        fun getData(context: Context): RoomData {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, RoomData::class.java, "student-database").addMigrations(migration).addMigrations(migration1).addMigrations(migration2).build()
            }
            return INSTANCE!!
        }

        fun getRoomData() : RoomData{
                return INSTANCE!!
        }
    }

    abstract  fun studentDao (): StudentDao
    abstract  fun userDao (): UserFBDao
}