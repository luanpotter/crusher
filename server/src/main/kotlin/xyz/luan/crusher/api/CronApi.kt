package xyz.luan.crusher.api

import com.google.common.annotations.VisibleForTesting
import com.google.gson.Gson
import spark.kotlin.Http
import spark.kotlin.RouteHandler
import spark.kotlin.halt
import xyz.luan.crusher.FirebaseWrapper
import xyz.luan.crusher.model.Cron
import xyz.luan.crusher.model.Db

private val json = Gson()

@VisibleForTesting
fun String.extractToken() = this.replace("Bearer ([^)]*)".toRegex(), "$1")

private fun RouteHandler.parseCron() = json.fromJson(request.body(), Cron::class.java)

private fun RouteHandler.getToken(): String {
    val auth: String = request.headers("Authorization") ?: throw halt("Auth token is required")
    print("auth token $auth parsed ${auth.extractToken()}")
    return auth.extractToken()
}

object CronApi {
    fun routes(http: Http) {
        http.get("/crons") {
            val email = FirebaseWrapper.validateTokenAndGetEmail(getToken())
            val crons: List<Cron> = Db.listCrons(email)
            json.toJson(crons)
        }
        http.post("/crons") {
            val email = FirebaseWrapper.validateTokenAndGetEmail(getToken())
            val cron = parseCron()
            if (cron.userEmail != email) halt(403, "You can only created crons for yourself!")
            // TODO validate cron
            json.toJson(cron.save())
        }
    }
}