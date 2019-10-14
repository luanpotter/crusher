package xyz.luan.crusher

import com.google.firebase.auth.FirebaseAuth
import spark.kotlin.halt
import com.google.firebase.FirebaseApp
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.*


object FirebaseWrapper {

    private val app by lazy {
        val serviceAccount = getResourceAsStream("/crusher-app-firebase-service-account.json")

        val options = FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://crusher-app.firebaseio.com")
            .build()

        FirebaseApp.initializeApp(options)
    }

    fun validateTokenAndGetEmail(token: String): String {
        val firebaseToken = FirebaseAuth.getInstance(app).verifyIdToken(token)
        if (!firebaseToken.isEmailVerified) throw halt("Verified email required");
        return firebaseToken.email
    }

    fun sendPush(deviceToken: String, title: String, text: String) {
        val message = Message.builder()
            .setNotification(Notification(title, text))
            .setAndroidConfig(
                AndroidConfig.builder()
                    .setNotification(
                        AndroidNotification.builder()
                            .setIcon("news_alert_icon")
                            .build()
                    )
                    .build()
            )
            .setToken(deviceToken)
            .build()
        FirebaseMessaging.getInstance(app).sendAsync(message).get()
    }
}