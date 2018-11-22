package com.lotuss.ordersisprocessed.data.auth

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.lotuss.ordersisprocessed.ProgressDialogCaller
import com.lotuss.ordersisprocessed.R

class SignUpWithFirebase{
    companion object {
        private val auth: FirebaseAuth = FirebaseAuth.getInstance()

        private fun validateForm(context: Context, firstName: String, lastName: String,
                                 email: String, password: String, repeatPassword: String): Boolean{
            var valid = true
            if (TextUtils.isEmpty(firstName)) {
                Toast.makeText(context, R.string.first_name_is_empty, Toast.LENGTH_LONG).show()
                valid = false
            }else if (TextUtils.isEmpty(lastName)){
                Toast.makeText(context, R.string.last_name_is_empty, Toast.LENGTH_LONG).show()
                valid = false
            }else if (TextUtils.isEmpty(email)) {
                Toast.makeText(context, R.string.email_is_empty, Toast.LENGTH_LONG).show()
                valid = false
            }else if (TextUtils.isEmpty(password)) {
                Toast.makeText(context, R.string.password_is_empty, Toast.LENGTH_LONG).show()
                valid = false
            }else if (TextUtils.isEmpty(repeatPassword)) {
                Toast.makeText(context, R.string.repeat_password, Toast.LENGTH_LONG).show()
                valid = false
            }else if (!password.equals(repeatPassword)) {
                Toast.makeText(context, R.string.passwords_dont_match, Toast.LENGTH_LONG).show()
                valid = false
            }else if (password.length < 6) {
                Toast.makeText(context, R.string.password_length_error, Toast.LENGTH_LONG).show()
                valid = false
            }

            return valid
        }

        fun signUp(context: Context, firstName: String, lastName: String,
                   email: String, password: String, repeatPassword: String, position: Int){
            if (!validateForm(context, firstName, lastName, email, password, repeatPassword)) {
                return
            }
            ProgressDialogCaller.showProgressDialog(context)
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener{task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            UserManager.user = User(auth.uid!!, firstName, lastName, position)
                            setUserToDatabase()
                            AuthWithFirebase.authIsSuccess(context, UserManager.user.position)
                        } else {
                            ProgressDialogCaller.hideProgressDialog()
                            Toast.makeText(context, R.string.error, Toast.LENGTH_LONG).show()
                        }}


        }

        private fun setUserToDatabase(){
            val database = FirebaseDatabase.getInstance()
            val myRef = database.reference
            myRef.child("users").child(auth.uid!!).setValue(UserManager.user)
        }
    }
}