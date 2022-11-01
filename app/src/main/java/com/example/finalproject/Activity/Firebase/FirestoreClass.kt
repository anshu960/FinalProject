package com.example.finalproject.Activity.Firebase

import android.app.Activity
import android.util.Log
import com.example.finalproject.Activity.MainActivity
import com.example.finalproject.Activity.SignUpActivity
import com.example.finalproject.Activity.SigninActivity
import com.example.finalproject.Activity.Utils.Constants
import com.example.finalproject.Activity.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, userInfo : User){

        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
                activity.finish()
            }.addOnFailureListener {
                e->
                Log.e(activity.javaClass.simpleName, "Error writing document", e)
            }
    }
    fun signInUser(activity: Activity){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val loggedInUser = document.toObject(User::class.java)
                when(activity){
                    is SigninActivity ->{
                        if (loggedInUser != null) {
                            activity.signInSuccess(loggedInUser)
                        }
                    }
                    is MainActivity ->{
                        if (loggedInUser != null) {
                            activity.updateNavigationUserDetails(loggedInUser)
                        }
                    }
                }
               // activity.signInSuccess(loggedInUser)
            }.addOnFailureListener {
                e->
                when(activity){
                    is SigninActivity ->{
                        activity.hideProgressDialog()
                    }
                    is MainActivity ->{
                        activity.hideProgressDialog()
                    }
                }

                Log.e("SignInUser","Error writing document", e)
            }
    }

     fun getCurrentUserId(): String{
         var currentUser = FirebaseAuth.getInstance().currentUser
         var currentUserID = ""
       // return FirebaseAuth.getInstance().currentUser!!.uid
         if (currentUser != null){
             currentUserID = currentUser.uid
         }
         return currentUserID
    }
}