package com.spbarber.sct_project.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.spbarber.sct_project.App
import com.spbarber.sct_project.App.Companion.getAuth
import com.spbarber.sct_project.App.Companion.getFirestore
import com.spbarber.sct_project.R
import com.spbarber.sct_project.entities.User
import com.spbarber.sct_project.ui.fragments.SigninFragmentDirections
import com.spbarber.sct_project.utils.Constants
import java.lang.Exception

class UsuarioViewModel : ViewModel() {

    fun login(username: String, pass: String): LiveData<Task<AuthResult>> {
        val data = MutableLiveData<Task<AuthResult>>()
        getAuth().signInWithEmailAndPassword(username, pass)
            .addOnCompleteListener { task ->
                data.value = task
            }
        return data
    }

    fun registro(user:User): MutableLiveData<Exception?> {
        val data = MutableLiveData<Exception?>()
        getAuth().createUserWithEmailAndPassword(user.userName, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    data.value = null
                    getFirestore()
                        .collection(Constants.USERS)
                        .document(getAuth().currentUser!!.uid)
                        .set(
                            user
                        )
                        .addOnCompleteListener { taskNewUser ->
                            if (taskNewUser.isSuccessful) {
                                data.value = null
                            } else {
                                data.value = taskNewUser.exception
                            }
                    }
                } else {
                    data.value = task.exception
                }
            }
        return data
    }


}