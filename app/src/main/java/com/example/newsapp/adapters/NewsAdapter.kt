package com.example.newsapp.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.ItemArticlePreviewBinding
import com.example.newsapp.models.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val newsAdapterBinding: ItemArticlePreviewBinding) :
        RecyclerView
        .ViewHolder(newsAdapterBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val newsAdapterBinding = ItemArticlePreviewBinding.inflate(layoutInflater, parent, false)
        return ArticleViewHolder(newsAdapterBinding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.newsAdapterBinding.apply {
            root.setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(holder.newsAdapterBinding.ivArticleImage)
            holder.newsAdapterBinding.tvSource.text = article.source?.name
            holder.newsAdapterBinding.tvTitle.text = article.title
            holder.newsAdapterBinding.tvDescription.text = article.description
            holder.newsAdapterBinding.tvPublishedAt.text = article.publishedAt
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}