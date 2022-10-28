package com.example.finalproject.Activity.Firebase

import android.util.Log
import com.example.finalproject.Activity.SignUpActivity
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
                Log.e(activity.javaClass.simpleName, "Error")
            }
    }

     fun getCurrentUserId(): String{
        return FirebaseAuth.getInstance().currentUser!!.uid
    }
}