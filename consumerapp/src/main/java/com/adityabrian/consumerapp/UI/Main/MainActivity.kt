package com.adityabrian.consumerapp.UI.Main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityabrian.consumerapp.UI.Favorit.FavoritViewModel
import com.adityabrian.consumerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoritViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /** kita membutuhkan adapter*/

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoritViewModel::class.java)

        binding.apply {
            RVUser.setHasFixedSize(true)
            RVUser.layoutManager = LinearLayoutManager(this@MainActivity)
            RVUser.adapter = adapter
        }

        /** kemudian kita set*/
        viewModel.setFavoritUser(this)

        /** kita set adapternya dengan dengan data kita yang kita observe dari view model*/
        viewModel.getFavoritUser().observe(this, {
            if (it != null) {
                /** disini erorr karena di FavoritUserDAO untuk getFavoritUser dalam List
                 *  sedangkan di UserAdapter listnya yang kita bawa kesini dalam ArrayList
                 *  karena disini kita ingin mengubahnya dengan proses MAPPING
                 *
                 *  kita akan membuat fun untuk mappingnya , dan kita mengembalikannnya dalam variabel list*/
//                val list = mapList(it)
                adapter.setList(it)
            }
        })
    }

    /** KITA TIDAK PERLU LAGI MAPLIST, Karena datanya sudah berubah menjadi Array List langsung*/
}