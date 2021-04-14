package com.adityabrian.consumerapp.UI.Main

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.adityabrian.githubapp"
    const val SCHEME = "content"

    internal class FavoritUserColums : BaseColumns{
        companion object{
            const val TABLE_NAME = "favorit_user"
            const val ID = "id"
            /** ID ini harus disamakan dengan entitas dari FavoritUser di githubapp*/
            const val USERNAME = "login"
            const val AVATAR_URL = "avatar_url"

            val CONTENT_URL = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}