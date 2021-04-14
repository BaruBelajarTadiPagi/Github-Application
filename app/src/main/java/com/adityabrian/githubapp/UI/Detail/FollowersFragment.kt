package com.adityabrian.githubapp.UI.Detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityabrian.githubapp.R
import com.adityabrian.githubapp.UI.Main.UserAdapter
import com.adityabrian.githubapp.databinding.FragmentFollowBinding

class FollowersFragment : Fragment(R.layout.fragment_follow) {

    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : FollowersViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString() /** setelah dapat usernamenya > set adapternya*/
        _binding = FragmentFollowBinding.bind(view) /** kemdian kita buat Section Pagger untuk mengatur view pager nya*/

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            RVUser.setHasFixedSize(true)
            RVUser.layoutManager = LinearLayoutManager(activity)
            RVUser.adapter = adapter
        }
        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner,{
            if (it!=null){
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /** kita ingin saat melakukan pencarian ada tampilan progressbarnya*/
    private fun showLoading(state : Boolean){
        if (state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}