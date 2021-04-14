package com.adityabrian.githubapp.Data.Lokal

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavorituserDAO {

    @Insert
    suspend fun addToFavorit(favoritUser: FavoritUser)

    /** untuki menampilkan list nya*/
    @Query("SELECT * FROM favorit_user")
    fun getFavoritUser() : LiveData<List<FavoritUser>>

    /** melakukan pengecekan apakah user tersebut telah difavoritkan oleh kita*/
    @Query("SELECT count(*) FROM favorit_user WHERE favorit_user.id = :id")
    suspend fun checUser(id : Int) : Int

    @Query("DELETE FROM favorit_user WHERE favorit_user.id = :id")
    suspend fun removeFromFavorit(id:Int) : Int  /** kita buat database nya*/

    /** untuk content provider kita tidak memerlukan nilai balik, tetapi menggunakan Cursor*/
    @Query("SELECT * FROM favorit_user")
    fun findAll() : Cursor
    
}