package com.example.finalproject.Activity.Firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.finalproject.Activity.MainActivity
import com.example.finalproject.Activity.MyProfileActivity
import com.example.finalproject.Activity.SignUpActivity
import com.example.finalproject.Activity.SigninActivity
import com.example.finalproject.Activity.Utils.Constants
import com.example.finalproject.Activity.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.Objects


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

    fun updateUserProfileData(activity: MyProfileActivity, userHashMap: HashMap<String, Any>){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .update(userHashMap)
            .addOnSuccessListener {
                Log.i(activity.javaClass.simpleName,"Profile Data updated successfully")
                Toast.makeText(activity, "Profile updated successfully!",Toast.LENGTH_SHORT).show()
                activity.profileUpdateSuccess()
            }.addOnFailureListener {
                e->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName,
                "Error while creating a board.",
                e)
                Toast.makeText(activity,"Error when updating the profile!",Toast.LENGTH_SHORT).show()
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
                    is MyProfileActivity ->{
                        if (loggedInUser != null) {
                            activity.setUserDataInUI(loggedInUser)
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