package com.example.boltmedia

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.boltmedia.Signup
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.oAuthCredential

class Login : AppCompatActivity() {
    private lateinit var context:Context
    private lateinit var signupRedirect: TextView
    private lateinit var emailEditText: androidx.appcompat.widget.AppCompatEditText
    private lateinit var passwordEditText: androidx.appcompat.widget.AppCompatEditText
    private lateinit var loginButton: Button
    private lateinit var intent: Intent
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        context=this
        FirebaseApp.initializeApp(context)
        auth= FirebaseAuth.getInstance()
        signupRedirect=findViewById(R.id.signupRedirectText)
        emailEditText=findViewById(R.id.login_email)
        passwordEditText=findViewById(R.id.login_password)
        loginButton=findViewById(R.id.login_button)
        signupRedirect.setOnClickListener {
            intent=Intent(this,Signup::class.java)
            startActivity(intent)
        }
        loginButton.setOnClickListener {
            var email=emailEditText.text
            var password=passwordEditText.text
            if(email==null || password==null){
                Toast.makeText(this,"Enter email and Password",Toast.LENGTH_SHORT)
            }
            else{
                auth.signInWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(ContentValues.TAG, "Login:success")
                            intent=Intent(this,MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(ContentValues.TAG, "Login:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }
        }
    }
}