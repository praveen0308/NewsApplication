package com.example.newsapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapplication.databinding.ActivityMainBinding
import com.example.newsapplication.retrofit.Article
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NewsAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val newsViewModel by viewModels<NewsViewModel>()
    private lateinit var adapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        adapter = NewsAdapter(this)

        newsViewModel.list.observe(this) {
            adapter.submitData(lifecycle, it)
        }

        adapter.addLoadStateListener { state ->

            when (state.refresh) {
                is LoadState.Loading -> {
                    binding.newsProgress.visibility = View.VISIBLE
                }
                is LoadState.NotLoading -> {
                    binding.newsProgress.visibility = View.GONE
                }
                is LoadState.Error -> {
                    binding.newsProgress.visibility = View.GONE
                    Toast.makeText(this, "Error Occured", Toast.LENGTH_SHORT).show()
                }

            }

        }
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.adapter = adapter
        binding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        binding.viewPager.adapter = adapter


    }

    override fun onItemClick(article: Article) {

    }

    override fun onSwipe(url: String) {
        val intent = Intent(this, WebActivity::class.java).apply {
            putExtra("URL", url)
        }
        startActivity(intent)
    }
}