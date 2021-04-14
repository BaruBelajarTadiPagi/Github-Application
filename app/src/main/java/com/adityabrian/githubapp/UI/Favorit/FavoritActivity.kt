package com.adityabrian.githubapp.UI.Favorit

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityabrian.githubapp.Data.Lokal.FavoritUser
import com.adityabrian.githubapp.Data.Model.User
import com.adityabrian.githubapp.R
import com.adityabrian.githubapp.UI.Detail.DetailUserActivity
import com.adityabrian.githubapp.UI.Main.UserAdapter
import com.adityabrian.githubapp.databinding.ActivityFavoritBinding

class FavoritActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoritBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoritViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritBinding.inflate(layoutInflater)
        setContentView(binding.root) /** kita membutuhkan adapter*/

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoritViewModel::class.java)
        /** setelah itu kita setOnClickCallback */

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@FavoritActivity, DetailUserActivity::class.java).also {
                    /** karena kita memasukkan put ekstra karena di DetailUser membutuhkan usernamenya*/
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    // kita akan mengirimkan juga id nya
                    it.putExtra(DetailUserActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailUserActivity.EXTRA_URL, data.id)
                    startActivity(it)
                }
            }
        })
        binding.apply {
            RVUser.setHasFixedSize(true)
            RVUser.layoutManager = LinearLayoutManager(this@FavoritActivity)
            RVUser.adapter = adapter
        }
        /** kita set adapternya dengan dengan data kita yang kita observe dari view model*/
        viewModel.getFavoritUser()?.observe(this,{
            if (it!=null){
                /** disini erorr karena di FavoritUserDAO untuk getFavoritUser dalam List
                 *  sedangkan di UserAdapter listnya yang kita bawa kesini dalam ArrayList
                 *  karena disini kita ingin mengubahnya dengan proses MAPPING
                 *
                 *  kita akan membuat fun untuk mappingnya , dan kita mengembalikannnya dalam variabel list*/
                val list = mapList(it)
                adapter.setList(list)
            }
        })
    }

    private fun mapList(users: List<FavoritUser>): ArrayList<User> { /** selanjutnya kita perlu membuat option menu untuk favoritnya*/
        val listUsers = ArrayList<User>()
        for (user in users){
            /** ini prsess mapping nya*/
            val userMapped = User(
                user.login,
                user.id,
                user.avatar_url
            )
            /** jika sudah kita tambahkan */
            listUsers.add(userMapped)
        }
        return listUsers
    }
}