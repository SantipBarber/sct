package com.spbarber.sct_project.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.UserProfileChangeRequest
import com.spbarber.sct_project.App.Companion.getAuth
import com.spbarber.sct_project.App.Companion.getFirestore
import com.spbarber.sct_project.entities.User
import com.spbarber.sct_project.utils.Constants

class UsuarioViewModel : ViewModel() {
    private val T = "TAG"

    fun login(username: String, pass: String): LiveData<Task<AuthResult>> {
        val data = MutableLiveData<Task<AuthResult>>()
        getAuth().signInWithEmailAndPassword(username, pass)
            .addOnCompleteListener { task ->
                data.value = task
            }
        return data
    }

    fun registro(user: User): MutableLiveData<Exception?> {
        Log.i(T, "Entrando en la función registro")
        Log.i(T, "${user.firstName}, ${user.lastName}, ${user.userName}, ${user.password}")
        val data = MutableLiveData<Exception?>()
        getAuth().createUserWithEmailAndPassword(user.userName, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    getAuth().currentUser?.apply {
                        Log.i(T, "En el 1º successful")
                        val profile = UserProfileChangeRequest.Builder()
                            .setDisplayName(user.firstName)
                            .build()
                        updateProfile(profile).addOnCompleteListener { taskChangeProfile ->
                            if (taskChangeProfile.isSuccessful) {
                                Log.i(T, "En el 2º successful")
                                getFirestore()
                                    .collection(Constants.USERS)
                                    .document(getAuth().currentUser!!.uid)
                                    .set(
                                        user
                                    )
                                    .addOnCompleteListener { taskNewUser ->
                                        if (taskNewUser.isSuccessful) {
                                            Log.i(T, "En el 3º successful")
                                            data.value = null
                                        } else {
                                            Log.i(T, "En 2º else")
                                            data.value = taskNewUser.exception
                                        }
                                    }
                            } else {
                                data.value = taskChangeProfile.exception
                            }

                        }
                    }

                } else {
                    data.value = task.exception
                }
            }
        return data
    }


}