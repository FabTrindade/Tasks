package com.devmasterteam.tasks.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.devmasterteam.tasks.R
import com.devmasterteam.tasks.databinding.ActivityLoginBinding
import com.devmasterteam.tasks.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Variáveis da classe
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        // Layout
        setContentView(binding.root)

        // Eventos
        binding.buttonLogin.setOnClickListener(this)
        binding.textRegister.setOnClickListener(this)

        viewModel.verifyAuthentication()

        // Observadores
        observe()
    }

    override fun onClick(v: View) {
        when (v.id) {
            binding.buttonLogin.id -> handleLogin()

            binding.textRegister.id -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }


    }

    private fun observe() {
        viewModel.onLogin.observe(this) {
            if (it.getStatus()) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(applicationContext, it.getMessage(), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loggedUser.observe(this) {
            if (it) {
                biometricAuthentication()
            }
        }
    }

    private fun biometricAuthentication() {
        //Thread that will execute the operation
        val executor = ContextCompat.getMainExecutor(this)

        val bio = BiometricPrompt(this, executor, object: BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        })

        val info = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Fingerprint Identification")
            .setDescription("For your securitty.")
            .setSubtitle("Please identify yourself ")
            .setNegativeButtonText("Cancel")
            .build()

        bio.authenticate(info)
    }


    private fun handleLogin() {
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()

        viewModel.doLogin(email, password)
    }

}