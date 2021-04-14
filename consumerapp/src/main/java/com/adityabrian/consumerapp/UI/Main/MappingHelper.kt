package com.adityabrian.consumerapp.UI.Main

import android.database.Cursor
import com.adityabrian.consumerapp.Model.User

object MappingHelper {

    fun mapCursorToArrayList (cursor: Cursor?) : ArrayList<User>{
        val list = ArrayList<User>()
        if (cursor!=null){
            /** jadi cursor itu ada indexnya, lalu kita atur di while ini*/
            while (cursor.moveToNext() ){
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.FavoritUserColums.ID))
                val username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoritUserColums.USERNAME))
                val avatarUrl = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.FavoritUserColums.AVATAR_URL))
                list.add(
                    /** user yang tereferensi dari FavoritUser yang telah kita copy, sesuai urutan*/
                    User(
                        username,
                        id,
                        avatarUrl
                    )
                )
            }
        }
        return list
    }
}