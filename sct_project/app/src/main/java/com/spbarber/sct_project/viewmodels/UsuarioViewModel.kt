package com.spbarber.sct_project.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.toObject
import com.google.gson.Gson
import com.spbarber.sct_project.App.Companion.getAuth
import com.spbarber.sct_project.App.Companion.getFirestore
import com.spbarber.sct_project.entities.Preferences
import com.spbarber.sct_project.entities.User
import com.spbarber.sct_project.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuarioViewModel : ViewModel() {
    private val T = "TAG"
    val _users: MutableLiveData<List<com.google.firebase.firestore.auth.User>> by lazy {
        MutableLiveData<List<com.google.firebase.firestore.auth.User>>().also {
            loadUsers()
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO)
            {
                val users = mutableListOf<com.google.firebase.firestore.auth.User>()
                val docRef =
                    getFirestore().collection(Constants.USERS).document("${getAuth().currentUser?.uid}")
                docRef.get()
                    .addOnCompleteListener { document ->

                    }
                _users.postValue(users)
            }
        }
    }

    fun login(username: String, pass: String): LiveData<Task<AuthResult>> {
        val data = MutableLiveData<Task<AuthResult>>()
        getAuth().signInWithEmailAndPassword(username, pass)
            .addOnCompleteListener { task ->
                data.value = task
            }
        return data
    }

    fun getUser(uid: String): LiveData<User> {
        val data = MutableLiveData<User>()
        getFirestore()
            .collection(Constants.USERS)
            .document(uid)
            .get()
            .addOnSuccessListener {
                val user = it.toObject<User>()
                data.value = user!!
            }
        return data
    }

    fun getPreferences(user: String): LiveData<Preferences> {
        val data = MutableLiveData<Preferences>()
        getFirestore()
            .collection(Constants.USERS)
            .document(user)
            .get()
            .addOnSuccessListener { dataUser ->
                val prefs = dataUser.data!!["preferences"]
                val dataJson = Gson().toJson(prefs)

                val preferences = Gson().fromJson(dataJson, Preferences::class.java)

                data.value = preferences
            }

        return data
    }

    fun signin(user: User, preferences: Preferences): LiveData<Exception?> {
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
                                            getFirestore()
                                                .collection(Constants.USERS)
                                                .document(getAuth().currentUser!!.uid)
                                                .update("preferences", preferences)
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
