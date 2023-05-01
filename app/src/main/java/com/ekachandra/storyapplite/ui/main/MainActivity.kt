package com.ekachandra.storyapplite.ui.main

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekachandra.storyapplite.R
import com.ekachandra.storyapplite.databinding.ActivityMainBinding
import com.ekachandra.storyapplite.ui.ViewModelFactory
import com.ekachandra.storyapplite.ui.auth.WelcomeActivity
import com.ekachandra.storyapplite.ui.camera.CameraActivity
import com.ekachandra.storyapplite.ui.maps.MapsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: StoryAdapter

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showListStory()
        updateStory()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                viewModel.setLogout()
                startActivity(Intent(this@MainActivity, WelcomeActivity::class.java))
                finishAffinity()
                true
            }

            R.id.add_story -> {
                startActivity(Intent(this@MainActivity, CameraActivity::class.java))
                true
            }

            R.id.change_language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }

            R.id.open_map -> {
                startActivity(Intent(this@MainActivity, MapsActivity::class.java))
                true
            }

            else -> true
        }
    }

    private fun showListStory() {
        adapter = StoryAdapter(this@MainActivity)
        val layoutManager = LinearLayoutManager(this@MainActivity)

        binding.apply {
            rvStories.layoutManager = layoutManager
            rvStories.addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    layoutManager.orientation
                )
            )
            rvStories.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                }
            )
        }

        observeToken()
    }

    private fun updateStory() {
        viewModel.isUpdated().observe(this@MainActivity) { isUpdated ->
            if (isUpdated) {
                showListStory()
                adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        if (positionStart == 0) {
                            binding.rvStories.layoutManager?.scrollToPosition(0)
                        }
                    }
                })
            }
        }
    }

    private fun observeToken() {
        viewModel.getToken().observe(this@MainActivity) { token ->
            if (token != "") {
                observeStoryList(token)
                observeIndicator()
            }
        }
    }

    private fun observeStoryList(token: String) {
        viewModel.getAllStories(token).observe(this@MainActivity) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun observeIndicator() {
        viewModel.apply {
            isLoading().observe(this@MainActivity) { isLoading ->
                if (isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }

            isError().observe(this@MainActivity) { isError ->
                if (isError) {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}