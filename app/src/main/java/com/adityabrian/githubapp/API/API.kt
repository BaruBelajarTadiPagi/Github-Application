package com.adityabrian.githubapp.API

import com.adityabrian.githubapp.Data.Model.DetailUserResponse
import com.adityabrian.githubapp.Data.Model.User
import com.adityabrian.githubapp.Data.Model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("search/users")
    @Headers("Authorization:token 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")
    fun getSearchUsers(
        @Query("q") query : String
    ):Call<UserResponse>

    /** kita buat objeknya agar bisa menginstance API nya di view model nanti*/

    /** Request baru untuk Model dari Detail User Response*/
    @GET("users/{username}")
    @Headers("Authorization:token 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")
    fun getUserDetail(
        @Path("username") username : String
    ): Call<DetailUserResponse>

    /** end point untuk followers , KITA TIDAK PERLU MEMBUAT UNTUK FOLLOWINGNYA JADI CUKUP SATU SAJA*/
    @GET("users/{username}/followers")
    @Headers("Authorization:token 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")
    fun getFollowers(
        @Path("username") username : String
    ): Call<ArrayList<User>>
    /** perhatikan endpoint JSON nya followers ini masuk kedalam ArrayList dahulu
     *  KENAPA KOK DATANYA USER? karena kita hanya ingin menampilkan avatar , id dan login*/

    @GET("users/{username}/following")
    @Headers("Authorization:token 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")
    fun getFollowing(
        @Path("username") username : String
    ): Call<ArrayList<User>>
}