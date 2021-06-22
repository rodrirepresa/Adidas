package com.represa.adidas

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.represa.adidas.databinding.ActivityMainBinding
import com.represa.adidas.ui.fragments.InternetConectionDialogFragment
import com.represa.adidas.ui.fragments.ReviewDialogFragment
import com.represa.adidas.ui.viewmodels.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val productViewModel: ProductViewModel by viewModel()
    private var internetDialog: InternetConectionDialogFragment? = null

    private fun startObserver() {
        productViewModel.internetConection.observe(this, {
            when (it) {
                true -> {
                    if (internetDialog != null && internetDialog!!.isVisible) {
                        internetDialog!!.dismiss()
                    }
                }
                false -> {
                    if (internetDialog == null) {
                        internetDialog = InternetConectionDialogFragment()
                        internetDialog!!.show(supportFragmentManager, "MyCustomInternetFragment")
                    } else {
                        internetDialog!!.show(supportFragmentManager, "MyCustomInternetFragment")
                    }
                }
            }
        })

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        productViewModel.errorLiveData.observe(this, {
            it?.let {
                onCreateDialog(it.message).show()
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
        startObserver()
    }

    fun onCreateDialog(error: String?): Dialog {
        var message = error ?: "Something went wrong"
        return this?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(message)
                .setPositiveButton(
                    "Close"
                ) { dialog, id ->
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}