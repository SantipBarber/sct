package com.spbarber.sct_project

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class App: Application() {
    companion object{

        fun getAuth(): FirebaseAuth{
            //Acceso a Firebase Auth
            return Firebase.auth
        }

        fun getFirestore(): FirebaseFirestore{
            //Acceso a Cloud Firestore
            return Firebase.firestore
        }

        fun getStorage(): FirebaseStorage {
            //Para imágenes, archivos...
            return Firebase.storage
        }

    }
}