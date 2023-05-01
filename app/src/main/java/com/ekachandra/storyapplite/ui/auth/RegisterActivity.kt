package com.ekachandra.storyapplite.ui.auth

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ekachandra.storyapplite.R
import com.ekachandra.storyapplite.databinding.ActivityRegisterBinding
import com.ekachandra.storyapplite.ui.ViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel by viewModels<AuthViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()
        registerUser()
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }

    private fun registerUser() {
        binding.btnRegister.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {

                if (binding.edRegisterEmail.error == null && binding.edRegisterPassword.error == null) {
                    viewModel.register(name, email, password)

                    observeIndicator()
                }

            } else {
                showToast(getString(R.string.fill_out_all_the_field))
            }
        }
    }

    private fun observeIndicator() {
        viewModel.apply {
            isLoading().observe(this@RegisterActivity) { isLoading ->
                if (isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }

            isSuccessRegister().observe(this@RegisterActivity) { isSuccess ->
                if (isSuccess) {
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finishAffinity()
                }
            }

            isError().observe(this@RegisterActivity) { isError ->
                if (isError) {
                    showToast(getString(R.string.error))
                }
            }

            message().observe(this@RegisterActivity) { message ->
                Toast.makeText(this@RegisterActivity, message.message, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imgRegisterPicture, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val tvRegister =
            ObjectAnimator.ofFloat(binding.tvRegisterTitle, View.ALPHA, 1f).setDuration(500)
        val tvName = ObjectAnimator.ofFloat(binding.tvRegisterName, View.ALPHA, 1f).setDuration(500)
        val edName = ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(500)
        val tvEmail =
            ObjectAnimator.ofFloat(binding.tvRegisterEmail, View.ALPHA, 1f).setDuration(500)
        val edEmail =
            ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(500)
        val tvPassword =
            ObjectAnimator.ofFloat(binding.tvRegisterPassword, View.ALPHA, 1f).setDuration(500)
        val edPassword =
            ObjectAnimator.ofFloat(binding.edRegisterPassword, View.ALPHA, 1f).setDuration(500)
        val btnRegister =
            ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(500)

        val togetherName = AnimatorSet().apply {
            playTogether(tvName, edName)
        }

        val togetherEmail = AnimatorSet().apply {
            playTogether(tvEmail, edEmail)
        }

        val togetherPassword = AnimatorSet().apply {
            playTogether(tvPassword, edPassword)
        }

        AnimatorSet().apply {
            playSequentially(tvRegister, togetherName, togetherEmail, togetherPassword, btnRegister)
        }.start()
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}
