package com.example.finalproject.Activity

import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.example.finalproject.Activity.Firebase.FirestoreClass
import com.example.finalproject.Activity.models.User
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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

    fun userRegisteredSuccess(){
        Toast.makeText(this,"you have" +
                "successfully registered", Toast.LENGTH_LONG
        ).show()
        hideProgressDialog()

        FirebaseAuth.getInstance().signOut()
        finish()
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
        val email: String = et_email_sign_in.text.toString().trim{ it <= ' '}
        val password: String = et_password.text.toString().trim { it <= ' ' }

        if (validateForm(name, email, password)){
            showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                   // hideProgressDialog()
                    if (task.isSuccessful) {
                        val firebaseUser: FirebaseUser = task.result!!.user!!
                        val registeredEmail = firebaseUser.email!!
                        val user = User(firebaseUser.uid, name, registeredEmail)
                        FirestoreClass().registerUser(this, user)

                       /* Toast.makeText(
                            this,
                            "$name you have" +
                                    " successfully registered the email" +
                                    " address $registeredEmail",
                            Toast.LENGTH_SHORT
                        ).show()
                        FirebaseAuth.getInstance().signOut()
                        finish()*/
                    } else {
                        Toast.makeText(
                            this,
                            "Registration failed", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
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