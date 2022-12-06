package com.example.quick_eats_team_project

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginAcitivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_acitivity)
        var userEmailaddress = findViewById<EditText>(R.id.etEmailaddress)
        var userPassword = findViewById<EditText>(R.id.etpassword)
        var userLoginbutton = findViewById<Button>(R.id.Loginbutton)
        var createAccounttext = findViewById<TextView>(R.id.tvcreateAccount)
        createAccounttext.setOnClickListener{
            var intent = Intent(this,RegistrationActivity::class.java)
            startActivity(intent)
        }
        userLoginbutton.setOnClickListener {


            if (userEmailaddress.text.toString().isEmpty().not() && userPassword.text.toString()
                    .isEmpty().not()
            ) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(userEmailaddress.text.toString())
                        .matches()
                ) {

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(userEmailaddress.text.toString(), userPassword.text.toString()).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "You have successfully logged in!!", Toast.LENGTH_SHORT)
                                .show()
                            val sharedPreference =  getSharedPreferences("PREFERENCE_USER", Context.MODE_PRIVATE)
                            val editor = sharedPreference.edit()
                            editor.putString("userId",task.result.user?.uid)
                            editor.putString("useremail",userEmailaddress.text.toString())
                            editor.putString("password",userPassword.text.toString())
                            editor.commit()
                            startActivity(Intent(this,FoodSearchActivity::class.java))
                            finish()
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(this, "Error ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }


                } else {
                    Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()

            }



        }
    }
}



