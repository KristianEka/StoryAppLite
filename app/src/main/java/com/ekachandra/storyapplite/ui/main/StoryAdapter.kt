package com.ekachandra.storyapplite.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ekachandra.storyapplite.R
import com.ekachandra.storyapplite.data.remote.response.ListStoryItem
import com.ekachandra.storyapplite.databinding.ItemStoryBinding
import com.ekachandra.storyapplite.ui.detail.DetailActivity
import com.ekachandra.storyapplite.ui.utils.withDateFormat

class StoryAdapter(private val context: Context) :
    PagingDataAdapter<ListStoryItem, StoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: ListStoryItem) {
            binding.apply {
                Glide.with(root.context)
                    .load(story.photoUrl)
                    .into(imgItemStory)

                tvItemName.text = story.name
                tvItemDescription.text = story.description
                tvItemTime.text =
                    context.getString(R.string.dateFormat, story.createdAt.withDateFormat())
                root.setOnClickListener {
                    val optionCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            context as Activity,
                            Pair(imgItemStory, context.getString(R.string.story_picture)),
                            Pair(tvItemName, context.getString(R.string.name)),
                            Pair(tvItemTime, context.getString(R.string.time)),
                            Pair(tvItemDescription, context.getString(R.string.description))
                        )

                    val intent = Intent(root.context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.STORY_ID, story.id)
                    }
                    root.context.startActivity(intent, optionCompat.toBundle())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem,
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}