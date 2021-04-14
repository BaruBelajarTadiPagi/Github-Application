package com.adityabrian.consumerapp.UI.Favorit

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adityabrian.consumerapp.Model.User
import com.adityabrian.consumerapp.UI.Main.DatabaseContract
import com.adityabrian.consumerapp.UI.Main.MappingHelper

class FavoritViewModel(application: Application) : AndroidViewModel(application) {

    /** Mapping helper kita akses menggunakan View Model disini*/
    private var list = MutableLiveData<ArrayList<User>>()

    fun setFavoritUser(context: Context) {
        /** parameter context karena kita akan menggunakan content Resolver*/
        val cursor = context.contentResolver.query(
            DatabaseContract.FavoritUserColums.CONTENT_URL,
            null,
            null,
            null,
            null
        )
        /** kita convert dulu cursornya menjadi arraylist*/
        val listConverted = MappingHelper.mapCursorToArrayList(cursor)

        /** kita masukkan list yang sudah kita convert*/
        list.postValue(listConverted)
        /** kemudian kita buat fun getFavoritUsernya*/
    }

    /** fun get favorit usernya*/
    fun getFavoritUser(): LiveData<ArrayList<User>> {
        return list
    }
}