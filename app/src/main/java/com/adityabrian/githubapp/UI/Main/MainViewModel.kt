package com.adityabrian.githubapp.UI.Main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adityabrian.githubapp.API.RetrofitClient
import com.adityabrian.githubapp.Data.Model.User
import com.adityabrian.githubapp.Data.Model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>() /** merupakan isi dari arraylist User itu nanti*/

    fun setSearchUsers(query : String){
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful){
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                    /** jadi kita bisa tau di logcatnya erornya karena apa*/
                }
            })
    }

    /** kita buat getternya juga*/
    fun getSearchUsers():LiveData<ArrayList<User>>{
        return listUsers
    }

    /** setelah itu kuta butuh adapterjuga untuk recycler view*/
}