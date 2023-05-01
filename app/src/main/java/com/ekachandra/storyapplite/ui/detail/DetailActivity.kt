package com.ekachandra.storyapplite.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ekachandra.storyapplite.R
import com.ekachandra.storyapplite.databinding.ActivityDetailBinding
import com.ekachandra.storyapplite.ui.ViewModelFactory
import com.ekachandra.storyapplite.ui.utils.withDateFormat

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeToken()
        observeIndicator()
    }

    private fun observeToken() {
        viewModel.getToken().observe(this@DetailActivity) { token ->
            val idUser = intent.getStringExtra(STORY_ID)
            if (idUser != null) {
                observeStoryDetail(token, idUser)
            }
        }
    }

    private fun observeStoryDetail(token: String, id: String) {
        viewModel.getDetailStory(token, id).observe(this@DetailActivity) { detailStory ->
            Glide.with(this@DetailActivity)
                .load(detailStory.photoUrl)
                .into(binding.imgDetailPhoto)

            binding.apply {
                tvDetailName.text = detailStory.name
                tvDetailTime.text =
                    resources.getString(R.string.dateFormat, detailStory.createdAt.withDateFormat())
                tvDetailDescription.text =
                    resources.getString(R.string.description_with, detailStory.description)
            }
        }
    }

    private fun observeIndicator() {
        viewModel.apply {
            isLoading().observe(this@DetailActivity) { isLoading ->
                if (isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }

            isError().observe(this@DetailActivity) { isError ->
                if (isError) {
                    Toast.makeText(
                        this@DetailActivity,
                        getString(R.string.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    companion object {
        const val STORY_ID = "id"
    }
}