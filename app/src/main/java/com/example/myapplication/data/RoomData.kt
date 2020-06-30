package com.example.myapplication.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Student::class,UserFB::class,Cart::class,ProductWatched::class],version = 8,exportSchema = true)
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

        private  val migration5 = object :Migration(4,5){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table user_tb add column image_user text")
            }

        }

        private  val migration6 = object :Migration(5,6){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE `productwatched` (`id` text, `name` text,`price` text" +
                        "PRIMARY KEY(`id`))")
            }

        }

        private  val migration7 = object :Migration(6,7){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table productwatched add column image text")
            }

        }

        private  val migration8 = object :Migration(7,8){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table productwatched add column love text")
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
                INSTANCE = Room.databaseBuilder(context.applicationContext, RoomData::class.java, "concung_database").addMigrations(migration)
                        .addMigrations(migration1).addMigrations(migration2).addMigrations(migration3).addMigrations(migration4)
                        .addMigrations(migration5) .addMigrations(migration6).addMigrations(migration7).addMigrations(migration8).build()
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
    abstract  fun productwatchedDao (): ProductWatchedDao
}