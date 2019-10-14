package xyz.luan.crusher

import com.google.firebase.auth.FirebaseAuth
import spark.kotlin.halt

object FirebaseWrapper {

    fun validateTokenAndGetEmail(token: String): String {
        val firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token)
        if (!firebaseToken.isEmailVerified) throw halt("Verified email required");
        return firebaseToken.email
    }
}