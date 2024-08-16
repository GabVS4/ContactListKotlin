package com.example.contatos.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.contatos.R
import com.example.contatos.database.DBHelper
import com.example.contatos.databinding.ActivityContactDetailBinding
import com.example.contatos.model.ContactModel

class ContactDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactDetailBinding
    private lateinit var db: DBHelper
    private var contactModel = ContactModel()
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private var imageId: Int? = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityContactDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val i = intent
        val id = i.extras?.getInt("id")
        db = DBHelper(applicationContext)

        if(id != null) {
            contactModel = db.getContact(id)
            populate()
        } else {
            finish()
        }

        binding.buttonBack.setOnClickListener{
            setResult(0,i)
            finish()
        }

        binding.buttonEdit.setOnClickListener{
            binding.LayoutEditDelete.visibility = View.VISIBLE
            binding.LayoutEdit.visibility = View.GONE
            changeEditText(true)

        }

        binding.buttonCancel.setOnClickListener{
            binding.LayoutEditDelete.visibility = View.GONE
            binding.LayoutEdit.visibility = View.VISIBLE
            populate()
            changeEditText(false)
        }

        binding.buttonSave.setOnClickListener{
                val res = db.updateContact(
                id = contactModel.id,
                name = binding.editName.text.toString(),
                address = binding.editAddress.text.toString(),
                email = binding.editEmail.text.toString(),
                phone = binding.editPhone.text.toString().toInt(),
                imageId = contactModel.imageId
            )
            if(res > 0 ){
                Toast.makeText(applicationContext, "Update OK",Toast.LENGTH_SHORT).show()
                setResult(1,i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Update ERROR",Toast.LENGTH_SHORT).show()
                setResult(0,i)
                finish()
            }
        }

        binding.buttonDelete.setOnClickListener{
            val res = db.deleteContact(contactModel.id)
            if(res > 0 ){
                Toast.makeText(applicationContext, "Delete OK",Toast.LENGTH_SHORT).show()
                setResult(1,i)
                finish()
            } else {
                Toast.makeText(applicationContext, "Delete ERROR",Toast.LENGTH_SHORT).show()
                setResult(0,i)
                finish()
            }
        }

        binding.imageContact.setOnClickListener{
            launcher.launch(Intent(applicationContext, ContactImageSelectionActivity::class.java))
        }

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.data != null && it.resultCode == 1){
                imageId = it.data?.extras?.getInt("id")
                binding.imageContact.setImageDrawable(resources.getDrawable(imageId!!))
            } else {
                imageId = -1
                binding.imageContact.setImageResource(R.drawable.perfil)
            }
        }

    }

    private fun changeEditText(status: Boolean) {
        binding.editName.isEnabled = status
        binding.editAddress.isEnabled = status
        binding.editPhone.isEnabled = status
        binding.editEmail.isEnabled = status
    }

    private fun populate() {

        binding.editName.setText(contactModel.name)
        binding.editAddress.setText(contactModel.address)
        binding.editEmail.setText(contactModel.email)
        binding.editPhone.setText(contactModel.phone.toString())
        if(contactModel.imageId > 0){
            binding.imageContact.setImageDrawable(resources.getDrawable(contactModel.imageId))
        }
        binding.imageContact.setImageResource(R.drawable.perfil)

    }
}