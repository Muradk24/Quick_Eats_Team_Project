package com.example.quick_eats_team_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast

class FoodSearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_search)

        val imageButton=findViewById<ImageButton>(R.id.imageButton)
        imageButton.setOnClickListener {
            Toast.makeText(this,"Apply for all",Toast.LENGTH_SHORT).show()
        }
    }
}