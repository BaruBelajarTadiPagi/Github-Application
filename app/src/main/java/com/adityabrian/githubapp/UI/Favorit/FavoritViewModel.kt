package com.adityabrian.githubapp.UI.Favorit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.adityabrian.githubapp.Data.Lokal.FavoritUser
import com.adityabrian.githubapp.Data.Lokal.FavorituserDAO
import com.adityabrian.githubapp.Data.Lokal.UserDatabase

class FavoritViewModel(application: Application) : AndroidViewModel(application) {

    /** deklarasikan Dao nya*/
    private var userDAO: FavorituserDAO?
    private var userDB: UserDatabase?

    init {
        userDB = UserDatabase.getDatabase(application)
        userDAO = userDB?.favoritUserDAO()
        /**setelah mendeklarasikan di contructor kita buat fun addToFavorit dibawah*/
    }

    /** fun get favorit usernya*/
    fun getFavoritUser(): LiveData<List<FavoritUser>>? {
        return userDAO?.getFavoritUser()
    }
}