package com.example.finalproject.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.example.finalproject.R
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setupActionBar()
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_sign_up_activity)

        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)

        }
        toolbar_sign_up_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        btn_sign_up.setOnClickListener {
            registerUser()
        }
    }
    private fun registerUser(){
        val name: String = et_name.text.toString().trim{ it <= ' '}
        val email: String = et_email.text.toString().trim{ it <= ' '}
        val password: String = et_password.text.toString().trim { it <= ' ' }

        if (validateForm(name, email, password)){
            Toast.makeText(
                this@SignUpActivity,
                "Now we can register a new user.",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
    private fun validateForm(name: String, email: String, password: String): Boolean{
        return when{
            TextUtils.isEmpty(name)->{
                showErrorSnackBar("Please enter a name")
                false
            }
            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Please enter email")
                false
            }
            TextUtils.isEmpty(password)->{
                showErrorSnackBar("Please enter your password")
                false
            }else ->{
                true
            }

        }
    }
}