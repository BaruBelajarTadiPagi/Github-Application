package com.adityabrian.githubapp.Data.Lokal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities =  [FavoritUser::class],
    version = 1
)
abstract class UserDatabase : RoomDatabase() {
    /** membuat comapanion object agar bisa mengakses Instance nya secara langsung*/
    companion object{
        var INSTANCE : UserDatabase? = null

        /** fun untuk mendapatkan instancenya*/
        fun getDatabase(context: Context) : UserDatabase?{
            if (INSTANCE==null){
                synchronized(UserDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "user_database").build()
                }
            }
            return INSTANCE
        }
    }

    /** LALU KITA BUAT ABSTRACT FUN UNTUK dAO NYA*/
    abstract fun favoritUserDAO() : FavorituserDAO
}