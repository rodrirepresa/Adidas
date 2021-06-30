package com.represa.adidas

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.represa.adidas.ui.MyTheme
import com.represa.adidas.ui.compose.SplashScreen
import android.content.Intent
import com.represa.adidas.ui.viewmodels.SplashViewModel


class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashViewModel.populateDatabase()
        val i = Intent(this, MainActivity::class.java)
        setContent {
            MyTheme() {
                SplashScreen {
                    startActivity(i)
                }
            }
        }
    }
}