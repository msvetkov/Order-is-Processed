package com.lotuss.ordersisprocessed.data.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.lotuss.ordersisprocessed.R
import com.lotuss.ordersisprocessed.cook.CookActivity
import com.lotuss.ordersisprocessed.ProgressDialogCaller
import com.lotuss.ordersisprocessed.data.food.MenuGetter
import com.lotuss.ordersisprocessed.waiter.WaiterActivity

class AuthWithFirebase {
    companion object {
        private val auth: FirebaseAuth = FirebaseAuth.getInstance()

        private fun validateForm( context: Context, email: String, password: String): Boolean {
            var valid = true

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(context, R.string.email_is_empty, Toast.LENGTH_LONG).show()
                valid = false
            }else if (TextUtils.isEmpty(password)) {
                Toast.makeText(context, R.string.password_is_empty, Toast.LENGTH_LONG).show()
                valid = false
            }

            return valid
        }

        fun signIn(context: Context, email: String, password: String){

            if (!validateForm(context, email, password)) {
                return
            }
            ProgressDialogCaller.showProgressDialog(context)
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener{ task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            getUserFromDatabase(context)
                        } else {
                            ProgressDialogCaller.hideProgressDialog()
                            Toast.makeText(context, R.string.no_acc, Toast.LENGTH_LONG).show()
                        }
                    }
        }

        fun getUserFromDatabase(context: Context){
            val database= FirebaseDatabase.getInstance()
            val myRef = database.reference
            myRef.child("users").child(auth.currentUser!!.uid).addValueEventListener(object: ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    UserManager.user = dataSnapshot.getValue(User::class.java)!!
                    authIsSuccess(context, UserManager.user.position)
                }

                override fun onCancelled(dataSnapshot: DatabaseError) {
                    Toast.makeText(context, R.string.error, Toast.LENGTH_LONG).show()
                }
            })
        }

        fun startWaiterActivity(context: Context){
            val intent = Intent(context, WaiterActivity::class.java)
            ProgressDialogCaller.hideProgressDialog()
            context.startActivity(intent)
            val activity = context as Activity
            activity.finish()

        }

        fun authIsSuccess(context: Context, position: Int){
            lateinit var intent: Intent
            when(position){
                0 -> MenuGetter.getMenuFromDatabase(context)
                1 -> {
                    intent = Intent(context, CookActivity::class.java)
                    ProgressDialogCaller.hideProgressDialog()
                    context.startActivity(intent)
                    val activity = context as Activity
                    activity.finish()
                }
            }
        }
    }
}