package com.represa.adidas

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.represa.adidas.databinding.ActivityMainBinding
import com.represa.adidas.ui.fragments.InternetConectionDialogFragment
import com.represa.adidas.ui.viewmodels.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.fragment.app.Fragment
import com.represa.adidas.data.exception.ProductDetailException
import com.represa.adidas.data.exception.ProductsException
import com.represa.adidas.ui.fragments.ProductsFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val productViewModel: ProductViewModel by viewModel()
    private var internetDialog: InternetConectionDialogFragment? = null
    private var errorDialog: Dialog? = null

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

        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        productViewModel.checkFirstConnection {
            internetDialog = InternetConectionDialogFragment()
            internetDialog!!.show(supportFragmentManager, "MyCustomInternetFragment")
        }

        productViewModel.errorLiveData.observe(this, {
            if (errorDialog == null || !errorDialog!!.isShowing) {
                when (it) {
                    is ProductDetailException -> {
                        it?.let {
                            if (internetDialog == null || !internetDialog!!.isVisible) {
                                errorDialog = onCreateDialog(it.message) {
                                    super.onBackPressed()
                                }
                                errorDialog!!.show()
                            }
                        }
                    }
                    is ProductsException -> {}
                    else -> {
                        it?.let {
                            if (internetDialog == null || !internetDialog!!.isVisible) {
                                errorDialog = onCreateDialog(it.message) {}
                                errorDialog!!.show()
                            }
                        }
                    }
                }
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

    fun onCreateDialog(error: String?, onClose: () -> Unit): Dialog {
        var message = error ?: "Something went wrong"
        return this?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(message)
                .setPositiveButton(
                    "Close"
                ) { dialog, id ->
                    dialog.dismiss()
                    onClose.invoke()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onBackPressed() {
        getForegroundFragment()?.let {
            when (it) {
                is ProductsFragment -> {
                }
                else -> super.onBackPressed()
            }
        }

    }

    private fun getForegroundFragment(): Fragment? {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        return navHostFragment?.childFragmentManager?.fragments?.get(0)
    }
}