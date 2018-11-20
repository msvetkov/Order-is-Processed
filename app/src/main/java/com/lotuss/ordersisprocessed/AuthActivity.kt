package com.lotuss.ordersisprocessed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.lotuss.ordersisprocessed.data.auth.AuthWithFirebase
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button
    private lateinit var emailTextField: EditText
    private lateinit var passwordTextField: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        supportActionBar!!.hide()

        emailTextField = this.email_text_field
        passwordTextField = this.password_text_field

        signInButton = this.sign_in
        signInButton.setOnClickListener{
            val email: String = emailTextField.text.toString()
            val password: String = passwordTextField.text.toString()
            AuthWithFirebase.signIn(this, email, password)
        }

        signUpButton = this.sign_up
        signUpButton.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }




}
