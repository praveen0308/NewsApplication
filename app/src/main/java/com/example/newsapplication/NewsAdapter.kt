package com.example.newsapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.newsapplication.databinding.TemplateNewsBinding
import com.example.newsapplication.retrofit.Article

class NewsAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Article, NewsAdapter.MyViewHolder>(DIFF_UTIL) {


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!)


        holder.binding.root.setOnTouchListener(object : OnSwipeTouchListener(holder.binding.root.context) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                listener.onSwipe(item.url)

            }

            override fun onSwipeRight() {
                super.onSwipeRight()

//                listener.onSwipe(item.url)
            }

        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            TemplateNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class MyViewHolder(val binding: TemplateNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }



        }

        fun bind(article: Article) {
            binding.apply {
                Glide.with(itemView)
                    .load(article.urlToImage)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)

                textView.text = article.title
                textView2.text = article.description

            }
        }


    }

    interface OnItemClickListener {
        fun onItemClick(article: Article)
        fun onSwipe(url: String)
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}