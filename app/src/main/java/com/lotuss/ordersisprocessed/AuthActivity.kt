package com.lotuss.ordersisprocessed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.lotuss.ordersisprocessed.waiter.WaiterActivity
import kotlinx.android.synthetic.main.activity_main.*

class AuthActivity : AppCompatActivity() {

    private lateinit var signInButton: Button
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        signInButton = this.sign_in
        signInButton.setOnClickListener{
            val intent = Intent(this, WaiterActivity::class.java)
            startActivity(intent)
        }

        signUpButton = this.sign_up
        signUpButton.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
