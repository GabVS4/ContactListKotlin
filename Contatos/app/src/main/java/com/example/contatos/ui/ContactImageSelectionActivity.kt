package com.example.contatos.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contatos.R
import com.example.contatos.databinding.ActivityContactDetailBinding
import com.example.contatos.databinding.ActivityContactImageSelectionBinding

class ContactImageSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactImageSelectionBinding
    private lateinit var i: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact_image_selection)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityContactImageSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        i = intent

        binding.imageProfile1.setOnClickListener{sendID(R.drawable.profile1)}
        binding.imageProfile2.setOnClickListener{sendID(R.drawable.profile2)}
        binding.imageProfile3.setOnClickListener{sendID(R.drawable.profile3)}
        binding.imageProfile4.setOnClickListener{sendID(R.drawable.profile4)}
        binding.imageProfile5.setOnClickListener{sendID(R.drawable.man)}
        binding.imageProfile6.setOnClickListener{sendID(R.drawable.woman)}
        binding.buttonRemoveImage.setOnClickListener{sendID(R.drawable.perfil)}
    }

    private fun sendID(id: Int) {
        i.putExtra("id", id)
        setResult(1, i)
        finish()
    }
}