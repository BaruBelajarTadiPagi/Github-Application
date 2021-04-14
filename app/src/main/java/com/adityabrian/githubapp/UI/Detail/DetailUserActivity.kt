package com.adityabrian.githubapp.UI.Detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.adityabrian.githubapp.databinding.ActivityDetailUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** tangkap dulu intent di MainActivity tadi*/
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)

        val bundle = Bundle()
        /** kita ingin membagi kedua DetailUser Followers di Pager, maka dari itu kita buat bundle nya*/
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        viewModel.setUserDetail(username!!)
        /** yang kita dapatkan dari intent*/
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    /** dibinding layout layout yang ada di layooutnya DetailUserActivity*/
                    TVName.text = it.name
                    TVUsernameProfile.text = it.login
                    TVFollowers.text = "${it.followers} Followers"
                    TVFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(IVProfile)
                    /** setelah itu set juga view pager nya*/
                }
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            /** variabel count untuk menghitung apakah user tsb ada pada db*/
            val count = viewModel.checkUser(id)
            /** kemudian kita tread ke Main trad/Ui tread dengan cara withContext, karena kalau kita set UI tidak bisa di bg
             * maka kita perlu memindahkan Coroutines nya ke Main*/
            withContext(Dispatchers.Main) {
                if (count != null) {
                    /** kalau datanya ketemu dalam database maka kita set*/
                    if (count > 0) {
                        binding.ToggleFavorit.isChecked = true
                        /** alias warnanya merah*/
                        _isChecked = true
                    } else {
                        binding.ToggleFavorit.isChecked = false
                        /** alias warnanya abu abu*/
                        _isChecked = false
                    }
                }
            }
        }

        /** kita akan set untuk meremove dari favoritnya, jadi nanti saat di klik yang tadinya merah jadi abu abu
         * jadi saat ngeclick favorit keadaan true maka akan menghilangkan juga user yang ada dalam database*/
        binding.ToggleFavorit.setOnClickListener {
            _isChecked = !_isChecked
            /** disini kita balik, yang tadinya true sekarang di false*/
            if (_isChecked) {
                viewModel.addToFavorit(username, id, avatarUrl!!)
            } else {
                /** kalau ceknya tidak true*/
                viewModel.removeFromFavorit(id)
            }
            binding.ToggleFavorit.isChecked = _isChecked
            /** jadi secara langsung merubah kondisi dari toggle nya*/
        }

        // setelah menambahkan bundle kita buat followers View model
        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            ViewPager.adapter = sectionPagerAdapter
            Tabs.setupWithViewPager(ViewPager)
        }
    }
}