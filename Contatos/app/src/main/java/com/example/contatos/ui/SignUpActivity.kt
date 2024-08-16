package com.example.contatos.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contatos.R
import com.example.contatos.database.DBHelper
import com.example.contatos.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(this)

        binding.buttonSignup.setOnClickListener{
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()
            val passwordC = binding.editConfirmPassword.text.toString()

            if(username.isNotEmpty() && password.isNotEmpty() && passwordC.isNotEmpty()) {

                if(password == passwordC) {
                    val res = db.insertUser(username, password)
                    if (res > 0) {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.signup_ok),
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.signup_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }else {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.passwords_dont_match),
                            Toast.LENGTH_SHORT
                        ).show()
                    binding.editUsername.setText("")
                    binding.editPassword.setText("")
                    binding.editConfirmPassword.setText("")
                }
            } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.please_insert_all_required_fields),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }
}