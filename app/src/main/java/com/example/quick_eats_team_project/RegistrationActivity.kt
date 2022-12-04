package com.example.quick_eats_team_project

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(personEmailaddress.text.toString(), personPassword.text.toString())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                lifecycleScope.launch {
                                    val profileUpdates = userProfileChangeRequest {
                                        displayName = personName.text.toString()
                                        photoUri = Uri.parse("https://example.com/jane-q-user/profile.jpg")
                                    }
                                    task.result.user?.updateProfile(profileUpdates)?.await()
                                    storeInDb(task)

                                }
                            }
                        }.addOnFailureListener { exception ->
                        Toast.makeText(this,  exception.localizedMessage!!.toString(), Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this,  "Invalid", Toast.LENGTH_SHORT).show()
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

private fun storeInDb(task: Task<AuthResult>) {
    val db = Firebase.firestore(Firebase.app)

    // Create a new user with a first and last name

    val user = hashMapOf(
        "user_id" to task.result.user?.uid,
        "dob" to personDateofbirth.text.toString()
    )

    // Add a new document with a generated ID
    db.collection("Users").document(task.result.user?.uid!!).set(user)
        .addOnSuccessListener { documentReference ->

            Toast.makeText(this, "You have registered successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
        .addOnFailureListener { e ->
            Log.w(RegistrationActivity::class.simpleName, "Error adding document  ${e.message}")


        }
}

}




