package com.lotuss.ordersisprocessed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.lotuss.ordersisprocessed.data.auth.AuthWithFirebase

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent: Intent
        if (FirebaseAuth.getInstance().currentUser != null)
            AuthWithFirebase.getUserFromDatabase(this)
        else{
            intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
