package com.example.quick_eats_team_project

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.FirebaseApp

class QuickEatsSplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_eats_splash_screen)
        FirebaseApp.initializeApp(applicationContext)
        Handler(Looper.getMainLooper()).postDelayed({//we are giving a delay of three seconds
            val sharedPreference =  getSharedPreferences("PREFERENCE_USER", Context.MODE_PRIVATE)
            if (sharedPreference.getString("useremail","").isNullOrEmpty()){
            val intent= Intent(this,LoginAcitivity::class.java)//we are creating an intent
            startActivity(intent)
            finish()
            }else{
                startActivity(Intent(this,FoodSearchActivity::class.java))
                finish()
            }

        },3000)


    }



}


