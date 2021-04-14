package com.adityabrian.githubapp.Data.Lokal

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorit_user")
data class FavoritUser(
    val login : String,
    @PrimaryKey
    val id :Int, /** si Id ini dijadikan untuk primary key*/
    val avatar_url : String
):Serializable
