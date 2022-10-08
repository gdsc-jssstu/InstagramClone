package com.example.instagramclone

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.instagramclone.data.Event
import com.example.instagramclone.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

const val USERS = "users"

@HiltViewModel
class IgViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage
) : ViewModel() {

    val signedIn = mutableStateOf(false)
    val inProgress = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    val popupNotification = mutableStateOf<Event<String>?>(null)

    fun onSignup(username: String, email: String, pass: String) {
        inProgress.value = true

        //In firestore we'll be checking whether a user already exists
        db.collection(USERS).whereEqualTo("username", username).get()
            .addOnSuccessListener {  documents ->
                if(documents.size() > 0){
                    handleException(customMessage = "Username already exists")
                    inProgress.value = false
                }
                else{
                    //create a new user
                    auth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener{ task ->
                            if(task.isSuccessful){
                                signedIn.value = true
                                //create profile
                            }
                            else{
                                handleException(exception = task.exception, customMessage = "Signup failed")
                            }
                            inProgress.value = false
                        }
                }
            }
            .addOnFailureListener {  }
    }

    fun handleException(exception: Exception? = null, customMessage: String = ""){
        exception?.printStackTrace()
        val errorMessage = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMessage else "$customMessage : $errorMessage"
        popupNotification.value = Event(message)
    }
}