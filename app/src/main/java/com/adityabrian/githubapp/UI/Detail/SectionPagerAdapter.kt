package com.adityabrian.githubapp.UI.Detail

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.adityabrian.githubapp.R

class SectionPagerAdapter(private val mCtx : Context, fn : FragmentManager, data : Bundle) : FragmentPagerAdapter(fn, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentBundle : Bundle

    init {
        fragmentBundle = data
    }

    /** kita tambahkan anotasi String Res*/
    @StringRes
    private val TAB_TITTLES = intArrayOf(R.string.tab_1, R.string.tab_2)

    override fun getCount(): Int = 2  /** kita set 2 karena hanya ada Followers dan Following*/

    /** Untuk Tabs Tittle nya kita buat dulu String Resource di Res*/

    override fun getItem(position: Int): Fragment {
        /** kita buat Fragment Dulu*/
        var fragment : Fragment? = null

        when(position){
            /**posistion kan ada 0 dan 1*/
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle /** jadi kita masukkan fragmentnya di argumen*/
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mCtx.resources.getString(TAB_TITTLES[position])
    }

}