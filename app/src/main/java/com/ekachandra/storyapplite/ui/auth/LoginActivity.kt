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
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ekachandra.storyapplite.R
import com.ekachandra.storyapplite.data.remote.response.LoginResponse
import com.ekachandra.storyapplite.databinding.ActivityLoginBinding
import com.ekachandra.storyapplite.ui.ViewModelFactory
import com.ekachandra.storyapplite.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel by viewModels<AuthViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()
        loginUser()
        notHaveAccount()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                startActivity(Intent(this@LoginActivity, WelcomeActivity::class.java))
                finishAffinity()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
    }

    private fun loginUser() {
        binding.btnLogin.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                if (binding.edLoginEmail.error == null && binding.edLoginPassword.error == null) {
                    viewModel.apply {
                        login(email, password)

                        isError().observe(this@LoginActivity) { isError ->
                            if (isError) {
                                showToast(getString(R.string.error))
                            }
                        }

                        loginResponse().observe(this@LoginActivity) { loginResponse ->
                            Toast.makeText(
                                this@LoginActivity,
                                loginResponse.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

            } else {
                showToast(getString(R.string.fill_out_all_the_field))
            }
        }

        observeIndicator()
    }

    private fun savePreference(data: LoginResponse) {
        viewModel.saveUser(
            data.loginResult.token,
            isLogin = true
        )
    }

    private fun observeIndicator() {
        viewModel.apply {
            isLoading().observe(this@LoginActivity) { isLoading ->
                if (isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }

            isSuccessLogin().observe(this@LoginActivity) { isSuccess ->
                if (isSuccess) {
                    loginResponse().observe(this@LoginActivity) { loadingResponse ->
                        savePreference(loadingResponse)
                    }

                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finishAffinity()
                }
            }

        }
    }

    private fun notHaveAccount() {
        binding.tvLoginCrateAcc.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imgLoginPicture, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val tvLogin = ObjectAnimator.ofFloat(binding.tvLoginTitle, View.ALPHA, 1f).setDuration(500)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvLoginEmail, View.ALPHA, 1f).setDuration(500)
        val edEmail = ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(500)
        val tvPassword =
            ObjectAnimator.ofFloat(binding.tvLoginPassword, View.ALPHA, 1f).setDuration(500)
        val edPassword =
            ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1f).setDuration(500)
        val tvCreateAcc =
            ObjectAnimator.ofFloat(binding.tvLoginCrateAcc, View.ALPHA, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)

        val togetherEmail = AnimatorSet().apply {
            playTogether(tvEmail, edEmail)
        }

        val togetherPassword = AnimatorSet().apply {
            playTogether(tvPassword, edPassword)
        }

        AnimatorSet().apply {
            playSequentially(tvLogin, togetherEmail, togetherPassword, btnLogin, tvCreateAcc)
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