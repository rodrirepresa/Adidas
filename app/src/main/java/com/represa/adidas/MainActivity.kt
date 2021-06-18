package com.represa.adidas

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.represa.adidas.databinding.ActivityMainBinding
import com.represa.adidas.ui.viewmodels.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val productViewModel: ProductViewModel by viewModel()

    private fun startObsrrver(){
        productViewModel.internetConection.observe(this, Observer {
            when(it){
                true -> Toast.makeText(applicationContext, "HOLA", Toast.LENGTH_SHORT).show()
                false -> Toast.makeText(applicationContext, "ADIOS", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        productViewModel.populateDatabase()
        startObsrrver()
    }
}