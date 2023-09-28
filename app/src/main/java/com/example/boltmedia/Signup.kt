package com.example.boltmedia

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.boltmedia.Login
import com.example.boltmedia.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp

class Signup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var editTextEmail: androidx.appcompat.widget.AppCompatEditText
    private lateinit var editTextPassword: androidx.appcompat.widget.AppCompatEditText
    //    private lateinit var editTextName: TextInputEditText
//    private lateinit var editTextUsername: TextInputEditText
    private lateinit var signupButton: Button
    private lateinit var loginRedirect:TextView
    private lateinit var intent:Intent
    private lateinit var context:Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        context=this
        FirebaseApp.initializeApp(context)
        auth = FirebaseAuth.getInstance()
        editTextEmail = findViewById(R.id.signup_email)
        editTextPassword = findViewById(R.id.signup_password)
//        editTextName = findViewById(R.id.signup_name)
//        editTextUsername = findViewById(R.id.signup_username)
        loginRedirect=findViewById(R.id.loginRedirectText)
        signupButton = findViewById(R.id.signup_button)
        signupButton.setOnClickListener {
            var email = editTextEmail.text
            var password = editTextPassword.text
//            var name = editTextName.text
//            var userName = editTextUsername.text
            if (email == null || email?.contains("@") != true) {
                Toast.makeText(this, "Enter Valid Mail", Toast.LENGTH_SHORT)
            }
            else if (password == null) {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT)
            }
//            if (name == null) {
//                Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT)
//            }
//            if (userName == null) {
//                Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT)
//            }
            else{
                auth.createUserWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            intent=Intent(this,Login::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }


        }
        loginRedirect.setOnClickListener {
            intent=Intent(this,Login::class.java)
            startActivity(intent)
        }
    }

//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if (currentUser != null) {
//            intent=Intent(this,Login::class.java)
//            startActivity(intent)
//        }
//    }
}