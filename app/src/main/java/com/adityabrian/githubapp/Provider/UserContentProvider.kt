package com.adityabrian.githubapp.Provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.Dao
import com.adityabrian.githubapp.Data.Lokal.FavorituserDAO
import com.adityabrian.githubapp.Data.Lokal.UserDatabase

class UserContentProvider : ContentProvider() {

    companion object{
        const val AUTHORITY = "com.adityabrian.githubapp"
        /** untuk table name nya bisa diliat di Entitas > Model FavoritUser*/
        const val TABLE_NAME = "favorit_user"
        const val ID_FAVORIT_USER_DATA = 1 /** karena kita hanya menampilkan list data jadi kita perlu satu saja idnya*/
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, ID_FAVORIT_USER_DATA)
        }
    }
    /** Kita juga perlu mengintansiasi databasenya, SEBELUM ITU kita  mendeklarasikan dulu DAO nya*/
    private lateinit var userDAO: FavorituserDAO

    override fun onCreate(): Boolean {
        userDAO = context?.let { ctx ->
            UserDatabase.getDatabase(ctx)?.favoritUserDAO()
        }!!
        return false
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        var cursor : Cursor?
        when ( uriMatcher.match(uri)){
            ID_FAVORIT_USER_DATA -> {
                /** kita panggil dari userDao*/
                cursor = userDAO.findAll()
                if (context!=null){
                    cursor.setNotificationUri(context?.contentResolver, uri)
                }
            }
            else -> {
                cursor = null
            }
        }
        return cursor
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int { return 0 }

    override fun getType(uri: Uri): String? { return null }

    override fun insert(uri: Uri, values: ContentValues?): Uri? { return null }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int { return 0 }
}