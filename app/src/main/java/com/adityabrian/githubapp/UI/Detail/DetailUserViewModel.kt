package com.adityabrian.githubapp.UI.Detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adityabrian.githubapp.API.RetrofitClient
import com.adityabrian.githubapp.Data.Lokal.FavoritUser
import com.adityabrian.githubapp.Data.Lokal.FavorituserDAO
import com.adityabrian.githubapp.Data.Lokal.UserDatabase
import com.adityabrian.githubapp.Data.Model.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()

    /** deklarasikan Dao nya*/
    private var userDAO: FavorituserDAO?
    private var userDB: UserDatabase?

    init {
        userDB = UserDatabase.getDatabase(application)
        userDAO = userDB?.favoritUserDAO()
        /**setelah mendeklarasikan di contructor kita buat fun addToFavorit dibawah*/
    }

    fun setUserDetail(username: String) {
        RetrofitClient.apiInstance
            .getUserDetail(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }
            })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> {
        return user // menit 22:59
    }

    fun addToFavorit(username: String, id: Int, avatarUrl: String) {
        /** kenapa kita jalankan di bg, karena kalau diliat di Interface FavoritUserDAO nya
         * milik addToFavorit berupa susspend fun yang dapat dijalankan dengan Coroutines*/
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoritUser(
                username,
                id,
                avatarUrl
            )
            /** kita panggil userDAO nya dan fun addToFavoritnya*/
            userDAO?.addToFavorit(user)
            /** selanjutnya mmebuat susspend fun untuk cek user*/
        }
    }

    /** kita akan melakukan secara background pada MainActivity*/
    suspend fun checkUser(id: Int) = userDAO?.checUser(id)

    fun removeFromFavorit(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDAO?.removeFromFavorit(id)
        }
    }
}