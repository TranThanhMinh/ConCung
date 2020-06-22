package com.example.myapplication.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Student::class,UserFB::class,Cart::class],version = 4,exportSchema = true)
abstract class RoomData : RoomDatabase() {
    companion object {
        private var INSTANCE: RoomData? = null

        private  val migration2 = object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `user` (`id` text, `image` TEXT, " +
                        "PRIMARY KEY(`id`))")
            }
        }

        private  val migration4 = object :Migration(3,4){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `cart` (`id` text, `name` text,`number` integer, `price` text, `image` text" +
                        "PRIMARY KEY(`id`))")
            }
        }

        private  val migration3 = object :Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table user_tb add column name_user text")
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
                INSTANCE = Room.databaseBuilder(context.applicationContext, RoomData::class.java, "concung-database").addMigrations(migration)
                        .addMigrations(migration1).addMigrations(migration2).addMigrations(migration3).addMigrations(migration4).build()
            }
            return INSTANCE!!
        }

        fun getRoomData() : RoomData{
                return INSTANCE!!
        }
    }

    abstract  fun studentDao (): StudentDao
    abstract  fun userDao (): UserFBDao
    abstract  fun cartDao (): CartDao
}