package xyz.luan.crusher.api

import com.google.common.annotations.VisibleForTesting
import com.google.gson.Gson
import spark.kotlin.Http
import spark.kotlin.RouteHandler
import spark.kotlin.halt
import xyz.luan.crusher.FirebaseWrapper
import xyz.luan.crusher.model.Cron
import xyz.luan.crusher.model.Db
import xyz.luan.crusher.model.Db.createCron

private val json = Gson()

@VisibleForTesting
fun String.extractToken() = this.replace("Bearer ([^)]*)".toRegex(), "$1")

private fun RouteHandler.parseCron() = json.fromJson(request.body(), Cron::class.java)

private fun RouteHandler.getToken(): String {
    val auth: String = request.headers("Authorization") ?: throw halt("Auth token is required")
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
            cron.validate()
            json.toJson(createCron(cron))
        }
        http.delete("/crons/:id") {
            val email = FirebaseWrapper.validateTokenAndGetEmail(getToken())
            val id = request.params("id")?.let { Integer.parseInt(it) } ?: throw halt(404, "Invalid id provided.")
            val dbCron = Db.findDbCron(id) ?: throw halt(404, "Cron does not exist")
            if (dbCron.userEmail != email) throw halt(403, "You can only delete your own crons")
            Db.deleteCron(dbCron)
            "Removed successfully"
        }
    }
}