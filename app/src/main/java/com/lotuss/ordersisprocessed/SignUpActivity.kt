package com.lotuss.ordersisprocessed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import com.lotuss.ordersisprocessed.data.auth.SignUpWithFirebase
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpButton: Button
    private lateinit var firstNameTextField: EditText
    private lateinit var lastNameTextField: EditText
    private lateinit var emailTextField: EditText
    private lateinit var passwordTextField: EditText
    private lateinit var repeatPasswordTextField: EditText
    private lateinit var radioGroup: RadioGroup
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar!!.hide()

        firstNameTextField = this.first_name_text_field
        lastNameTextField = this.last_name_text_field
        emailTextField = this.email_text_field
        passwordTextField = this.password_text_field
        repeatPasswordTextField = this.repeat_password_text_field
        radioGroup = this.radio_group
        rb_waiter.isChecked = true

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_waiter -> position = 0
                R.id.rb_cook -> position = 1

            }
        }

        signUpButton = this.sign_up
        signUpButton.setOnClickListener{
            val firstName: String = firstNameTextField.text.toString()
            val lastName: String = lastNameTextField.text.toString()
            val email: String = emailTextField.text.toString()
            val password: String = passwordTextField.text.toString()
            val repeatPassword: String = repeatPasswordTextField.text.toString()

            SignUpWithFirebase.signUp(this, firstName, lastName, email, password, repeatPassword, position)
        }
    }


}
