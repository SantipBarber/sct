package com.spbarber.sct_project.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.spbarber.sct_project.App.Companion.getAuth

class UsuarioViewModel : ViewModel() {

    fun login(username: String, pass: String): LiveData<Task<AuthResult>> {
        val data = MutableLiveData<Task<AuthResult>>()
        getAuth().signInWithEmailAndPassword(username, pass)
            .addOnCompleteListener { task ->
                data.value = task
            }
        return data
    }
}