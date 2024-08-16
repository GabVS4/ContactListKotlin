package com.example.contatos.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contatos.R
import com.example.contatos.database.DBHelper
import com.example.contatos.databinding.ActivityMainBinding
import com.example.contatos.databinding.ActivityNewContactBinding

class NewContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewContactBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private var id: Int? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_contact)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityNewContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = DBHelper(applicationContext)
        val i = intent

        binding.buttonSave.setOnClickListener{
            val name = binding.editName.text.toString()
            val address = binding.editAddress.text.toString()
            val email = binding.editEmail.text.toString()
            val phone = binding.editPhone.text.toString().toInt()
            var imageId = -1
            if(id != null){
                imageId = id as Int
            }

            if(name.isNotEmpty() && address.isNotEmpty() && email.isNotEmpty()){
                val res = db.insertContact(name, address, email, phone, imageId)
                if(res > 0){
                    Toast.makeText(applicationContext, "Insert OK",Toast.LENGTH_SHORT).show()
                    setResult(1, i)
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Insert ERROR",Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.buttonCancel.setOnClickListener{
            setResult(0, i)
            finish()
        }

        binding.imageContact.setOnClickListener{
            launcher.launch(Intent(applicationContext, ContactImageSelectionActivity::class.java))
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.data != null && it.resultCode == 1){
                id = it.data?.extras?.getInt("id")
                binding.imageContact.setImageDrawable(resources.getDrawable(id!!))
            } else {
                id = -1
                binding.imageContact.setImageResource(R.drawable.perfil)
            }
        }
    }
}