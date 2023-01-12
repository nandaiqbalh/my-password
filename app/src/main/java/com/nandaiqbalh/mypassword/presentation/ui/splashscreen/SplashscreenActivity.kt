package com.nandaiqbalh.mypassword.presentation.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.nandaiqbalh.mypassword.MainActivity
import com.nandaiqbalh.mypassword.R

class SplashscreenActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splashscreen)

		Handler().postDelayed({
			val i = Intent(this@SplashscreenActivity, MainActivity::class.java)
			startActivity(i)
			finish()
		}, 3000)
	}
}