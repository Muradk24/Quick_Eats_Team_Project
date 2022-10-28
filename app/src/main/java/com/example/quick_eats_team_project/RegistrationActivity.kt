package com.example.quick_eats_team_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegistrationActivity : AppCompatActivity() {
    lateinit var personName: EditText
    lateinit var personPassword: EditText
    lateinit var personEmailaddress: EditText
    lateinit var personDateofbirth: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        personName = findViewById<EditText>(R.id.etPersonName) //Get the persons name in variable
        personPassword = findViewById<EditText>(R.id.etPassword)
        personEmailaddress = findViewById<EditText>(R.id.etEmailAddress)
        personDateofbirth = findViewById<EditText>(R.id.etDate)
        var registerButton = findViewById<Button>(R.id.registrationButton)
        registerButton.setOnClickListener {
            if (checkValidation()) {
                finish()
            }
//to add a action to a button/ click
        }
    }


    fun checkValidation(): Boolean {
        if (personName.text.toString().isEmpty().not() && personPassword.text.toString().isEmpty()
                .not() && personEmailaddress.text.toString().isEmpty()
                .not() && personDateofbirth.text.toString().isEmpty().not()
        )//To make sure no feilds are empty
        {
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(personEmailaddress.text.toString())
                    .matches() && personName.text.toString().length > 3
            )//check the pattern of email address if it true or not
            {
                Toast.makeText(this, "you have registered successfully", Toast.LENGTH_SHORT).show()
                return true
                //finish()
            } else {
                Toast.makeText(this, "please enter a valid text", Toast.LENGTH_SHORT).show()
                return false
            }
        } else {
            Toast.makeText(this, "please fill all the fields", Toast.LENGTH_SHORT).show()
            return false
        }//If any fields are empty this text will appear and this will show an error message to a user

    }
}




