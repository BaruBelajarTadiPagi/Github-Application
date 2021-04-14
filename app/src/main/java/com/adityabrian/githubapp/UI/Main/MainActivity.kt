package com.adityabrian.githubapp.UI.Main

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityabrian.githubapp.Data.Model.User
import com.adityabrian.githubapp.R
import com.adityabrian.githubapp.UI.Detail.DetailUserActivity
import com.adityabrian.githubapp.UI.Favorit.FavoritActivity
import com.adityabrian.githubapp.UI.Setting.SettingActivity
import com.adityabrian.githubapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** di inisialisasikan dulu*/
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUserActivity::class.java).also {
                    /** karena kita memasukkan put ekstra karena di DetailUser membutuhkan usernamenya*/
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    // kita akan mengirimkan juga id nya
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.avatar_url)
                    startActivity(it)
                }
            }

        })
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        binding.apply {
            RVUser.layoutManager = LinearLayoutManager(this@MainActivity)
            RVUser.setHasFixedSize(true)
            RVUser.adapter = adapter

            btnSearch.setOnClickListener {
                searchUsers()
            }
            /** jadi kita juga ingin di keyboard saat kita klik enter maka akan melakukan pencarian juga*/
            ETQuery.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    /** dan ketika enternya ditekan juga diberikan fun searchUsers()*/
                    searchUsers()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
        /** kita set Recycler Viewnya dengan kita get dari data viewModel
         *
         *  MENAMPILKAN RV nya dan MENGHILANGKAN PROGRESS BAR*/
        viewModel.getSearchUsers().observe(this, {
            if (it != null) {
                adapter.setList(it)
                /** artinya kita set adaptenya dari listnya dari API*/
                showLoading(false)
            }
        })

    }

    /** kita akan buat fun untuk Search usernya*/
    private fun searchUsers() {
        binding.apply {
            val query = ETQuery.text.toString()
            if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUsers(query)
        }
    }

    /** kita ingin saat melakukan pencarian ada tampilan progressbarnya*/
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    /** kita inflate onCreateOption Menu disini*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        /** kitainflate diatas return*/
        return super.onCreateOptionsMenu(menu)
    }

    /** kita override juga untuk option fun selected nya*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorit_menu -> {
                Intent(this, FavoritActivity::class.java).also {
                    startActivity(it)
                    /** INGAT KITA TADI SUDAH MELAKUKAN PERUBAHAN DI ENTITAS
                     * JADI KITA UNINSTAL TERLEBIH DAHULU UNTUK APK DI HP KITA*/
                }
            }
            R.id.settings_menu -> {
                Intent(this, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}